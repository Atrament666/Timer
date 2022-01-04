/* 
 * Copyright 2022 Atrament.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ui;

import core.ApplicationState;
import core.Preset;

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
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import core.TimerPreferences;
import dorkbox.systemTray.SystemTray;
import java.io.BufferedInputStream;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;
import ui.actions.ExitAction;
import ui.actions.ResetAction;
import ui.actions.StartAction;
import ui.actions.StopAction;

/**
 *
 * @author Atrament
 */
@Component
public class MainWindow extends JFrame implements TimerFrame, ApplicationContextAware {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private ApplicationContext ctx;

    public static enum TimeDirection {
        DOWN, UP
    };

    private Timer timer;
    private TimeDirection timeDirection; //
    private JMenuBar menuBar;

    @Autowired
    private ApplicationState appState;

    @Autowired
    public MainWindow(ApplicationContext context) {
        ctx = context;
        initComponents();
    }

    private void initComponents() {
        try {

            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            log.debug(e.getMessage());
        }
        timer = new Timer(1000, taskPerformer);
        setLayout(new MigLayout("", "[70px][70px][70px]", "[][70px][]"));
        setTitle("Timer");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon-16.png")));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e); //To change body of generated methods, choose Tools | Templates.
                setExtendedState(JFrame.ICONIFIED);
            }

        });
        setMenu();
        //setSysTray();
        setDorkBox();

        add(new MySpinLabel("Hours"));
        add(new MySpinLabel("Minutes"));
        add(new MySpinLabel("Seconds"), "wrap");
        add((MySpinner) ctx.getBean("hourSpinner"), "growx, growy");
        add((MySpinner) ctx.getBean("minuteSpinner"), "growx, growy");
        add((MySpinner) ctx.getBean("secondSpinner"), "growx, growy, wrap");
        add(new JButton(ctx.getBean(StartAction.class)), "growx");
        add(new JButton(ctx.getBean(ResetAction.class)), "growx");
        add(new JButton(ctx.getBean(StopAction.class)), "growx, cell 2 2");

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
            OptionsDialog od = ctx.getBean(OptionsDialog.class);
            od.setLocationRelativeTo(this);
            od.setVisible(true);
        });
        JMenuItem presetMenuItem = new JMenuItem("Preset Manager");
        presetMenuItem.addActionListener(e -> {
            PresetManagerDialog pmd = ctx.getBean(PresetManagerDialog.class);
            pmd.setLocationRelativeTo(this);
            pmd.setVisible(true);
            Preset p = pmd.getSelectedPreset();
            if (p != null) {
                setSpinners(p);
            } else {
                log.debug("Preset not selected");
            }
        });
        menuMenu.add(optionsMenuItem);
        menuMenu.add(presetMenuItem);
        menuMenu.add(new JSeparator());
        menuMenu.add(new JMenuItem(ctx.getBean(ExitAction.class)));

        setJMenuBar(menuBar);

    }

    private void setDorkBox() {
        SystemTray tray = SystemTray.get("Timer");
        tray.setImage(getClass().getResource("/images/pngegg.png"));
        
        JMenu trayMenu = new JMenu();
        JMenuItem mw = new JMenuItem("Main window");
        mw.addActionListener(e -> {
            toggleWindow();
        });
        trayMenu.add(mw);
        trayMenu.add(ctx.getBean(ExitAction.class));
        tray.setMenu(trayMenu);

    }

    private void toggleWindow() {
        setVisible(!isVisible());
        var state = (getState() == JFrame.NORMAL) ? JFrame.ICONIFIED : JFrame.NORMAL;
        setState(state);
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
    public void setSpinners(Preset preset) {
        ((MySpinner) ctx.getBean("hourSpinner")).setValue(preset.getHours());
        ((MySpinner) ctx.getBean("minuteSpinner")).setValue(preset.getMinutes());
        ((MySpinner) ctx.getBean("secondSpinner")).setValue(preset.getSeconds());
    }

    private final ActionListener taskPerformer = (ActionEvent e) -> {
        switch (timeDirection) {
            case DOWN:
                ((MySpinner) ctx.getBean("secondSpinner")).tickDown();
                break;
            case UP:
                ((MySpinner) ctx.getBean("secondSpinner")).tickUp();
                break;
        }

        if (((int) ((MySpinner) ctx.getBean("hourSpinner")).getValue() == 0)
                && ((int) ((MySpinner) ctx.getBean("minuteSpinner")).getValue() == 0)
                && ((int) ((MySpinner) ctx.getBean("secondSpinner")).getValue() == 0)) {
            raiseAlarm();
        }

    };

    @Override
    public void startTimer() {
        timer.start();
        timeDirection = TimeDirection.DOWN;
        if (TimerPreferences.is("minimize", true)) {
            setState(JFrame.ICONIFIED);
            setVisible(false);
        }
        appState.setCurrentState(ApplicationState.State.RUNNING);

    }

    @Override
    public void stopTimer() {
        timer.stop();
        appState.setCurrentState(ApplicationState.State.STOPPED);
    }

    public ApplicationState getAppState() {
        return appState;
    }

    public void setAppState(ApplicationState appState) {
        this.appState = appState;
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        ctx = ac;
    }

}
