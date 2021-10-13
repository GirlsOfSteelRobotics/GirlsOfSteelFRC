package girlsofsteel;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import girlsofsteel.commands.*;

public class OI {

    public static final int DRIVING_JOYSTICK_PORT = 1;
    
    //PS3 button numbers
    public static final int SQUARE = 1;
    public static final int X = 2;
    public static final int CIRCLE = 3;
    public static final int TRIANGLE = 4;
    public static final int L1 = 5;
    public static final int R1 = 6;
    public static final int L2 = 7;
    public static final int R2 = 8;
    public static final int SELECT = 9;
    public static final int START = 10;
    
    //joysticks
    private Joystick drivingJoystick;
    
    //driver buttons
    private JoystickButton startDrive;
    private JoystickButton stopChassis;
    private JoystickButton toggleGyro;
    
    public OI(){
    
        //driver joystick
        drivingJoystick = new Joystick(DRIVING_JOYSTICK_PORT);
        
        startDrive = new JoystickButton(drivingJoystick, START);
        startDrive.whenPressed(new Drive());
        
        stopChassis = new JoystickButton(drivingJoystick, L1);
        stopChassis.whenPressed(new StopChassis());       
        
        toggleGyro = new JoystickButton(drivingJoystick, X);
        toggleGyro.whenPressed(new ToggleGyro());
        
    }//end constuctor
    
    public Joystick getDrivingJoystick(){
        
        return drivingJoystick;
        
    }//end getDriverJoystick
    
}//end OI