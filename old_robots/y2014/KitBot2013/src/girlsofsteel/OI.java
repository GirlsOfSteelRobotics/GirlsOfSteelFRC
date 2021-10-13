package girlsofsteel;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import girlsofsteel.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    
    public static double TURNING_SCALE = 0.8;
    
    //set up info for the driver joystick
    private final int DRIVER_JOYSTICK_PORT = 1;
    private Joystick driverJoystick;
    
    private final int SHOOTER_JOYSTICK_PORT = 2;
    private Joystick shooterJoystick;
    
    private JoystickButton drivePS3Button;
    private JoystickButton driveJoystickButton;
    
    private JoystickButton tankDriveJoystickButton;
    private JoystickButton tankDrivePS3Button;
    
    private JoystickButton shoot8;
    private JoystickButton shoot9;
    private JoystickButton shoot10;
    private JoystickButton shoot11;
    private JoystickButton shoot12;
    private JoystickButton stopJag;
    
    private final int DRIVER_JOYSTICK_RIGHT_PORT = 2;
    private Joystick driverJoystickRight;
    
    private final int DRIVER_JOYSTICK_LEFT_PORT = 3;
    private Joystick driverJoystickLeft;
    
    public OI(){
        
        //Testing Shooting at different volts (volts indicated by number after shoot)
        shooterJoystick = new Joystick(SHOOTER_JOYSTICK_PORT);
        
        shoot8 = new JoystickButton(shooterJoystick, 1);
        //Square
        shoot8.whenPressed(new Shoot8());
        
        shoot9 = new JoystickButton(shooterJoystick, 6);
        //R1
        shoot9.whenPressed(new Shoot9());
        
        shoot10 = new JoystickButton(shooterJoystick, 7);
        //L2
        shoot10.whenPressed(new Shoot10());
        
        shoot11 = new JoystickButton(shooterJoystick, 8);
        //R2
        shoot11.whenPressed(new Shoot11());
        
        shoot12 = new JoystickButton(shooterJoystick, 5);
        //L1
        shoot12.whenPressed(new Shoot12());     
        
        stopJag = new JoystickButton(shooterJoystick, 4);
        //Triangle
        stopJag.whenPressed(new StopShooter());
        
        
        
        //construct the driver joystick
        driverJoystick = new Joystick(DRIVER_JOYSTICK_PORT);
        
        drivePS3Button = new JoystickButton(driverJoystick, 10);
            //start button on ps3 controller
        drivePS3Button.whenPressed(new DriveJagsPS3());
        
        driveJoystickButton = new JoystickButton(driverJoystick, 3);
            //center top button on joystick (numbered 3)
        driveJoystickButton.whenPressed(new DriveJagsJoystick(TURNING_SCALE));
        
        //construct tank drive joysticks
        driverJoystickRight = new Joystick(DRIVER_JOYSTICK_RIGHT_PORT);
        driverJoystickLeft = new Joystick(DRIVER_JOYSTICK_LEFT_PORT);
        
        tankDrivePS3Button = new JoystickButton(driverJoystickLeft, 9);
            //select on PS3
        tankDrivePS3Button.whenPressed(new DriveJagsTankPS3());
        
        tankDriveJoystickButton = new JoystickButton(driverJoystickLeft, 2);
            //2 on joystick
        tankDriveJoystickButton.whenPressed(new DriveJagsTankJoystick());
    }//end constructor
    
    /**
     * Return the joystick object that is connected to driving.
     * @return driverJoystick
     */
    public Joystick getDriverJoystick(){
        return driverJoystick;
    }//end getDriverJoystick
    
    public Joystick getDriverJoystickRight(){
        return driverJoystickRight;
    }//end getDriverJoystick
    
    public Joystick getDriverJoystickLeft(){
        return driverJoystickLeft;
    }//end getDriverJoystick
    
}//end OI