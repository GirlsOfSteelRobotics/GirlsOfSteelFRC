
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    
    private final int JOYSTICK_PORT = 1;
    private Joystick joystick;
    
    public OI()
    {
        joystick = new Joystick(JOYSTICK_PORT);
    }
    
    public Joystick getJoystick()
    {
        return joystick;
    }
}

