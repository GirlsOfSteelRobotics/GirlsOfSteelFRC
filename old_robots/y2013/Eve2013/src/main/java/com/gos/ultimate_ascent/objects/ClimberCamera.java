/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.objects;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * @author Heather
 */
public class ClimberCamera {
    public static boolean isConnected() {
        return NetworkTable.getTable("camera").isConnected();
    }
}
