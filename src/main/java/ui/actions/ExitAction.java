/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.actions;

import java.awt.event.ActionEvent;
import static javax.swing.Action.NAME;
import org.atrament.ManagedAction;
import ui.MainWindow;

/**
 *
 * @author Atrament
 */
public class ExitAction extends ManagedAction {

    public ExitAction(MainWindow mw) {
        super(mw);
        initComponent();
    }

    private void initComponent() {
        putValue(NAME, "Exit");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }

}
