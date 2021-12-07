package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team3504.robot.commands.CollectBall;
import org.usfirst.frc.team3504.robot.commands.FlapDown;
import org.usfirst.frc.team3504.robot.commands.FlapUp;
import org.usfirst.frc.team3504.robot.commands.PivotDown;
import org.usfirst.frc.team3504.robot.commands.PivotMiddle;
import org.usfirst.frc.team3504.robot.commands.PivotUp;
import org.usfirst.frc.team3504.robot.commands.ReleaseBall;
import org.usfirst.frc.team3504.robot.commands.ResetEncoderDistance;
import org.usfirst.frc.team3504.robot.commands.RotateToDesiredAngle;
import org.usfirst.frc.team3504.robot.commands.ShiftDown;
import org.usfirst.frc.team3504.robot.commands.ShootBall;
import org.usfirst.frc.team3504.robot.commands.ShooterPistonsIn;
import org.usfirst.frc.team3504.robot.commands.ShooterPistonsOut;
import org.usfirst.frc.team3504.robot.commands.StopShooterWheels;
import org.usfirst.frc.team3504.robot.commands.buttons.SwitchToBackward;
import org.usfirst.frc.team3504.robot.commands.buttons.SwitchToForward;
import org.usfirst.frc.team3504.robot.commands.camera.SwitchCam;
import org.usfirst.frc.team3504.robot.subsystems.Camera;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;
import org.usfirst.frc.team3504.robot.subsystems.Claw;
import org.usfirst.frc.team3504.robot.subsystems.Flap;
import org.usfirst.frc.team3504.robot.subsystems.Pivot;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;
import org.usfirst.frc.team3504.robot.subsystems.Shooter;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
@SuppressWarnings({"PMD.TooManyFields", "PMD.ExcessiveMethodLength", "PMD.NcssCount"})
public class OI {
    public enum DriveDirection {kFWD, kREV}

    /**
     * ROZIE IS WILDIN SO PLEASE CONSULT HER FOR DROPERATION PLANS
     */
    //IF ROZIE IS GAMEPAD; TURN TRUE. ELSE; TURN FALSE.
    private static final boolean rozieDrive = false;

    private final Joystick m_drivingStickForward = new Joystick(0);
    private final Joystick m_drivingStickBackward = new Joystick(1);
    // The button board gets plugged into USB and acts like a Joystick
    private final Joystick m_gamePad = new Joystick(2);
    private final Joystick m_buttonBoard = new Joystick(3);
    // The autonomous command selector is uses buttons 2-5
    private final Joystick m_autonSelector = new Joystick(4);

    //Rozie's Nonsense: Project Droperation.
    private final Joystick m_roziePad = new Joystick(5);



    //JOYSTICK BUTTONS
    //private JoystickButton shiftUpButton;
    private final JoystickButton m_shiftDownButton;

    //private JoystickButton shiftUpButton2; //for backwards joystick
    private final JoystickButton m_shiftDownButton2; //for backwards joystick

    private DriveDirection m_driveDirection = DriveDirection.kFWD;

    private final JoystickButton m_switchCam;
    private final JoystickButton m_switchCam2; //for backwards joystick

    private final JoystickButton m_switchToForward;
    private final JoystickButton m_switchToBackward;

    //ROZIE DECLARATIONS
    /**
     * ROZIE
     */
    private JoystickButton m_rozieShiftDownButton;
    private JoystickButton m_rozieFlapUp;
    private JoystickButton m_rozieFlapDown;
    private JoystickButton m_roziePivotUp;
    private JoystickButton m_roziePivotDown;


    //button board
    private JoystickButton m_collectBallButtonBoard;
    private JoystickButton m_releaseBallButtonBoard;
    private JoystickButton m_flapUpButtonBoard;
    private JoystickButton m_flapDownButtonBoard;
    private JoystickButton m_pivotUpButtonBoard;
    private JoystickButton m_pivotDownButtonBoard;
    private JoystickButton m_shootBallButtonBoard;
    //private JoystickButton shooterPistonsOutButtonBoard;
    //private JoystickButton shooterPistonsInButtonBoard;
    //private JoystickButton rollersInButtonBoard;
    //private	JoystickButton rollersOutButtonBoard;

    //game pad
    private final JoystickButton m_collectBallButton;
    private final JoystickButton m_releaseBallButton;
    private final JoystickButton m_flapUp;
    private final JoystickButton m_flapDown;
    //private JoystickButton flapUpRocker;
    //private JoystickButton flapDownRocker;
    private final JoystickButton m_pivotUp;
    private final JoystickButton m_pivotDown;
    private final JoystickButton m_pivotMiddle;
    private final JoystickButton m_testDesiredRotationAngle;  //for NavBoard
    //private JoystickButton resetGyro;
    //private JoystickButton shootBall;
    private JoystickButton m_shooterPistonsOut;
    private JoystickButton m_shooterPistonsIn;
    private final JoystickButton m_resetEncoders;
    private final JoystickButton m_shooterStop;


    private static final int AXIS_DPAD = 6;

    //Flap: Rocker (2 buttons) + 2 buttons, Pivot: 3 buttons, Claw: 2 Buttons, Other: 3 Buttons (defenses & scoring), Shooter: 2 buttons - total 12 buttons + rocker

    public OI(Chassis chassis, Shifters shifters, Claw claw, Shooter shooter, Flap flap, Pivot pivot, Camera camera) {

        //DriveStick Buttons

        //shiftUpButton = new JoystickButton(drivingStickForward, 4);
        //shiftUpButton.whenPressed(new ShiftUp());
        m_shiftDownButton = new JoystickButton(m_drivingStickForward, 3);
        m_shiftDownButton.whenPressed(new ShiftDown(shifters));

        //shiftUpButton2 = new JoystickButton (drivingStickBackward, 4);
        //shiftUpButton2.whenPressed (new ShiftUp());
        m_shiftDownButton2 = new JoystickButton (m_drivingStickBackward, 3);
        m_shiftDownButton2.whenPressed(new ShiftDown(shifters));

        m_switchCam = new JoystickButton(m_drivingStickForward, 10);
        m_switchCam.whenPressed(new SwitchCam(camera));

        m_switchCam2 = new JoystickButton(m_drivingStickBackward, 10);
        m_switchCam2.whenPressed(new SwitchCam(camera));

        m_switchToForward = new JoystickButton(m_drivingStickForward, 1);
        m_switchToForward.whenPressed(new SwitchToForward(this, camera));


        m_switchToBackward = new JoystickButton(m_drivingStickBackward, 1);
        m_switchToBackward.whenPressed(new SwitchToBackward(this, camera));

        // Button board buttons
        if (m_buttonBoard.getButtonCount() > 0){
        m_shooterPistonsOut = new JoystickButton(m_buttonBoard, 3);
        m_shooterPistonsOut.whenPressed(new ShooterPistonsOut(shooter));
        m_shooterPistonsIn = new JoystickButton(m_buttonBoard, 4);
        m_shooterPistonsIn.whenPressed(new ShooterPistonsIn(shooter));
        m_shootBallButtonBoard = new JoystickButton(m_buttonBoard, 6);
        m_shootBallButtonBoard.whenPressed(new ShootBall(claw, shooter));
        m_pivotUpButtonBoard = new JoystickButton(m_buttonBoard, 7);
        m_pivotUpButtonBoard.whileHeld(new PivotUp(pivot));
        m_collectBallButtonBoard = new JoystickButton(m_buttonBoard, 8);
        m_collectBallButtonBoard.whileHeld(new CollectBall(claw, shooter));
        m_releaseBallButtonBoard = new JoystickButton(m_buttonBoard, 9);
        m_releaseBallButtonBoard.whileHeld(new ReleaseBall(claw, shooter));
        m_pivotDownButtonBoard = new JoystickButton(m_buttonBoard, 10);
        m_pivotDownButtonBoard.whileHeld(new PivotDown(pivot));
        m_flapUpButtonBoard = new JoystickButton(m_buttonBoard, 11);
        m_flapUpButtonBoard.whileHeld(new FlapUp(flap));
        m_flapDownButtonBoard = new JoystickButton(m_buttonBoard, 12);
        m_flapDownButtonBoard.whileHeld(new FlapDown(flap));
        }

        //GamePad Buttons

        // these work for both claw and shooter
        m_collectBallButton = new JoystickButton(m_gamePad, 5);
        m_collectBallButton.whileHeld(new CollectBall(claw, shooter));
        m_releaseBallButton = new JoystickButton(m_gamePad, 6);
        m_releaseBallButton.whileHeld(new ReleaseBall(claw, shooter));
        m_shooterStop = new JoystickButton(m_gamePad, 1);
        m_shooterStop.whenPressed(new StopShooterWheels(claw, shooter));

        //flap: rocker = drivers want to use to control movement of flap at full speed, w/o rocker goes until limit switch

        m_flapUp = new JoystickButton(m_gamePad, 8);//switched 7 & 8 again
        m_flapUp.whileHeld(new FlapUp(flap)); //false because it is not rocker button
        m_flapDown = new JoystickButton(m_gamePad, 7);
        m_flapDown.whileHeld(new FlapDown(flap));
        //flapUpRocker = new JoystickButton(buttonBoard, 5);
        //flapUpRocker.whileHeld(new FlapUp(true); //true because using rocker
        //flapDownRocker = new JoystickButton(buttonBoard, 6);
        //flapDownRocker.whileHeld(new FlapUp(true));//^^

        //pivot
        m_pivotUp = new JoystickButton(m_gamePad, 3); //switched 2 & 3 again
        m_pivotUp.whileHeld(new PivotUp(pivot));
        m_pivotDown = new JoystickButton(m_gamePad, 2);
        m_pivotDown.whileHeld(new PivotDown(pivot));
        m_pivotMiddle = new JoystickButton(m_gamePad, 4); //this is always left unused
        m_pivotMiddle.whileHeld(new PivotMiddle(pivot));

        //test nav board
        m_testDesiredRotationAngle = new JoystickButton(m_drivingStickForward, 7);
        m_testDesiredRotationAngle.whenPressed(new RotateToDesiredAngle(chassis, .2, 90));

        m_resetEncoders = new JoystickButton(m_gamePad, 9);
        m_resetEncoders.whenPressed(new ResetEncoderDistance(chassis, flap, pivot));

        //ROZIE STUFF!!!!
        if (rozieDrive){
            m_rozieShiftDownButton = new JoystickButton(m_roziePad, 3);
            m_rozieShiftDownButton.whenPressed(new ShiftDown(shifters));
            m_rozieFlapUp = new JoystickButton(m_roziePad, 8);//switched 7 & 8 again
            m_rozieFlapUp.whileHeld(new FlapUp(flap)); //false because it is not rocker button
            m_rozieFlapDown = new JoystickButton(m_roziePad, 7);
            m_rozieFlapDown.whileHeld(new FlapDown(flap));
            m_roziePivotUp = new JoystickButton(m_roziePad, 3); //switched 2 & 3 again
            m_roziePivotUp.whileHeld(new PivotUp(pivot));
            m_roziePivotDown = new JoystickButton(m_roziePad, 2);
            m_roziePivotDown.whileHeld(new PivotDown(pivot));
        }



    }



    public double getDrivingJoystickY() {
        if (rozieDrive){
            return m_roziePad.getY();
        }
        else if (m_driveDirection == DriveDirection.kFWD){
            return m_drivingStickForward.getY();
        }
        else {
            return -m_drivingStickBackward.getY();
        }
    }

    public double getDrivingJoystickX() {
        if (rozieDrive){
            return m_roziePad.getX();
        }
        else if (m_driveDirection == DriveDirection.kFWD){
            return m_drivingStickForward.getX();
        }
        else {
            return m_drivingStickBackward.getX();
        }
    }

    public double getOperatorStickThrottle() {
        return m_gamePad.getThrottle();
    }

    public void setDriveDirection(DriveDirection driveDirection) {
        this.m_driveDirection = driveDirection;
        System.out.println("Drive direction set to: " + driveDirection);
    }

    public boolean isJoystickReversed() {
        return (m_driveDirection == DriveDirection.kREV);
    }

    public double getDPadX() {
        return m_gamePad.getRawAxis(AXIS_DPAD);
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
        return 1 * (m_autonSelector.getRawButton(2) ? 1 : 0) +
                2 * (m_autonSelector.getRawButton(3) ? 1 : 0) +
                4 * (m_autonSelector.getRawButton(4) ? 1 : 0) +
                8 *	(m_autonSelector.getRawButton(5) ? 1 : 0);
    }
}
