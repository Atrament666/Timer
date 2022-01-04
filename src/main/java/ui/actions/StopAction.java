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
package ui.actions;

import core.ApplicationState;
import java.awt.event.ActionEvent;
import org.springframework.stereotype.Component;

/**
 *
 * @author Atrament
 */
@Component
public class StopAction extends StateAwareAction{

    public StopAction() {
        initComponent();
    }

    private void initComponent() {
        addEnabledState(ApplicationState.State.RUNNING);
        putValue(NAME, "Stop");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        super.actionPerformed(arg0);
        getFrame().stopTimer();
    }
    
    
    
    
    
}