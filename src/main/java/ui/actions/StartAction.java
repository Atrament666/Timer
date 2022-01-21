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
package ui.actions;


import java.awt.event.ActionEvent;
import static javax.swing.Action.NAME;
import javax.swing.JFrame;
import org.atrament.ManagedAction;
import ui.MainWindow;

/**
 *
 * @author Atrament
 */
public class StartAction extends ManagedAction {

    public StartAction(JFrame mw) {
        super(mw);
        initComponent();
    }

    private void initComponent() {
        putValue(NAME, "Start");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        ((MainWindow)mainWindow).startTimer();
    }

}
