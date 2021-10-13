package girlsofsteel;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import girlsofsteel.commands.DisableChassis;
import girlsofsteel.commands.GoToLocation;
import girlsofsteel.commands.MoveToSetPoint;
import girlsofsteel.commands.TurnToSetPoint;

public class OI {

    private static final int JOYSTICK_PORT = 1;
    private static Joystick joystick;
    
    private static final int DISABLE_CHASSIS_BUTTON_LEFT = 5; //L1
    private JoystickButton disableChassisLeft;
    
    private static final int DISABLE_CHASSIS_BUTTON_RIGHT = 6; //R1
    private JoystickButton disableChassisRight;
    
    private static final int TURN_SET_POINT_BUTTON = 1; //square
    private JoystickButton turnSetPoint;
    
    private static final int MOVE_SET_POINT_BUTTON = 2; //x
    private JoystickButton moveSetPoint;
    
    private static final int GO_TO_LOCATION_BUTTON = 3; //cirlce
    private JoystickButton goToLocation;
    
    public OI() {
        joystick = new Joystick(JOYSTICK_PORT);
        disableChassisLeft = new JoystickButton(joystick, DISABLE_CHASSIS_BUTTON_LEFT);
        disableChassisRight = new JoystickButton(joystick, DISABLE_CHASSIS_BUTTON_RIGHT);
        disableChassisLeft.whenPressed(new DisableChassis());
        disableChassisRight.whenPressed(new DisableChassis());
        turnSetPoint = new JoystickButton(joystick, TURN_SET_POINT_BUTTON);
        turnSetPoint.whenPressed(new TurnToSetPoint());
        moveSetPoint = new JoystickButton(joystick, MOVE_SET_POINT_BUTTON);
        moveSetPoint.whenPressed(new MoveToSetPoint());
        goToLocation = new JoystickButton(joystick, GO_TO_LOCATION_BUTTON);
        goToLocation.whenPressed(new GoToLocation());
    }
    
    public Joystick getJoystick() {
        return joystick;
    }

}
