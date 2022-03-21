// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.rapidreact;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    //CAN
    public static final int VERTICAL_CONVEYOR_SPARK = 1;
    public static final int COLLECTOR_ROLLER = 2;
    public static final int HANGER_LEFT_SPARK = 3;
    public static final int DRIVE_LEFT_LEADER_SPARK = 4;
    public static final int DRIVE_LEFT_FOLLOWER_SPARK = 5;
    public static final int SHOOTER_LEADER_SPARK = 6;
    public static final int HORIZONTAL_CONVEYOR_LEADER_SPARK = 7;
    public static final int HANGER_RIGHT_SPARK = 8;
    public static final int DRIVE_RIGHT_LEADER_SPARK = 9;
    public static final int DRIVE_RIGHT_FOLLOWER_SPARK = 10;
    public static final int COLLECTOR_PIVOT_LEADER = 13;
    public static final int COLLECTOR_PIVOT_FOLLOWER = 11;
    public static final int VERTICAL_CONVEYOR_FEEDER_SPARK = 12;
    public static final int SHOOTER_ROLLER = 13;

    // Bigger to avoid future conflicts
    public static final int PIGEON_PORT = 20;


    // PWM
    public static final int LED = 0;
    public static final int SERVO_CHANNEL = 1;

    //DIO ports
    public static final int INDEX_SENSOR_LOWER_VERTICAL_CONVEYOR = 2;
    // 6 is used to power half of upper sensor
    // 7 is used for hanger???
    public static final int INTAKE_INDEX_SENSOR = 8;
    public static final int INDEX_SENSOR_UPPER_VERTICAL_CONVEYOR = 9;



    //from FRC game manual page 24
    public static final double TARMAC_DEPTH = Units.feetToMeters(7.75);
    //TODO: update this to have correct robot size based on CAD
    public static final double ROBOT_LENGTH = Units.inchesToMeters(30);
}
