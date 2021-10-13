package girlsofsteel;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import girlsofsteel.commands.*;
import girlsofsteel.objects.ShooterCamera;
import girlsofsteel.subsystems.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

public class OI {

    public static double JAG_SPEED = 1.0;
    public static double ENCODER_SPEED = 50;
    public static double VOLTAGE_SPEED = 12.0;
    
    //PS3 button numbers
    private static final int SQUARE = 1;
    private static final int X = 2;
    private static final int CIRCLE = 3;
    private static final int TRIANGLE = 4;
    private static final int L2 = 7;
    private static final int SELECT = 9;
    private static final int HOME = 13;
    private static final int START = 10;
    private static final int L1 = 5;
    private static final int R2 = 8;
    private static final int R1 = 6;

    //Joystick Ports + Joysticks
    private static final int DRIVER_JOYSTICK_PORT = 1;
    private static final int SHOOTER_JOYSTICK_PORT = 2;
    private Joystick driverJoystick;
    private Joystick operatorJoystick;
    
    //Driver Buttons
    private JoystickButton startDrive;
    private JoystickButton stopChassis;
    private JoystickButton gyroDrive;
    private JoystickButton normalDrive;
    private JoystickButton liningDrive;
    private JoystickButton disableRotation;
    private JoystickButton rotateRight;
    private JoystickButton rotateLeft;
    private JoystickButton rotateShootingBackRight;
    private JoystickButton rotateShootingBackLeft;
    
    //Operator Buttons
    private JoystickButton prepShoot;
    private JoystickButton prepShootPyramid;
    private JoystickButton loadFrisbee;
    private JoystickButton tipOver;
    private JoystickButton retract;
    private JoystickButton closeBottomGrip;
    private JoystickButton openBottomGrip;
    private JoystickButton closeTopGrip;
    private JoystickButton climb;
    private JoystickButton stopClimbing;
    private JoystickButton stopClimbing2;
    private JoystickButton toggleBlocker;
    private JoystickButton raiseBlocker;
    private JoystickButton lowerBlocker;

    public OI() {
        //Defining Joysticks
        driverJoystick = new Joystick(DRIVER_JOYSTICK_PORT);
        operatorJoystick = new Joystick(SHOOTER_JOYSTICK_PORT);
        
             //Defining Driver Buttons - microsoft joytick
        startDrive = new JoystickButton(driverJoystick, 9);
        startDrive.whenPressed(new Drive(1.0, 0.5, false));
        stopChassis = new JoystickButton(driverJoystick, 7);
        stopChassis.whenPressed(new StopChassis());
        gyroDrive = new JoystickButton(driverJoystick,11);
        gyroDrive.whenPressed(new Drive(1.0, 0.5, true));
        normalDrive = new JoystickButton(driverJoystick, 10);
        normalDrive.whenPressed(new Drive(1.0, 0.5, false));
        liningDrive = new JoystickButton(driverJoystick, 12);
        liningDrive.whenPressed(new Drive(0.5, 0.25, true));
        //disables driver rotation
        disableRotation = new JoystickButton(driverJoystick, 2);
        disableRotation.whileHeld(new DisableRotation());
//        rotateRight = new JoystickButton(driverJoystick, 4);
//        rotateRight.whenPressed(new Rotate(Chassis.FEEDER_RIGHT, true));
//        rotateLeft = new JoystickButton(driverJoystick, 3);
//        rotateLeft.whenPressed(new Rotate(Chassis.FEEDER_LEFT, true));
        rotateShootingBackRight = new JoystickButton(driverJoystick, 6);
        rotateShootingBackRight.whenPressed(new Rotate(
              //  ShooterCamera.getTopDiffAngle() + ShooterCamera.getLocationOffsetAngle(), false));
            90, false));
        rotateShootingBackLeft = new JoystickButton(driverJoystick, 5);
        rotateShootingBackLeft.whenPressed(new Rotate(
//                ShooterCamera.getSideDiffAngle() + ShooterCamera.getLocationOffsetAngle(), false));
            -90, false));
//        Working blocker raising code
//        raiseBlocker = new JoystickButton(driverJoystick, 12);
//        raiseBlocker.whenPressed(new RaiseBlocker());
//        lowerBlocker = new JoystickButton(driverJoystick, 8);
//        lowerBlocker.whenPressed(new LowerBlocker());

        
        
        //Defining Operator Buttons
        prepShoot = new JoystickButton(operatorJoystick, R1);
        prepShoot.whileHeld(new Shoot(0.9));
        //prepShootPyramid = new JoystickButton(operatorJoystick, L1);
        //prepShootPyramid.whileHeld(new Shoot(0.85));
        loadFrisbee = new JoystickButton(operatorJoystick, X);
        loadFrisbee.whileHeld(new PushPullShooterPiston());
        tipOver = new JoystickButton(operatorJoystick, SQUARE);
        tipOver.whenPressed(new TipRobotOver());
        retract = new JoystickButton(operatorJoystick, TRIANGLE);
        retract.whenPressed(new RetractClimberPiston());
        closeBottomGrip = new JoystickButton(operatorJoystick, SELECT);
        closeBottomGrip.whenPressed(new CloseBottomGrip());
        openBottomGrip = new JoystickButton(operatorJoystick, START);
        openBottomGrip.whenPressed(new OpenAllGrippers());
        closeTopGrip = new JoystickButton(operatorJoystick, HOME);
        closeTopGrip.whenPressed(new CloseTopGrip());
        climb = new JoystickButton(operatorJoystick, CIRCLE);
        climb.whileHeld(new StartClimbMotors());
        stopClimbing = new JoystickButton(operatorJoystick, L2);
        stopClimbing.whenPressed(new StopClimbMotors());
        stopClimbing2 = new JoystickButton(operatorJoystick, R2);
        stopClimbing2.whenPressed(new StopClimbMotors());

        //Raising and lowering the blocker using a toggle
        toggleBlocker = new JoystickButton(operatorJoystick, L1);
        toggleBlocker.whenPressed(new Blocking());
    }
    
     public Joystick getDrivingJoystick(){
        
        return driverJoystick;
        
    }//end getDriverJoystick
}
