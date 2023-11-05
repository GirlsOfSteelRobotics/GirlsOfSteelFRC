// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.scra.mepi.rapid_react;

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
    // CAN IDs
    public static final int DRIVE_LEFT_LEADER = 2;
    public static final int DRIVE_LEFT_FOLLOWER = 3;
    public static final int DRIVE_RIGHT_LEADER = 4;
    public static final int DRIVE_RIGHT_FOLLOWER = 5;
    public static final int TOWER_SPARK = 8;
    public static final int TOWER_KICKER_SPARK = 9;
    public static final int SHOOTER_SPARK = 10;
    public static final int HOPPER_SPARK = 7;
    public static final int SHOOTER_HOOD_SPARK = 11;
    public static final int CLIMBER_LEFT = 12;
    public static final int CLIMBER_RIGHT = 13;

    // path constraints
    public static final PathConstraints DEFAULT_PATH_CONSTRAINTS =
            new PathConstraints(Units.inchesToMeters(48), Units.inchesToMeters(48));

    // DIO
    public static final int LEFT_LIMIT_SWITCH = 1;
    public static final int RIGHT_LIMIT_SWITCH = 0;

    // Odometry
    public static final double DRIVE_CONVERSION_FACTOR = 1;
    public static final double DRIVE_TRACK = 0; // TODO

    // Auto Aim
    public static final double MAX_TURN_VELOCITY = 0.5;
    public static final double MAX_TURN_ACCELERATION = 0.5;
}
