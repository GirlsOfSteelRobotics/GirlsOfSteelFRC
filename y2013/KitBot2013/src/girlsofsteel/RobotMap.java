package girlsofsteel;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    
    //chassis
    //jags
    public static final int RIGHT_JAG_FRONT = 7;
    public static final int RIGHT_JAG_BACK = 9;
    public static final int LEFT_JAG_FRONT = 8;
    public static final int LEFT_JAG_BACK = 10;
    public static final int SHOOTER_JAG = 6; 
    
    //encoders
    public static final int ENCODER_RIGHT_CHANNEL_A = 1;//change for real
    public static final int ENCODER_RIGHT_CHANNEL_B = 2;//change for real
    public static final int ENCODER_LEFT_CHANNEL_A = 3;//change for real
    public static final int ENCODER_LEFT_CHANNEL_B = 4;//change for real
    
}
