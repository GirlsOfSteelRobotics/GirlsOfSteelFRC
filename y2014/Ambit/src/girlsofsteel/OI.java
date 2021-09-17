
package girlsofsteel;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    
    public static final int X_TRANSLATION_AXIS = 1;
    public static final int Y_TRANSLATION_AXIS = 2;
    //creates constant port values for the x and y axes; controls rotation of wheels
    public static final int ROTATION_AXIS = 3;
    //creates constant port value for the other "joystick" on the controller, which controls rotation of robot
    private static final int JOYSTICK_PORT = 1;
    Joystick joystick;
    
    public OI() {
        
    joystick = new Joystick(JOYSTICK_PORT);
    
    }
    
    public Joystick getJoystick() {
        
        return joystick;
    }
    
}
