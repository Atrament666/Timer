/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.actions;

import javax.swing.JFrame;
import org.atrament.ManagedAction;

/**
 *
 * @author Atrament
 */
public abstract class MainWindowAction extends ManagedAction{

    public MainWindowAction(JFrame mainWindow) {
        super(mainWindow);
    }

    
}
