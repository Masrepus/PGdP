package com.samuel.pgdp;

import java.awt.*;

/**
 * Created by Samuel on 07.11.2016.
 */
public class Test {

    public static void main(String[] args) {
        java.awt.GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();


        for ( int i = 0; i < devices.length; i++ )
        {
            System.out.println( "Device " + i + " width: " + devices[ i ].getDisplayMode().getWidth() );
            System.out.println( "Device " + i + " height: " + devices[ i ].getDisplayMode().getHeight() );
        }
    }

    private static void test(String s) {
        System.out.println(s);
        test(s + "0");
        test(s + "1");
    }
}
