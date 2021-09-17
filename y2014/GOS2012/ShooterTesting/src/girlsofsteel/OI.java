package girlsofsteel;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import girlsofsteel.commands.AutoShootTop;

public class OI {
    
    private static final int JOYSTICK_PORT = 1;
    private static Joystick joystick;
    
    private static final int AUTO_SHOOT_BUTTON = 1;
    private JoystickButton autoShoot;
    
    public OI(){
        joystick = new Joystick(JOYSTICK_PORT);
        autoShoot = new JoystickButton(joystick, AUTO_SHOOT_BUTTON);
        autoShoot.whileHeld(new AutoShootTop());
    }
    
}

