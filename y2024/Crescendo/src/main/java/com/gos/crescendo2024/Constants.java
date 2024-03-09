// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.crescendo2024;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    // TODO(gpr) When we get the prototype up and running this should be decided by a jumper
    public static final boolean IS_COMPETITION_ROBOT = RobotBase.isReal();

    public static final boolean DEFAULT_CONSTANT_PROPERTIES = IS_COMPETITION_ROBOT;

    public static final boolean HAS_HANGER = true;

    // Joystick IDS
    public static final int DRIVER_JOYSTICK = 0;
    public static final int OPERATOR_JOYSTICK = 1;

    // CAN ID's
    public static final int ARM_PIVOT = 6;
    public static final int ARM_PIVOT_FOLLOW = 7;
    public static final int INTAKE_MOTOR = 10;
    public static final int SHOOTER_MOTOR_LEADER = 15;
    public static final int SHOOTER_MOTOR_FOLLOWER = 16;
    public static final int HANGER_LEFT_MOTOR = 4;
    public static final int HANGER_RIGHT_MOTOR = 5;

    public static final int PIGEON_PORT = 20;

    public static final int FRONT_RIGHT_WHEEL = 22;
    public static final int BACK_RIGHT_WHEEL = 21;
    public static final int FRONT_LEFT_WHEEL = 23;
    public static final int BACK_LEFT_WHEEL = 24;

    public static final int FRONT_RIGHT_AZIMUTH = 25;
    public static final int BACK_RIGHT_AZIMUTH = 26;
    public static final int FRONT_LEFT_AZIMUTH = 27;
    public static final int BACK_LEFT_AZIMUTH = 28;

    //Digital Inputs
    public static final int INTAKE_SENSOR = 1;
    public static final int SHOOTER_SENSOR = 0;
    public static final int HANGER_UPPER_LIMIT_SWITCH_LEFT = 2;
    public static final int HANGER_LOWER_LIMIT_SWITCH_LEFT = 3;
    public static final int HANGER_UPPER_LIMIT_SWITCH_RIGHT = 4;

    public static final int HANGER_LOWER_LIMIT_SWITCH_RIGHT = 5;

    //PWM
    public static final int LED_PORT = 0;


}
