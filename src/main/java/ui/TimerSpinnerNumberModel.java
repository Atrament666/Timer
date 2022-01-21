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

import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Atrament
 */
public class TimerSpinnerNumberModel extends SpinnerNumberModel {

    private SpinnerNumberModel linkedModel;

    public TimerSpinnerNumberModel(int value, int minimum, int maximum, int stepSize) {
        super(value, minimum, maximum, stepSize);
    }

    @Override
    public Object getPreviousValue() {
        Object value = super.getPreviousValue();
        if (value == null) {
            value = super.getMaximum();
            if (linkedModel != null) {
                linkedModel.setValue(linkedModel.getPreviousValue());
            }
        }
        return value;
    }

    @Override
    public Object getNextValue() {
        Object value = super.getNextValue();
        if (value == null) {
            value = super.getMinimum();
            if (linkedModel != null) {
                linkedModel.setValue(linkedModel.getNextValue());
            }
        }
        return value;
    }

    public SpinnerNumberModel getLinkedModel() {
        return linkedModel;
    }

    public void setLinkedModel(SpinnerNumberModel linkedModel) {
        this.linkedModel = linkedModel;
    }
    
    

}
