// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.reefscape;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final boolean DEFAULT_CONSTANT_PROPERTIES = true;
    public static final boolean IS_COMPETITION_ROBOT = false;

    // CAN
    public static final int FRONT_LEFT_DRIVE_MOTOR_ID = 1;
    public static final int FRONT_RIGHT_DRIVE_MOTOR_ID = 4;
    public static final int BACK_LEFT_DRIVE_MOTOR_ID = 5;
    public static final int BACK_RIGHT_DRIVE_MOTOR_ID = 8;

    public static final int FRONT_LEFT_STEER_MOTOR_ID = 2;
    public static final int FRONT_RIGHT_STEER_MOTOR_ID = 3;
    public static final int BACK_LEFT_STEER_MOTOR_ID = 6;
    public static final int BACK_RIGHT_STEER_MOTOR_ID = 7;

    public static final int FRONT_LEFT_CANCODER_ID = 9;
    public static final int FRONT_RIGHT_CANCODER_ID = 10;
    public static final int BACK_LEFT_CANCODER_ID = 11;
    public static final int BACK_RIGHT_CANCODER_ID = 12;
    public static final int ELEVATOR_MOTOR_ID = 14;
    public static final int ELEVATOR_FOLLOW_MOTOR_ID = 18;
    public static final int CORAL_MOTOR_ID = 15;
    public static final int PIVOT_MOTOR_ID = 16;
    public static final int ALGAE_MOTOR_ID = 17;


    public static final int PIGEON_ID = 20;

    // DIO
    public static final int CORAL_SENSOR_ID = 9;
    public static final int ALGAE_SENSOR_ID = 4;
    public static final int BOTLIMITSWICTH_ID = 2;
    public static final int TOPLIMITSWITCH_ID = 3;
    public static final int PIVOT_ABSOLUTE_ENCODER = 8;

    // Joysticks
    public static final int DRIVER_CONTROLLER_PORT = 0;
    public static final int OPERATOR_CONTROLLER_PORT = 1;

    // PWM
    public static final int LED_PORT_ID = 0;
}
