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

import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Atrament
 */
public class MySpinnerNumberModel extends SpinnerNumberModel {

    private SpinnerNumberModel linkedModel;

    public MySpinnerNumberModel(int value, int minimum, int maximum, int stepSize) {
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
