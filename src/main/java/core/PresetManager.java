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
package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Atrament
 */
public class PresetManager {

    private final List<Preset> presets;
    private final Path file = Path.of(System.getProperty("user.home"), ".config", "timer", "presets.csv");
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private final TableModel model;

    public PresetManager() {
        try {
            Files.createDirectories(file.getParent());
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        this.presets = new ArrayList<>();
        model = new AbstractTableModel() {
            private final String[] columNames = {"Name", "Hours", "Minutes", "Seconds"};

            @Override
            public int getRowCount() {
                return presets.size();
            }

            @Override
            public int getColumnCount() {
                return columNames.length;

            }

            @Override
            public Object getValueAt(int row, int col) {
                Preset p = presets.get(row);
                switch (col) {
                    case 0:
                        return p.getName();
                    case 1:
                        return p.getHours();
                    case 2:
                        return p.getMinutes();
                    case 3:
                        return p.getSeconds();
                }
                return p;
            }
            
            

            @Override
            public String getColumnName(int column) {
                return columNames[column];
            }

            @Override
            public void fireTableDataChanged() {
                super.fireTableDataChanged();
            }

        };

    }

    public void add(Preset p) {
        presets.add(p);
    }

    public boolean remove(Preset p) {
        return presets.remove(p);
    }

    public boolean edit(Preset p) {
        return false;
    }

    public Preset getPreset(int index) {
        return presets.get(index);
    }

    public void savePresets() {
        log.debug("Saving presets");
        try {
            Files.writeString(file, "", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            for (var p : presets) {
                Files.writeString(file, p.toString() + System.lineSeparator(), StandardOpenOption.APPEND);
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(PresetManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public final void loadPresets() {
        log.debug("Loading presets");
        presets.clear();
        try {
            List<String> lines = Files.readAllLines(file);
            for (var l : lines) {
                presets.add(Preset.fromString(l));
            }
        } catch (IOException ex) {
            log.debug(ex.getMessage());
        }
        ((AbstractTableModel) model).fireTableDataChanged();
    }

    public List<Preset> getPresets() {
        return Collections.unmodifiableList(presets);
    }

    public TableModel getModel() {
        return model;
    }

    public void add(String presetString) {
        String[] split = presetString.split(":");
        presets.add(Preset.fromString(presetString));
        ((AbstractTableModel) model).fireTableDataChanged();
    }

    public void remove(int selection) {
        presets.remove(selection);
        ((AbstractTableModel) model).fireTableDataChanged();
    }
}
