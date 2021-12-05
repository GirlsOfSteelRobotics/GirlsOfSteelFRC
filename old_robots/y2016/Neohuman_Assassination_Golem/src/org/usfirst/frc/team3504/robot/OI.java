package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.buttons.SwitchToForward;
import org.usfirst.frc.team3504.robot.commands.buttons.SwitchToBackward;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team3504.robot.commands.CollectBall;
import org.usfirst.frc.team3504.robot.commands.PivotDown;
import org.usfirst.frc.team3504.robot.commands.PivotUp;
import org.usfirst.frc.team3504.robot.commands.ReleaseBall;
import org.usfirst.frc.team3504.robot.commands.ShiftDown;
import org.usfirst.frc.team3504.robot.commands.ShooterPistonsIn;
import org.usfirst.frc.team3504.robot.commands.ShooterPistonsOut;
import org.usfirst.frc.team3504.robot.commands.StopShooterWheels;
import org.usfirst.frc.team3504.robot.commands.ShootBall;
import org.usfirst.frc.team3504.robot.commands.FlapUp;
import org.usfirst.frc.team3504.robot.commands.FlapDown;
import org.usfirst.frc.team3504.robot.commands.PivotMiddle;
import org.usfirst.frc.team3504.robot.commands.ResetEncoderDistance;
import org.usfirst.frc.team3504.robot.commands.RotateToDesiredAngle;
import org.usfirst.frc.team3504.robot.commands.camera.SwitchCam;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public enum DriveDirection {kFWD, kREV}

    /**
     * ROZIE IS WILDIN SO PLEASE CONSULT HER FOR DROPERATION PLANS
     */
    //IF ROZIE IS GAMEPAD; TURN TRUE. ELSE; TURN FALSE.
    private final boolean rozieDrive = false;


    private final Joystick drivingStickForward = new Joystick(0);
    private final Joystick drivingStickBackward = new Joystick(1);
    // The button board gets plugged into USB and acts like a Joystick
    private final Joystick gamePad = new Joystick(2);
    private final Joystick buttonBoard = new Joystick(3);
    // The autonomous command selector is uses buttons 2-5
    private final Joystick autonSelector = new Joystick(4);

    //Rozie's Nonsense: Project Droperation.
    private final Joystick roziePad = new Joystick(5);



    //JOYSTICK BUTTONS
    //private JoystickButton shiftUpButton;
    private final JoystickButton shiftDownButton;

    //private JoystickButton shiftUpButton2; //for backwards joystick
    private final JoystickButton shiftDownButton2; //for backwards joystick

    private DriveDirection driveDirection = DriveDirection.kFWD;

    private final JoystickButton switchCam;
    private final JoystickButton switchCam2; //for backwards joystick

    private final JoystickButton switchToForward;
    private final JoystickButton switchToBackward;

    //ROZIE DECLARATIONS
    /**
     * ROZIE
     */
    private JoystickButton rozieShiftDownButton;
    private JoystickButton rozieFlapUp;
    private JoystickButton rozieFlapDown;
    private JoystickButton roziePivotUp;
    private JoystickButton roziePivotDown;


    //button board
    private JoystickButton collectBallButtonBoard;
    private JoystickButton releaseBallButtonBoard;
    private JoystickButton flapUpButtonBoard;
    private JoystickButton flapDownButtonBoard;
    private JoystickButton pivotUpButtonBoard;
    private JoystickButton pivotDownButtonBoard;
    private JoystickButton shootBallButtonBoard;
    //private JoystickButton shooterPistonsOutButtonBoard;
    //private JoystickButton shooterPistonsInButtonBoard;
    //private JoystickButton rollersInButtonBoard;
    //private	JoystickButton rollersOutButtonBoard;

    //game pad
    private final JoystickButton collectBallButton;
    private final JoystickButton releaseBallButton;
    private final JoystickButton flapUp;
    private final JoystickButton flapDown;
    //private JoystickButton flapUpRocker;
    //private JoystickButton flapDownRocker;
    private final JoystickButton pivotUp;
    private final JoystickButton pivotDown;
    private final JoystickButton pivotMiddle;
    private final JoystickButton testDesiredRotationAngle;  //for NavBoard
    //private JoystickButton resetGyro;
    //private JoystickButton shootBall;
    private JoystickButton shooterPistonsOut;
    private JoystickButton shooterPistonsIn;
    private final JoystickButton resetEncoders;
    private final JoystickButton shooterStop;


    private static final int AXIS_DPAD = 6;

    //Flap: Rocker (2 buttons) + 2 buttons, Pivot: 3 buttons, Claw: 2 Buttons, Other: 3 Buttons (defenses & scoring), Shooter: 2 buttons - total 12 buttons + rocker

    public OI() {

        //DriveStick Buttons

        //shiftUpButton = new JoystickButton(drivingStickForward, 4);
        //shiftUpButton.whenPressed(new ShiftUp());
        shiftDownButton = new JoystickButton(drivingStickForward, 3);
        shiftDownButton.whenPressed(new ShiftDown());

        //shiftUpButton2 = new JoystickButton (drivingStickBackward, 4);
        //shiftUpButton2.whenPressed (new ShiftUp());
        shiftDownButton2 = new JoystickButton (drivingStickBackward, 3);
        shiftDownButton2.whenPressed(new ShiftDown());

        switchCam = new JoystickButton(drivingStickForward, 10);
        switchCam.whenPressed(new SwitchCam());

        switchCam2 = new JoystickButton(drivingStickBackward, 10);
        switchCam2.whenPressed(new SwitchCam());

        switchToForward = new JoystickButton(drivingStickForward, 1);
        switchToForward.whenPressed(new SwitchToForward());


        switchToBackward = new JoystickButton(drivingStickBackward, 1);
        switchToBackward.whenPressed(new SwitchToBackward());

        // Button board buttons
        if (buttonBoard.getButtonCount() > 0){
        shooterPistonsOut = new JoystickButton(buttonBoard, 3);
        shooterPistonsOut.whenPressed(new ShooterPistonsOut());
        shooterPistonsIn = new JoystickButton(buttonBoard, 4);
        shooterPistonsIn.whenPressed(new ShooterPistonsIn());
        shootBallButtonBoard = new JoystickButton(buttonBoard, 6);
        shootBallButtonBoard.whenPressed(new ShootBall());
        pivotUpButtonBoard = new JoystickButton(buttonBoard, 7);
        pivotUpButtonBoard.whileHeld(new PivotUp());
        collectBallButtonBoard = new JoystickButton(buttonBoard, 8);
        collectBallButtonBoard.whileHeld(new CollectBall());
        releaseBallButtonBoard = new JoystickButton(buttonBoard, 9);
        releaseBallButtonBoard.whileHeld(new ReleaseBall());
        pivotDownButtonBoard = new JoystickButton(buttonBoard, 10);
        pivotDownButtonBoard.whileHeld(new PivotDown());
        flapUpButtonBoard = new JoystickButton(buttonBoard, 11);
        flapUpButtonBoard.whileHeld(new FlapUp());
        flapDownButtonBoard = new JoystickButton(buttonBoard, 12);
        flapDownButtonBoard.whileHeld(new FlapDown());
        }

        //GamePad Buttons

        // these work for both claw and shooter
        collectBallButton = new JoystickButton(gamePad, 5);
        collectBallButton.whileHeld(new CollectBall());
        releaseBallButton = new JoystickButton(gamePad, 6);
        releaseBallButton.whileHeld(new ReleaseBall());
        shooterStop = new JoystickButton(gamePad, 1);
        shooterStop.whenPressed(new StopShooterWheels());


        if(!RobotMap.USING_CLAW) {
            // Put any claw-specific button assignments in here
        }

        //flap: rocker = drivers want to use to control movement of flap at full speed, w/o rocker goes until limit switch

        flapUp = new JoystickButton(gamePad, 8);//switched 7 & 8 again
        flapUp.whileHeld(new FlapUp()); //false because it is not rocker button
        flapDown = new JoystickButton(gamePad, 7);
        flapDown.whileHeld(new FlapDown());
        //flapUpRocker = new JoystickButton(buttonBoard, 5);
        //flapUpRocker.whileHeld(new FlapUp(true); //true because using rocker
        //flapDownRocker = new JoystickButton(buttonBoard, 6);
        //flapDownRocker.whileHeld(new FlapUp(true));//^^

        //pivot
        pivotUp = new JoystickButton(gamePad, 3); //switched 2 & 3 again
        pivotUp.whileHeld(new PivotUp());
        pivotDown = new JoystickButton(gamePad, 2);
        pivotDown.whileHeld(new PivotDown());
        pivotMiddle = new JoystickButton(gamePad, 4); //this is always left unused
        pivotMiddle.whileHeld(new PivotMiddle());

        //test nav board
        testDesiredRotationAngle = new JoystickButton(drivingStickForward, 7);
        testDesiredRotationAngle.whenPressed(new RotateToDesiredAngle(.2, 90));

        resetEncoders = new JoystickButton(gamePad, 9);
        resetEncoders.whenPressed(new ResetEncoderDistance());

        //ROZIE STUFF!!!!
        if (rozieDrive == true){
            rozieShiftDownButton = new JoystickButton(roziePad, 3);
            rozieShiftDownButton.whenPressed(new ShiftDown());
            rozieFlapUp = new JoystickButton(roziePad, 8);//switched 7 & 8 again
            rozieFlapUp.whileHeld(new FlapUp()); //false because it is not rocker button
            rozieFlapDown = new JoystickButton(roziePad, 7);
            rozieFlapDown.whileHeld(new FlapDown());
            roziePivotUp = new JoystickButton(roziePad, 3); //switched 2 & 3 again
            roziePivotUp.whileHeld(new PivotUp());
            roziePivotDown = new JoystickButton(roziePad, 2);
            roziePivotDown.whileHeld(new PivotDown());
        }



    }



    public double getDrivingJoystickY() {
        if (rozieDrive == true){
            return roziePad.getY();
        }
        else if (driveDirection == DriveDirection.kFWD){
            return drivingStickForward.getY();
        }
        else {
            return -drivingStickBackward.getY();
        }
    }

    public double getDrivingJoystickX() {
        if (rozieDrive == true){
            return roziePad.getX();
        }
        else if (driveDirection == DriveDirection.kFWD){
            return drivingStickForward.getX();
        }
        else {
            return drivingStickBackward.getX();
        }
    }

    public double getOperatorStickThrottle() {
        return gamePad.getThrottle();
    }

    public void setDriveDirection(DriveDirection driveDirection) {
        this.driveDirection = driveDirection;
        System.out.println("Drive direction set to: " + driveDirection);
    }

    public boolean isJoystickReversed() {
        return (driveDirection == DriveDirection.kREV);
    }

    public double getDPadX() {
        return gamePad.getRawAxis(AXIS_DPAD);
    }

    public boolean getDPadLeft() {
        double x = getDPadX();
        return (x < -0.5);
    }

    public boolean getDPadRight() {
        double x = getDPadX();
        return (x > 0.5);
    }

    /** Get Autonomous Mode Selector
     *
     * Read a physical pushbutton switch attached to a USB gamepad controller,
     * returning an integer that matches the current readout of the switch.
     * @return int ranging 0-15
     */
    public int getAutonSelector() {
        // Each of the four "button" inputs corresponds to a bit of a binary number
        // encoding the current selection. To simplify wiring, buttons 2-5 were used.
        int value =
                1 * (autonSelector.getRawButton(2) ? 1 : 0) +
                2 * (autonSelector.getRawButton(3) ? 1 : 0) +
                4 * (autonSelector.getRawButton(4) ? 1 : 0) +
                8 *	(autonSelector.getRawButton(5) ? 1 : 0);
        return value;
    }
}
