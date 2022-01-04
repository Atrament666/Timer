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
package core;

import javax.swing.SwingUtilities;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ui.MainWindow;

/**
 *
 * @author atrament
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        MainWindow frame = ctx.getBean(MainWindow.class);
        frame.getAppState().setCurrentState(ApplicationState.State.STOPPED);
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);

        });

    }

}
