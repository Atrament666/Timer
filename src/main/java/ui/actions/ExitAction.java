/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;

/**
 *
 * @author Atrament
 */
public class ExitAction extends AbstractAction{

    public ExitAction() {
        putValue(NAME, "Exit");
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
    
    
    
}
