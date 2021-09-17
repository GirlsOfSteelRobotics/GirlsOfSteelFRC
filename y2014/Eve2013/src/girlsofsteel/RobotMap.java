package girlsofsteel;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    
//    //ROBOT -- WALL-E (Practice Robot)
//    
//    //Shooter values
//    public static final int SHOOTER_JAG = 4;
//    //This is for the special shooter controller
//    public static final int DIGITAL_INTPUT_CHANNEL = 7;
//    //This is for the shooter piston
//    public static final int SHOOTER_MODULE = 1;//2;
//    public static final int SHOOTER_PISTON_FRONT = 5;//module 3
//    public static final int SHOOTER_PISTON_BACK = 6;//module 3
//    
//    //Climber values
//    public static final int RIGHT_CLIMBER_SPIKE = 2;
//    public static final int LEFT_CLIMBER_SPIKE = 1;
//    public static final int TOP_GRIPPER_OPEN_BOTTOM_GRIPPER_CLOSE_SWITCH = 8;
//    public static final int TOP_GRIPPER_CLOSE_MIDDLE_GRIPPER_OPEN_SWITCH = 9;
//    public static final int MIDDLE_GRIPPER_CLOSE_SWITCH = 10;
//    public static final int BOTTOM_GRIPPER_OPEN_SWITCH = 11;
//    //None of the solenoids are on yet, so these values need to be changed when they are on
//    public static final int LIFTER_MODULE = 2;//6; //3?
//    public static final int EXTEND_LIFTER_PISTON_SOLENOID = 7;//module 7
//    public static final int RETRACT_LIFTER_PISTON_SOLENOID = 8;//module 7
//    public static final int CLIMBER_MODULE = 2;//6;
//    public static final int OPEN_TOP_GRIPPER_SOLENOID = 3;//module 7
//    public static final int CLOSE_TOP_GRIPPER_SOLENOID = 4;//module 7
//    public static final int OPEN_MIDDLE_GRIPPER_SOLENOID = 1;
//    public static final int CLOSE_MIDDLE_GRIPPER_SOLENOID = 2;
//    public static final int OPEN_BOTTOM_GRIPPER_SOLENOID = 6;//module 7
//    public static final int CLOSE_BOTTOM_GRIPPER_SOLENOID = 5;//module 7
//
//    public static final int OPEN_BLOCKER_SOLENOID = 7; //module 3
//    public static final int CLOSE_BLOCKER_SOLENOID = 8; //module 3
//
//    //jag numbers
//    public static final int RIGHT_WHEEL_JAG = 3;
//    public static final int BACK_WHEEL_JAG = 1;
//    public static final int LEFT_WHEEL_JAG = 2;
//    
//    //encoder numbers
//    public static final int RIGHT_WHEEL_CHANNEL_A = 5;
//    public static final int RIGHT_WHEEL_CHANNEL_B = 6;
//    public static final int BACK_WHEEL_CHANNEL_A = 1;
//    public static final int BACK_WHEEL_CHANNEL_B = 2;
//    public static final int LEFT_WHEEL_CHANNEL_A = 3;
//    public static final int LEFT_WHEEL_CHANNEL_B = 4;
//    
//    //gyro
//    public static final int GYRO_PORT = 1;//We were given two inputs - 1 & 2
//    
//    //compressor
//    public static final int COMPRESSOR_RELAY_PORT = 3;
//    public static final int PRESSURE_SWITCH_CHANNEL = 14;
    
            
//    //ROBOT -- KIWI
//
//    //Shooter values
//    public static final int SHOOTER_JAG = 4;
//    //This is for the special shooter controller
//    public static final int DIGITAL_INTPUT_CHANNEL = 7;
//    //This is for the shooter piston
//    public static final int SHOOTER_PISTON_FRONT = 3;
//    public static final int SHOOTER_PISTON_BACK = 4;
//
//    //Climber values
//    public static final int RIGHT_CLIMBER_SPIKE = 2;
//    public static final int LEFT_CLIMBER_SPIKE = 1;
//    public static final int TOP_GRIPPER_OPEN_BOTTOM_GRIPPER_CLOSE_SWITCH = 8;
//    public static final int TOP_GRIPPER_CLOSE_MIDDLE_GRIPPER_OPEN_SWITCH = 9;
//    public static final int MIDDLE_GRIPPER_CLOSE_SWITCH = 10;
//    public static final int BOTTOM_GRIPPER_OPEN_SWITCH = 11;
//    public static final int LIFTER_MODULE = 1;
//    public static final int EXTEND_LIFTER_PISTON_SOLENOID = 5;
//    public static final int RETRACT_LIFTER_PISTON_SOLENOID = 6;
//    public static final int CLIMBER_MODULE = 2;
//    public static final int OPEN_TOP_GRIPPER_SOLENOID = 1;
//    public static final int CLOSE_TOP_GRIPPER_SOLENOID = 2;
//    public static final int OPEN_MIDDLE_GRIPPER_SOLENOID = 4;
//    public static final int CLOSE_MIDDLE_GRIPPER_SOLENOID = 3;
//    public static final int OPEN_BOTTOM_GRIPPER_SOLENOID = 7;
//    public static final int CLOSE_BOTTOM_GRIPPER_SOLENOID = 8;
//
//    //jag numbers
//    public static final int RIGHT_WHEEL_JAG = 1;
//    public static final int BACK_WHEEL_JAG = 2;
//    public static final int LEFT_WHEEL_JAG = 3;
//
//    //encoder numbers
//    public static final int RIGHT_WHEEL_CHANNEL_A = 1;
//    public static final int RIGHT_WHEEL_CHANNEL_B = 2;
//    public static final int BACK_WHEEL_CHANNEL_A = 3;
//    public static final int BACK_WHEEL_CHANNEL_B = 4;
//    public static final int LEFT_WHEEL_CHANNEL_A = 5;
//    public static final int LEFT_WHEEL_CHANNEL_B = 6;
//
//    //gyro
//    public static final int GYRO_PORT = 1;
//    //accelerometer
//    //12c
//
//    //compressor
//    public static final int COMPRESSOR_RELAY_PORT = 3;
//    public static final int PRESSURE_SWITCH_CHANNEL = 14;
//
    //ROBOT -- EVE

    //pneumatics
    //shooter
    public static final int SHOOTER_MODULE = 1;
    public static final int SHOOTER_PISTON_FRONT = 3;
    public static final int SHOOTER_PISTON_BACK = 4;
    //lifter
    public static final int LIFTER_MODULE = 1;
    public static final int EXTEND_LIFTER_PISTON_SOLENOID = 5;
    public static final int RETRACT_LIFTER_PISTON_SOLENOID = 6;
    //climber
    public static final int CLIMBER_MODULE = 2;
    public static final int OPEN_TOP_GRIPPER_SOLENOID = 9;//random # not exist
    public static final int CLOSE_TOP_GRIPPER_SOLENOID = 10;//random # not exist
    public static final int OPEN_MIDDLE_GRIPPER_SOLENOID = 4;
    public static final int CLOSE_MIDDLE_GRIPPER_SOLENOID = 3;
    public static final int OPEN_BOTTOM_GRIPPER_SOLENOID = 7;
    public static final int CLOSE_BOTTOM_GRIPPER_SOLENOID = 8;
    //blocker
    public static final int BLOCKER_MODULE = 2;
    public static final int OPEN_BLOCKER_SOLENOID = 1;
    public static final int CLOSE_BLOCKER_SOLENOID = 2;
    
    //Shooter values
    public static final int SHOOTER_JAG = 4;
    //This is for the special shooter controller
    public static final int DIGITAL_INTPUT_CHANNEL = 7;

    //Climber values
    public static final int RIGHT_CLIMBER_SPIKE = 2;
    public static final int LEFT_CLIMBER_SPIKE = 1;
    public static final int TOP_GRIPPER_OPEN_BOTTOM_GRIPPER_CLOSE_SWITCH = 8;
    public static final int TOP_GRIPPER_CLOSE_MIDDLE_GRIPPER_OPEN_SWITCH = 9;
    public static final int MIDDLE_GRIPPER_CLOSE_SWITCH = 10;
    public static final int BOTTOM_GRIPPER_OPEN_SWITCH = 11;
    
    //jag numbers
    public static final int RIGHT_WHEEL_JAG = 3;
    public static final int BACK_WHEEL_JAG = 1;
    public static final int LEFT_WHEEL_JAG = 2;

    //encoder numbers
    public static final int RIGHT_WHEEL_CHANNEL_A = 5;
    public static final int RIGHT_WHEEL_CHANNEL_B = 6;
    public static final int BACK_WHEEL_CHANNEL_A = 1;
    public static final int BACK_WHEEL_CHANNEL_B = 2;
    public static final int LEFT_WHEEL_CHANNEL_A = 3;
    public static final int LEFT_WHEEL_CHANNEL_B = 4;

    //gyro
    public static final int GYRO_PORT = 1;
    //accelerometer
    //12c

    //compressor
    public static final int COMPRESSOR_RELAY_PORT = 3;
    public static final int PRESSURE_SWITCH_CHANNEL = 14;
}//end RobotMap
