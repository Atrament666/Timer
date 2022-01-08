/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.actions;

import java.awt.event.ActionEvent;
import static javax.swing.Action.NAME;
import ui.MainWindow;

/**
 *
 * @author Atrament
 */
public class ExitAction extends MainWindowAction{

    public ExitAction(MainWindow mw) {
        super(mw);
        putValue(NAME, "Exit");
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
    
    
    
}
