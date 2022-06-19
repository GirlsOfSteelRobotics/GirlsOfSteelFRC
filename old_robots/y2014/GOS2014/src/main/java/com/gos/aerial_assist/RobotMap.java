package com.gos.aerial_assist;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    public static final int OPERATOR_JOYSTICK = 2;
    public static final int CHASSIS_JOYSTICK = 1;
    public static final int MANIPULATOR_JAG = 5; //Checked on updated list 2/11/14
    public static final int MANIPULATOR_ENCODER_A = 9; //13; for practice robot //usually 9 and 10Checked on updated list 2/11/14
    public static final int MANIPULATOR_ENCODER_B = 10; //14; for practice robot //9; //Checked on updated list 2/11/14

    //TestBoard: public static final int RIGHT_JAG_PORT = 4;
    //TestBoard: public static final int LEFT_JAG_PORT = 6;
    public static final int RIGHT_JAG_PORT = 2; //Checked on updated list 2/11/14
    public static final int LEFT_JAG_PORT = 1; //Checked on updated list 2/11/14

    public static final int KICKER_MOTOR = 3;
    public static final int KICKER_ENCODER_A = 5;
    public static final int KICKER_ENCODER_B = 6;
    public static final int KICKER_LIMIT = 11;

    //public static final int KICKER_LIMIT_SWITCH_CHANNEL = 5; //Magic TODO
    public static final int LIGHTS = 2; //Checked on updated list 2/11/14

    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static final int leftMotor = 1;
    // public static final int rightMotor = 2;
    // If you are using multiple modusles, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
    public static final int COLLECTOR_WHEEL_SPIKE = 1; //Checked on updated list 2/11/14
    public static final int COLLECTOR_JAG = 4;  //Checked on updated list 2/11/14

    /* MIGHT BE THE COMPETITION BOT WIRING
     public static final int RIGHT_ENCODER_A = 3; //Checked on updated list 2/11/14
     public static final int RIGHT_ENCODER_B = 4; //Checked on updated list 2/11/14
     public static final int LEFT_ENCODER_A = 1; //Checked on updated list 2/11/14
     public static final int LEFT_ENCODER_B = 2; //Checked on updated list 2/11/14
     */

    public static final int RIGHT_ENCODER_A = 3; //Checked on updated list 2/11/14
    public static final int RIGHT_ENCODER_B = 4; //Checked on updated list 2/11/14
    public static final int LEFT_ENCODER_A = 1; //Checked on updated list 2/11/14
    public static final int LEFT_ENCODER_B = 2; //Checked on updated list 2/11/14


    public static final int ULTRASONIC_SENSOR_PORT = 3;

    public static final String COLLECTOR_ENCODER_READER = "Collector Encoder Reader";
    public static final String COLLECTOR_JAG_SPEED = "Collector Jag Speed";
    public static final String COLLECTOR_WHEEL_SPIKE_FORWARD = "Collector Wheel Spike Forward";
    public static final String COLLECTOR_WHEEL_SPIKE_BACKWARD = "Collector Wheel Spike Backward";

    //Names of things for SmartDashboard Test.  Should be changed once we know more things
    public static final String CHASSIS_SD = "Chassis Jags";
    public static final String LEFT_CHASSIS_SD = "Left Chassis Jag";
    public static final String RIGHT_CHASSIS_SD = "Right Chassis Jag";
    public static final String MANIPULATOR_SD = "Manipulator Jag";
    public static final String COLLECTOR_SD = "Collector Jag";
    public static final String KICKER_SD = "Kicker Jag";
    public static final String KICKER_SERVO_PREP = "Prep Kicker Servo";
    public static final String KICKER_SERVO_RELEASE = "Release Kicker Servo";
}
