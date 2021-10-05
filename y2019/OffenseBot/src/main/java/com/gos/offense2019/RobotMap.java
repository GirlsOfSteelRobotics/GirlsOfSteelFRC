/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.offense2019;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    public static final int DRIVE_RIGHT_MASTER_TALON = 4;//1;
    public static final int DRIVE_RIGHT_FOLLOWER_A_TALON = 5;//2;
    public static final int DRIVE_RIGHT_FOLLOWER_B_TALON = 6;//3;
    public static final int DRIVE_LEFT_MASTER_TALON = 1;//4;
    public static final int DRIVE_LEFT_FOLLOWER_A_TALON = 2;//5;
    public static final int DRIVE_LEFT_FOLLOWER_B_TALON = 3;//6;

    public static final int SHIFTER_RIGHT_A = 0;
    public static final int SHIFTER_RIGHT_B = 1;
    public static final int SHIFTER_LEFT_A = 2;
    public static final int SHIFTER_LEFT_B = 3;

    public static final int PISTON_A = 5;
    public static final int PISTON_B = 4;
}
