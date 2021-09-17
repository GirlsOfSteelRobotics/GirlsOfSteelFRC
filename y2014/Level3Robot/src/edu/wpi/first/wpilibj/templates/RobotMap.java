package edu.wpi.first.wpilibj.templates;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    
    public static final int LEFT_JAG = 2;
    public static final int RIGHT_JAG = 1;
    
    //here we are initializing the Jags. They are set to ints because those specific ints are where
    //they are plugged into on the digital side car (in ports). 
    
    public static final int SPIKE_COMPRESSOR = 2;
    public static final int SPIKE_VAN_DOOR = 1;
    
    //here we are initalizing the spikes. They are set to ints because they are plugged into specific plugs
    
    //public static final int RIGHT_ENCODER = 1;// 1/2
    //public static final int SPIKE_VAN_DOOR = 1;// 3/4
    
    //ADVICE YOU SHOULD LISTEN TO: Name your variables helpful things. 
    //Good example: LEFT_JAG
    //Bad example: FLUFFY_PRETTY_UNICORN
}
