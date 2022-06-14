/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code     */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                                 */
/*----------------------------------------------------------------------------*/

package com.gos.infinite_recharge;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    // Motor controller CAN IDs
    // - Keep these in numeric order so it's clear that there are no duplicate numbers
    public static final int DRIVE_LEFT_FOLLOWER_SPARK = 1;
    public static final int WINCH_A_SPARK = 2;
    public static final int WINCH_B_SPARK = 3;
    public static final int LIFT_TALON = 4;
    public static final int CONTROL_PANEL_TALON = 5;
    public static final int SHOOTER_CONVEYOR_SPARK_A = 6;
    public static final int DRIVE_RIGHT_MASTER_SPARK = 7;
    public static final int DRIVE_RIGHT_FOLLOWER_SPARK = 8;
    public static final int SHOOTER_SPARK_A = 9;
    public static final int SHOOTER_SPARK_B = 10;
    public static final int SHOOTER_INTAKE_TALON = 11;
    public static final int SHOOTER_CONVEYOR_SPARK_B = 12;
    public static final int DRIVE_LEFT_MASTER_SPARK = 13;

    // PCM port numbers for the pnuematic solenoids
    public static final int DOUBLE_SOLENOID_SHOOTER_INTAKE_FORWARD = 0;
    public static final int DOUBLE_SOLENOID_SHOOTER_INTAKE_BACKWARD = 1;

    // DIO port numbers for digital (on/off) sensors
    public static final int DIGITAL_INPUT_SENSOR_HANDOFF = 4;
    public static final int DIGITAL_INPUT_SENSOR_SECONDARY = 2;
    public static final int DIGITAL_INPUT_SENSOR_TOP = 0;
    public static final int DIGITAL_INPUT_EUROPA = 9;
    public static final int DIGITAL_INPUT_LIDAR_LITE = 6;

    // Camera USB numbers
    // - These start at zero and go up from there
    // - Numbers are based on which port the camera is plugged into ** at power on **
    public static final int CAMERA_INTAKE = 0;
    public static final int CAMERA_CLIMB = 1;

    // Other motor controller constant values
    public static final int SPARK_MAX_CURRENT_LIMIT = 60;
    public static final int CTRE_TIMEOUT = 30;

    public static final double DEFAULT_RPM = 8600;
    public static final double DEFAULT_RPM_LEFT = 8800;

    public static final double LONG_RPM = 10500;

    // TurnToAngleProfiled constant values

    public static final double TURN_KP = 1;
    public static final double TURN_KI = 0;
    public static final double TURN_KD = 0;

    public static final double MAX_TURN_RATE_DEG_PER_S = 100;
    public static final double MAX_TURN_ACCELERATION_DEG_PER_S_SQUARED = 300;

    public static final double TURN_TOLERANCE_DEG = 5;
    public static final double TURN_RATE_TOLERANCE_DEG_PER_S = 10; // degrees per second

    public static final double MINIMUM_TURN_SPEED = .25;

    public static final double AUTO_LINE_LEFT_X = 121;
    public static final double AUTO_LINE_LEFT_Y = -159;

    public static final double OPPONENTS_TRENCH_X = 227;
    public static final double OPPONENTS_TRENCH_Y = -285;
    public static final double OPPONENTS_TRENCH_ANGLE = Math.toRadians(-30);

    public static final double OPPONENTS_TRENCH_CELLS_X = 250;
    public static final double OPPONENTS_TRENCH_CELLS_Y = -287;

    public static final double AUTO_LINE_LEFT_SHOOT_ANGLE = Math.toRadians(-45);


    public static class DriveConstants {
        public static final double KS_VOLTS = 0.179;
        public static final double KV_VOLT_SECONDS_PER_METER = 0.0653;
        public static final double KA_VOLT_SECONDS_SQUARED_PER_METER = 0.00754;
        public static final double KV_VOLT_SECONDS_PER_RADIAN = 2.5;
        public static final double KA_VOLT_SECONDS_SQUARED_PER_RADIAN = 0.3;
        public static final double MAX_VOLTAGE = 10;

        public static final double TRACK_WIDTH_METERS = 1.1554881713809029;
        public static final DifferentialDriveKinematics DRIVE_KINEMATICS =
            new DifferentialDriveKinematics(TRACK_WIDTH_METERS);
    }



}
