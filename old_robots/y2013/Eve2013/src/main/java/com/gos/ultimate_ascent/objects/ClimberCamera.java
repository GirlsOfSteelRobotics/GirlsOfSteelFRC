/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.objects;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * @author Heather
 */
public class ClimberCamera {
    public static boolean isConnected() {
        return NetworkTableInstance.getDefault().getTable("camera").isConnected();
    }
}
