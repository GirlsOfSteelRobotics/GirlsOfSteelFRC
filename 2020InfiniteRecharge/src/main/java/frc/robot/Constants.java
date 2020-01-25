/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int DRIVE_LEFT_MASTER_SPARK = 1;
    public static final int DRIVE_LEFT_FOLLOWER_SPARK = 2;
    public static final int DRIVE_RIGHT_MASTER_SPARK = 3;
    public static final int DRIVE_RIGHT_FOLLOWER_SPARK = 4;
    public static final int CONTROL_PANEL_SPARK = 5;
    public static final int SHOOTER_CONVEYOR_SPARK = 6;

    public static final int SHOOTER_TALON = 7;
    public static final int SHOOTER_INTAKE_TALON = 8;
    public static final int WINCH_A_TALON = 9;
    public static final int WINCH_B_TALON = 10;
    public static final int LIFT_TALON = 11;

    public static final int SPARK_MAX_CURRENT_LIMIT = 60;
    public static final int CTRE_TIMEOUT = 30;
}
