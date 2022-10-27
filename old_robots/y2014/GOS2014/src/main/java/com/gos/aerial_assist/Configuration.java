/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist;

import edu.wpi.first.wpilibj.Relay;

/**
 * @author Sylvie
 * <p>
 * Note: The default for all values is the COMPETITION robot
 */
public class Configuration {

    private static final int PRACTICE_ROBOT = 2;
    private static final int COMPETITION_ROBOT = 1;

    private static final int ACTIVE_ROBOT = COMPETITION_ROBOT;

    public static final boolean RIGHT_ENCODER_REVERSE;
    public static final boolean LEFT_ENCODER_REVERSE;
    public static final double LEFT_POSITION_P;
    public static final double RIGHT_POSITION_P;
    public static final double DESIRED_ANGLE_PIVOT_ARM_SIGN; //Just a multiplier to flip the sign
    public static final double DISENGAGE_COLLECTOR_SPEED;
    public static final double ENGAGE_COLLECTOR_SPEED;
    //Old competition arm p
    public static final double MANIPULATOR_PIVOT_P;
    public static final int LEFT_SETPOINT_SIGN = -1; //competition robot
    public static final int RIGHT_SETPOINT_SIGN = 1; //competition robot
    public static final boolean LEFT_PID_REVERSE_ENCODER = true; //competition bot
    public static final boolean RIGHT_PID_REVERSE_ENCODER = false; //competition bot
    public static final Relay.Value COLLECTOR_WHEEL_FORWARD_SPEED;
    public static final Relay.Value COLLECTOR_WHEEL_BACKWARD_SPEED;
    public static final int PIVOT_ENCODER_ZERO_VALUE;
    public static final int SIGN_OF_CHASSIS_POSITION_PID_SETPOINT;

    static {
        if (ACTIVE_ROBOT == PRACTICE_ROBOT) {
            //Encoders are right- false, left- true
            PIVOT_ENCODER_ZERO_VALUE = 86;
            RIGHT_ENCODER_REVERSE = false;
            LEFT_ENCODER_REVERSE = true;
            LEFT_POSITION_P = .5; //These are still unknown
            RIGHT_POSITION_P = .5; //These are still unknown
            SIGN_OF_CHASSIS_POSITION_PID_SETPOINT = 1;
            DESIRED_ANGLE_PIVOT_ARM_SIGN = 1.0;
            DISENGAGE_COLLECTOR_SPEED = 1.0;
            ENGAGE_COLLECTOR_SPEED = -1.0;
            MANIPULATOR_PIVOT_P = 0.1; //as of 3/17/14
            COLLECTOR_WHEEL_BACKWARD_SPEED = Relay.Value.kForward; //Found 3/17 that it's swtiched
            COLLECTOR_WHEEL_FORWARD_SPEED = Relay.Value.kReverse;
        } else if (ACTIVE_ROBOT == COMPETITION_ROBOT) {
            RIGHT_ENCODER_REVERSE = false;
            LEFT_ENCODER_REVERSE = true;
            PIVOT_ENCODER_ZERO_VALUE = 82;
            LEFT_POSITION_P = .45;
            RIGHT_POSITION_P = .45;
            SIGN_OF_CHASSIS_POSITION_PID_SETPOINT = 1;
            DESIRED_ANGLE_PIVOT_ARM_SIGN = 1.0; //Just a multiplier to flip the sign
            DISENGAGE_COLLECTOR_SPEED = 1.0;
            ENGAGE_COLLECTOR_SPEED = -1.0;
            MANIPULATOR_PIVOT_P = -0.12;
            COLLECTOR_WHEEL_FORWARD_SPEED = Relay.Value.kReverse;
            COLLECTOR_WHEEL_BACKWARD_SPEED = Relay.Value.kForward;
        }
        //printAllValues(robot);
    }

    public static void printAllValues(int robot) {
        System.out.println("Robot Number: " + robot);
        System.out.println("Left Encoder Reverse: " + LEFT_ENCODER_REVERSE);
        System.out.println("Right Encoder Reverse: " + RIGHT_ENCODER_REVERSE);
        System.out.println("Left Position P: " + LEFT_POSITION_P);
        System.out.println("Right Position P: " + RIGHT_POSITION_P);
        System.out.println("Desired Angle Sign: " + DESIRED_ANGLE_PIVOT_ARM_SIGN);
        System.out.println("Disengage Collector Speed: " + DISENGAGE_COLLECTOR_SPEED);
        System.out.println("Engage Collector Speed: " + ENGAGE_COLLECTOR_SPEED);
        System.out.println("Manipulator Pivot P: " + MANIPULATOR_PIVOT_P);
    }

}
