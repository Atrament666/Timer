/* 
 * Copyright (C) 2022 Atrament.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package ui;

import com.formdev.flatlaf.FlatDarkLaf;
import core.AppState;
import core.Preset;
import core.PresetManager;

import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import core.TimerPreferences;
import dorkbox.systemTray.Menu;
import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.SystemTray;
import java.awt.Image;
import java.io.BufferedInputStream;
import javax.swing.ImageIcon;
import org.atrament.ActionManager;
import org.atrament.ManagedAction;
import ui.actions.ExitAction;
import ui.actions.ResetAction;
import ui.actions.StartAction;
import ui.actions.StopAction;

/**
 *
 * @author Atrament
 */
public class MainWindow extends JFrame implements TimerFrame {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private static final String ICON_FILE = "/images/icon.png";

    public static enum TimeDirection {
        DOWN, UP
    };

    private Timer timer;
    private TimeDirection timeDirection; //
    private JMenuBar menuBar;
    private TimerSpinner hourSpinner, minuteSpinner, secondSpinner;
    private final ActionManager<ManagedAction, MainWindow> actionManager;
    private final PresetManager presetManager;

    public MainWindow(PresetManager pm) {
        actionManager = new ActionManager<>(this);
        actionManager.setCurrentState(AppState.STOPPED);
        presetManager = pm;
        initComponents();
    }

    private void initComponents() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            log.debug(e.getMessage());
        }
        timer = new Timer(1000, taskPerformer);
        setLayout(new MigLayout("", "[70px][70px][70px]", "[][70px][]"));
        setTitle("Timer");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(ICON_FILE)));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                setExtendedState(JFrame.ICONIFIED);
            }

        });
        setMenu();
        setDorkBox();

        //actionManager.setMainWindow(this);
        hourSpinner = new TimerSpinner(23);
        minuteSpinner = new TimerSpinner(59);
        secondSpinner = new TimerSpinner(59);
        secondSpinner.setLinkedSpinner(minuteSpinner);
        minuteSpinner.setLinkedSpinner(hourSpinner);

        add(new TimerSpinLabel("Hours"));
        add(new TimerSpinLabel("Minutes"));
        add(new TimerSpinLabel("Seconds"), "wrap");

        add(hourSpinner, "growx, growy");
        add(minuteSpinner, "growx, growy");
        add(secondSpinner, "growx, growy, wrap");

        add(new JButton(actionManager.getAction(StartAction.class, AppState.STOPPED)), "growx");
        add(new JButton(actionManager.getAction(ResetAction.class, AppState.STOPPED)), "growx");
        add(new JButton(actionManager.getAction(StopAction.class, AppState.RUNNING)), "growx, cell 2 2");
        actionManager.updateActions();
        pack();
        setResizable(false);
        setLocationRelativeTo(null);

    }

    private void setMenu() {
        menuBar = new JMenuBar();
        JMenu menuMenu = new JMenu("Menu");
        menuBar.add(menuMenu);

        JMenuItem optionsMenuItem = new JMenuItem("Options");
        optionsMenuItem.addActionListener((ActionEvent e) -> {
            OptionsDialog od = new OptionsDialog();
            od.setLocationRelativeTo(this);
            od.setVisible(true);
        });
        JMenuItem presetMenuItem = new JMenuItem("Preset Manager");
        presetMenuItem.addActionListener(e -> {
            PresetManagerDialog pmd = new PresetManagerDialog(presetManager);
            pmd.setLocationRelativeTo(this);
            pmd.setVisible(true);
            Preset preset = pmd.getSelectedPreset();
            if (preset != null) {
                setSpinners(preset);
                setTitle("Timer - " + preset.toString());
            } else {
                log.debug("Preset not selected");
            }
        });
        menuMenu.add(optionsMenuItem);
        menuMenu.add(presetMenuItem);
        menuMenu.add(new JSeparator());
        menuMenu.add(new JMenuItem(actionManager.getAction(ExitAction.class)));

        setJMenuBar(menuBar);

    }

    private void setDorkBox() {
        SystemTray tray = SystemTray.get();
        Image icon = new ImageIcon(getClass().getResource(ICON_FILE)).getImage();
        tray.setImage(icon);

        Menu trayMenu = tray.getMenu();
        trayMenu.add(new MenuItem("Main window", (e) -> {
            toggleWindow();
        }));

        trayMenu.add(new JMenuItem(actionManager.getAction(ExitAction.class)));
    }

    private void toggleWindow() {
        setVisible(!isVisible());
        setState((getState() == JFrame.NORMAL) ? JFrame.ICONIFIED : JFrame.NORMAL);
    }

    private void raiseAlarm() {
        if (TimerPreferences.is("mute", false)) {
            log.debug("Beeping");
            SwingUtilities.invokeLater(() -> {
                try {
                    //TODO this has to be changed because of the proprietary warnings...
                    //InputStream in = new FileInputStream("beep.wav");
                    InputStream in = getClass().getClassLoader().getResourceAsStream("sound/beep.wav");
                    AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(in));
                    AudioFormat format = ais.getFormat();
                    DataLine.Info info = new DataLine.Info(Clip.class, format);
                    Clip clip = (Clip) AudioSystem.getLine(info);
                    clip.open(ais);
                    clip.start();

                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    log.debug("Problem playing audio: " + ex.getMessage());
                }
            });

        }
        setVisible(true);
        setState(JFrame.NORMAL);

        if ((timeDirection == TimeDirection.DOWN) && (TimerPreferences.is("timechange", false))) {
            timeDirection = TimeDirection.UP;
        } else {
            stopTimer();
        }
    }

    @Override
    public void resetSpinners() {
        setSpinners(new Preset(null, 0, 0, 0));
        setTitle("Timer");
    }

    @Override
    public void setSpinners(Preset preset) {
        hourSpinner.setValue(preset.getHours());
        minuteSpinner.setValue(preset.getMinutes());
        secondSpinner.setValue(preset.getSeconds());
    }

    private final ActionListener taskPerformer = (ActionEvent e) -> {
        switch (timeDirection) {
            case DOWN:
                secondSpinner.tickDown();
                break;
            case UP:
                secondSpinner.tickUp();
                break;
        }

        if (isTimerZero()) {
            raiseAlarm();

        }

    };

    private boolean isTimerZero() {
        return (((int) hourSpinner.getValue() == 0)
                && ((int) minuteSpinner.getValue() == 0)
                && ((int) secondSpinner.getValue() == 0));
    }

    @Override
    public void startTimer() {
        if (!isTimerZero()) {
            if (getTitle().equals("Timer")) {
                setTitle("Timer - " + getSpinnerValues());
            }
            timer.start();
            timeDirection = TimeDirection.DOWN;
            if (TimerPreferences.is("minimize", true)) {
                setState(JFrame.ICONIFIED);
                setVisible(false);
            }
            actionManager.setCurrentState(AppState.RUNNING);
        }
    }

    @Override
    public void stopTimer() {
        timer.stop();
        actionManager.setCurrentState(AppState.STOPPED);
    }

    private String getSpinnerValues() {
        return hourSpinner.getValue() + ":" + minuteSpinner.getValue() + ":" + secondSpinner.getValue();
    }

}
