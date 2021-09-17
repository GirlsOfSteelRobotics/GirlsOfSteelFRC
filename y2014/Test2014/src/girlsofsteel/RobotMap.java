package girlsofsteel;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static final int leftMotor = 1;
    // public static final int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
    
    /*
     * PORTS
     * 
     * Jag 1: 9
     * Jag 2: 10
     * Jag 3: 11
     * Jag 1 encoder: 1,2
     * Jag 2 encoder: 3,4
     * Ccmpressor: 5
     * 
     * Spike 1: 1
     * Spike 2: 2
     * 
     */
    
    public static final int JAG1_PORT = 9;
    public static final int JAG2_PORT = 10;
    public static final int JAG3_PORT = 11;
    public static final int JAG1_ENCODER_A = 1;
    public static final int JAG1_ENCODER_B = 2;
    public static final int JAG2_ENCODER_A = 3;
    public static final int JAG2_ENCODER_B = 4;
    public static final int COMPRESSOR_PORT = 5;
    
    public static final int SPIKE1_PORT = 1;
    public static final int SPIKE2_PORT = 2;
    
    public static final int MODULE = 1;
    public static final int EXTEND_PISTON_ONE_SOLENOID = 1;
    public static final int RETRACT_PISTON_ONE_SOLENOID = 2;
    public static final int EXTEND_PISTON_TWO_SOLENOID = 3;
    public static final int RETRACT_PISTON_TWO_SOLENOID = 4;
    
     //compressor
    public static final int COMPRESSOR_RELAY_PORT = 3;
    public static final int PRESSURE_SWITCH_CHANNEL = 14;
    
}
