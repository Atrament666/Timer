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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import java.util.prefs.Preferences;
import static java.util.prefs.Preferences.userRoot;

/**
 *
 * @author Atrament
 */
public class TimerPreferences {

    private static final Preferences prefs = userRoot().node("/atrasoft/timer");

    public static String get(String key, String def) {
        return prefs.get(key, def);
    }

    public static void put(String key, String def) {
        prefs.put(key, def);
    }

    public static Boolean is(String key, Boolean def) {
        return prefs.getBoolean(key, true);
    }

    public static void set(String key, Boolean def) {
        prefs.putBoolean(key, def);
    }

    public <T> Object getObject(String key, T defaultObject) {
        Object t = null;
        byte[] dba;
        try {
            dba = object2Bytes(defaultObject);
            t = bytes2Object(prefs.getByteArray(key, dba));
        } catch (IOException | ClassNotFoundException ex) {
            getLogger(TimerPreferences.class.getName()).log(SEVERE, null, ex);
        }
        return t;
    }

    public <T> void putObject(String key, T t) {
        try {
            var ba = object2Bytes(t);
            prefs.putByteArray(key, ba);
        } catch (IOException ex) {
            getLogger(TimerPreferences.class.getName()).log(SEVERE, null, ex);
        }
    }

    private static byte[] object2Bytes(Object o) throws IOException {
        var baos = new ByteArrayOutputStream();
        var oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        return baos.toByteArray();
    }

    private static Object bytes2Object(byte raw[]) throws IOException, ClassNotFoundException {
        Object o = null;
        if (raw != null) {
            var bais = new ByteArrayInputStream(raw);
            var ois = new ObjectInputStream(bais);
            o = ois.readObject();

        }
        return o;
    }

}
