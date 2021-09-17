package girlsofsteel;

import edu.wpi.first.wpilibj.Joystick;

public class OI {
    
    public static final int JOYSTICK_PORT = 1;
    private Joystick joystick;
    
    public OI() {
        joystick = new Joystick(JOYSTICK_PORT);
    }
    
    public Joystick getJoystick(){
        return joystick;
    }
    
}