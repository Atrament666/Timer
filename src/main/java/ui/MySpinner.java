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

import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Atrament
 */
public class MySpinner extends JSpinner {

    private final int maxValue;

    public MySpinner(int maxValue) {
        super();
        this.maxValue = maxValue;
        initComponents();
    }

    private void initComponents() {
        setModel(new MySpinnerNumberModel(0, 0, maxValue, 1));
        setEditor(new JSpinner.NumberEditor(this, "00"));
        setFont(new Font("Tahoma", Font.BOLD, 24));
       
    }

    public void setLinkedSpinner(MySpinner other) {
        ((MySpinnerNumberModel) getModel()).setLinkedModel(((MySpinnerNumberModel) other.getModel()));
    }
    
    public void tickDown() {
        setValue(getModel().getPreviousValue());
    }
    
    public void tickUp() {
        setValue(getModel().getNextValue());
    }
    
    
}
