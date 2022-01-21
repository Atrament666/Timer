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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import core.TimerPreferences;

/**
 *
 * @author Atrament
 */
public class OptionsDialog extends JDialog {

    private JCheckBox minimizeCheckBox;
    private JCheckBox muteCheckBox;
    private JCheckBox timeCheckBox;
    private JCheckBox startAfterRecentSelectedCheckBox;

    public OptionsDialog() {
        super();
        initComponents();
    }

    private void initComponents() {
        setLayout(new MigLayout("", "[grow]push[]", "[grow]push[]"));
        setModal(true);
        setPreferredSize(new Dimension(400, 400));
        setMinimumSize(getPreferredSize());
        setResizable(false);
        JPanel panel = new JPanel(new MigLayout("", "[][][][]", "[][][]"));
        panel.setBorder(BorderFactory.createTitledBorder("Options:"));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        muteCheckBox = new JCheckBox("Enable sound alarm");
        muteCheckBox.setSelected(TimerPreferences.is("mute", true));
        panel.add(muteCheckBox, "wrap");

        minimizeCheckBox = new JCheckBox("Minimize on start");
        minimizeCheckBox.setSelected(TimerPreferences.is("minimize", true));
        panel.add(minimizeCheckBox, "wrap");

        timeCheckBox = new JCheckBox("Enable time direction changing");
        timeCheckBox.setSelected(TimerPreferences.is("timechange", false));
        panel.add(timeCheckBox, "wrap");

        startAfterRecentSelectedCheckBox = new JCheckBox("Start timer right after recent item is selected");
        startAfterRecentSelectedCheckBox.setSelected(TimerPreferences.is("recentStart", false));
        panel.add(startAfterRecentSelectedCheckBox, "wrap");

        JButton okButton = new JButton("Ok");
        okButton.addActionListener((ActionEvent e) -> {
            saveConfig();
            close();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener((ActionEvent e) -> {
            close();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        add(panel, "wrap, grow");
        add(buttonPanel, " align right");
        pack();

    }

    private void saveConfig() {
        TimerPreferences.set("minimize", minimizeCheckBox.isSelected());
        TimerPreferences.set("mute", muteCheckBox.isSelected());
        TimerPreferences.set("timechange", timeCheckBox.isSelected());
        TimerPreferences.set("recentStart", startAfterRecentSelectedCheckBox.isSelected());
    }

    private void close() {
        setVisible(false);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

}
