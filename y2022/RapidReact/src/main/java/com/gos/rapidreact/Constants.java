// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.rapidreact;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int DRIVE_LEFT_LEADER_SPARK = 13;
    public static final int DRIVE_LEFT_FOLLOWER_SPARK = 1;
    public static final int DRIVE_RIGHT_LEADER_SPARK = 7;
    public static final int DRIVE_RIGHT_FOLLOWER_SPARK = 8;
    //hanger constants aren't correct
    public static final int HANGER_LEADER_SPARK = 11;
    public static final int HANGER_FOLLOWER_SPARK = 5;
    public static final int SERVO_CHANNEL = 0;
    //horizontal conveyor constants aren't correct
    public static final int HORIZONTAL_CONVEYOR_LEADER_SPARK = 3;
    public static final int HORIZONTAL_CONVEYOR_FOLLOWER_SPARK = 2;
    public static final int HORIZONTAL_CONVEYOR_MOTOR_SPEED = 600;

    public static final int VERTICAL_CONVEYOR_LEADER_SPARK = 4;
    public static final int VERTICAL_CONVEYOR_MOTOR_SPEED = 600;

    public static final int PIGEON_PORT = 5;

    public static final int COLLECTOR_ROLLER = 6;
    public static final int COLLECTOR_PIVOT = 4;
}
