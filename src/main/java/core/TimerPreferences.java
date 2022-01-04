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
