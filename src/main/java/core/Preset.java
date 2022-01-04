/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core;

/**
 *
 * @author Atrament
 */
public class Preset {
    
    private String name;
    private int hours, minutes, seconds;
    
    public static Preset fromString(String presetString) {
        String[] split = presetString.split(":");
        return new Preset(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]));
    }

    public Preset(String name, int hours, int minutes, int seconds) {
        this.name = name;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return name + ":" + hours + ":" + minutes + ":" + seconds;
    }
    
    
    
}
