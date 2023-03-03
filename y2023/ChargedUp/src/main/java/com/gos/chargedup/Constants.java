// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.chargedup;

import com.pathplanner.lib.PathConstraints;
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

    // CAN
    public static final int DRIVE_LEFT_LEADER_SPARK = 1;
    public static final int DRIVE_LEFT_FOLLOWER_SPARK = 2;
    public static final int DRIVE_RIGHT_LEADER_SPARK = 3;
    public static final int DRIVE_RIGHT_FOLLOWER_SPARK = 4;
    public static final int HOPPER_MOTOR = 10;
    public static final int PIVOT_MOTOR = 6;
    public static final int TURRET_MOTOR = 7;
    public static final int INTAKE_MOTOR = 5;
    public static final int PIGEON_PORT = 20;



    //DIO
    public static final int INTAKE_LOWER_LIMIT_SWITCH = 7;
    public static final int INTAKE_UPPER_LIMIT_SWITCH = 6;
    public static final int LEFT_TURRET_LIMIT_SWITCH = 5;
    public static final int INTAKE_TURRET_LIMIT_SWITCH = 4;
    public static final int RIGHT_TURRET_LIMIT_SWITCH = 3;

    //Pneumatics
    public static final int ARM_TOP_PISTON_OUT = 12;
    public static final int ARM_TOP_PISTON_IN = 13;
    public static final int ARM_BOTTOM_PISTON_REVERSE = 11;
    public static final int ARM_BOTTOM_PISTON_FORWARD = 8;

    public static final int CLAW_PISTON_FORWARD = 9;
    public static final int CLAW_PISTON_REVERSE = 10;
    public static final int INTAKE_PISTON_FORWARD = 15;
    public static final int INTAKE_PISTON_REVERSE = 14;

    //Controllers
    public static final int DRIVER_CONTROLLER_PORT = 0;
    public static final int OPERATOR_CONTROLLER_PORT = 1;

    //Auto velocity and acceleration
    public static final PathConstraints DEFAULT_PATH_CONSTRAINTS = new PathConstraints(Units.inchesToMeters(48), Units.inchesToMeters(48));

    // Pneumatics Module
    public static final int PRESSURE_SENSOR_PORT = 0;

    // PWM
    public static final int LED_PORT = 0;
}
