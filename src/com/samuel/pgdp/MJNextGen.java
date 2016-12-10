package com.samuel.pgdp;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Samuel on 25.11.2016.
 */
public class MJNextGen {
    public String readString(String text) {
        java.awt.GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        JFrame frame = new JFrame(devices[devices.length-1].getDefaultConfiguration());
        String s = JOptionPane.showInputDialog(frame, text);
        frame.dispose();

        if (s == null)
            System.exit(0);
        return s;
    }

    public String readString() {
        return readString("Eingabe:");
    }

    public int readInt(String text) {
        java.awt.GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        JFrame frame = new JFrame(devices[devices.length-1].getDefaultConfiguration());
        String s = JOptionPane.showInputDialog(frame, text);
        frame.dispose();

        int x = 0;
        if (s == null)
            System.exit(0);
        try {
            x = Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            x = readInt(text);
        }
        return x;
    }

    public int readInt() {
        return readInt("Geben Sie eine ganze Zahl ein:");
    }

    public int read(String text) {
        return readInt(text);
    }

    public int read() {
        return readInt();
    }

    public double readDouble(String text) {
        java.awt.GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        JFrame frame = new JFrame(devices[devices.length-1].getDefaultConfiguration());
        String s = JOptionPane.showInputDialog(frame, text);
        frame.dispose();

        double x = 0;
        if (s == null)
            System.exit(0);
        try {
            x = Double.parseDouble(s.trim());
        } catch (NumberFormatException e) {
            x = readDouble(text);
        }
        return x;
    }

    public double readDouble() {
        return readDouble("Geben Sie eine Zahl ein:");
    }

    public void write(String output) {
        java.awt.GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        JFrame frame = new JFrame(devices[devices.length-1].getDefaultConfiguration());
        JOptionPane.showMessageDialog(frame, output, "Ausgabe", JOptionPane.PLAIN_MESSAGE);
        frame.dispose();
    }

    public void write(int output) {
        write("" + output);
    }

    public void write(double output) {
        write("" + output);
    }

    public int randomInt(int minval, int maxval) {
        return minval + (new java.util.Random()).nextInt(maxval - minval + 1);
    }

    public int drawCard() {
        return randomInt(2, 11);
    }

    public int dice() {
        return randomInt(1, 6);
    }
}
