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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import ui.MySpinner;
import ui.OptionsDialog;
import ui.PresetManagerDialog;
import ui.actions.ExitAction;

/**
 *
 * @author Atrament
 */
@Configuration
@ComponentScan(basePackages = {"core", "ui"})
public class SpringConfig {

    @Bean
    public OptionsDialog optionsDialog() {
        return new OptionsDialog();
    }

    @Bean(name = "hourSpinner")
    public MySpinner hourSpinner() {
        return new MySpinner(23);
    }

    @Bean(name = "minuteSpinner")
    public MySpinner minuteSpinner(MySpinner hourSpinner) {
        MySpinner tmp = new MySpinner(59);
        tmp.setLinkedSpinner(hourSpinner);
        return tmp;
    }

    @Bean(name = "secondSpinner")
    public MySpinner secondSpinner(MySpinner minuteSpinner) {
        MySpinner tmp = new MySpinner(59);
        tmp.setLinkedSpinner(minuteSpinner);
        return tmp;
    }
    
    @Bean
    public ExitAction exitAction() {
        return new ExitAction();
    }
    
    @Bean(name = "presetManager")
    public PresetManager presetManager() {
        return new PresetManager();
    }
    
    @Bean
    @DependsOn(value = "presetManager")
    public PresetManagerDialog presetManagerDialog(PresetManager presetManager) {
        return new PresetManagerDialog(presetManager);
    }
    
    
}
