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

import core.Preset;
import core.PresetManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Atrament
 */
public class PresetManagerDialog extends javax.swing.JDialog {

    private final PresetManager presetManager;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private Preset selectedPreset;

    /**
     * Creates new form PresetManagerDialog
     */
    public PresetManagerDialog(PresetManager presetManager) {
        super();
        this.presetManager = presetManager;
        initComponents();
        presetManager.loadPresets();

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addButton = new JButton();
        removeButton = new JButton();
        closeButton = new JButton();
        jScrollPane1 = new JScrollPane();
        table = new JTable();
        selectButton = new JButton();

        setTitle("Preset Manager");
        setModal(true);
        setResizable(false);

        addButton.setText("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        removeButton.setText("Remove");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        closeButton.setText("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        table.setModel(presetManager.getModel());
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        jScrollPane1.setViewportView(table);

        selectButton.setText("Select");
        selectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                selectButtonActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(removeButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(selectButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                        .addComponent(closeButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(selectButton)
                    .addComponent(removeButton)
                    .addComponent(addButton)
                    .addComponent(closeButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        String presetString = JOptionPane.showInputDialog(this, "Enter preset:");
        presetManager.add(presetString);
    }//GEN-LAST:event_addButtonActionPerformed

    private void closeButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        presetManager.savePresets();
        dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void removeButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        int selection = table.getSelectedRow();
        if (selection >= 0) {
            log.debug("Removing preset at " + selection);
            presetManager.remove(selection);
        } else {
            log.debug("Nothing selected, nothing removed");
        }

    }//GEN-LAST:event_removeButtonActionPerformed

    private void selectButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_selectButtonActionPerformed
        int selection = table.getSelectedRow();
        if (selection >= 0) {
            selectedPreset = presetManager.getPresets().get(selection);
            dispose();
        } else {
        }
    }//GEN-LAST:event_selectButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton addButton;
    private JButton closeButton;
    private JScrollPane jScrollPane1;
    private JButton removeButton;
    private JButton selectButton;
    private JTable table;
    // End of variables declaration//GEN-END:variables

    public Preset getSelectedPreset() {
        return selectedPreset;
    }

}
