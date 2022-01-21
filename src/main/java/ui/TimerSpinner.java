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

import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Atrament
 */
public class TimerSpinner extends JSpinner {

    private final int maxValue;

    public TimerSpinner(int maxValue) {
        super();
        this.maxValue = maxValue;
        initComponents();
    }

    private void initComponents() {
        setModel(new TimerSpinnerNumberModel(0, 0, maxValue, 1));
        setEditor(new JSpinner.NumberEditor(this, "00"));
        setFont(new Font("Tahoma", Font.BOLD, 24));
       
    }

    public void setLinkedSpinner(TimerSpinner other) {
        ((TimerSpinnerNumberModel) getModel()).setLinkedModel(((TimerSpinnerNumberModel) other.getModel()));
    }
    
    public void tickDown() {
        setValue(getModel().getPreviousValue());
    }
    
    public void tickUp() {
        setValue(getModel().getNextValue());
    }
    
    
}
