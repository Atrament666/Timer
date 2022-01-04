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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.swing.AbstractAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import ui.TimerFrame;

/**
 *
 * @author Atrament
 */
public class StateAwareAction extends AbstractAction {

    private final Set<ApplicationState.State> enabledStates = new HashSet<>();
    @Autowired
    @Lazy
    private TimerFrame frame;

    @Override
    public void actionPerformed(ActionEvent arg0) {

    }

    public void addEnabledState(ApplicationState.State state) {
        enabledStates.add(state);
    }

    public Set<ApplicationState.State> getEnabledStates() {
        return Collections.unmodifiableSet(enabledStates);
    }

    public TimerFrame getFrame() {
        return frame;
    }

    public void setFrame(TimerFrame frame) {
        this.frame = frame;
    }

}
