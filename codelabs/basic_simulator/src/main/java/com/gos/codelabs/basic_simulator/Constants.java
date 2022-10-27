/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.codelabs.basic_simulator;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    /////////////////////////////////
    // Sensor / Actuator ports
    /////////////////////////////////

    // PWM

    // Digital IO
    public static final int DIO_LIFT_LOWER_LIMIT = 0;
    public static final int DIO_LIFT_UPPER_LIMIT = 1;

    // Solenoids
    public static final int SOLENOID_PUNCH = 0;

    // CAN
    public static final int CAN_CHASSIS_LEFT_A = 1;
    public static final int CAN_CHASSIS_LEFT_B = 2;
    public static final int CAN_CHASSIS_RIGHT_A = 3;
    public static final int CAN_CHASSIS_RIGHT_B = 4;
    public static final int CAN_LIFT_MOTOR = 5;

    public static final boolean SIMULATE_SENSOR_NOISE = false;

    private Constants() {

    }
}
