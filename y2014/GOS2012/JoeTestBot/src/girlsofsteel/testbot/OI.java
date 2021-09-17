package girlsofsteel.testbot;

import edu.wpi.first.wpilibj.Joystick;

public class OI {

    private Joystick joystick = new Joystick(1);

    public OI() {
    }

    public Joystick getJoystick(){
        return joystick;
    }
}