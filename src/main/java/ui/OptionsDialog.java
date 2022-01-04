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
