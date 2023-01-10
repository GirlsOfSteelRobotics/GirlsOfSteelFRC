package com.gos.stronghold.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import com.gos.stronghold.robot.commands.CollectBall;
import com.gos.stronghold.robot.commands.FlapDown;
import com.gos.stronghold.robot.commands.FlapUp;
import com.gos.stronghold.robot.commands.PivotDown;
import com.gos.stronghold.robot.commands.PivotMiddle;
import com.gos.stronghold.robot.commands.PivotUp;
import com.gos.stronghold.robot.commands.ReleaseBall;
import com.gos.stronghold.robot.commands.ResetEncoderDistance;
import com.gos.stronghold.robot.commands.RotateToDesiredAngle;
import com.gos.stronghold.robot.commands.ShiftDown;
import com.gos.stronghold.robot.commands.ShootBall;
import com.gos.stronghold.robot.commands.ShooterPistonsIn;
import com.gos.stronghold.robot.commands.ShooterPistonsOut;
import com.gos.stronghold.robot.commands.StopShooterWheels;
import com.gos.stronghold.robot.commands.buttons.SwitchToBackward;
import com.gos.stronghold.robot.commands.buttons.SwitchToForward;
import com.gos.stronghold.robot.commands.camera.SwitchCam;
import com.gos.stronghold.robot.subsystems.Camera;
import com.gos.stronghold.robot.subsystems.Chassis;
import com.gos.stronghold.robot.subsystems.Claw;
import com.gos.stronghold.robot.subsystems.Flap;
import com.gos.stronghold.robot.subsystems.Pivot;
import com.gos.stronghold.robot.subsystems.Shifters;
import com.gos.stronghold.robot.subsystems.Shooter;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
@SuppressWarnings({"PMD.TooManyFields", "PMD.ExcessiveMethodLength", "PMD.NcssCount"})
public class OI {

    /**
     * ROZIE IS WILDIN SO PLEASE CONSULT HER FOR DROPERATION PLANS
     */
    //IF ROZIE IS GAMEPAD; TURN TRUE. ELSE; TURN FALSE.
    private static final boolean ROZIE_DRIVE = false;

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
    //private    JoystickButton rollersOutButtonBoard;

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
        //shiftUpButton.onTrue(new ShiftUp());
        m_shiftDownButton = new JoystickButton(m_drivingStickForward, 3);
        m_shiftDownButton.onTrue(new ShiftDown(shifters));

        //shiftUpButton2 = new JoystickButton (drivingStickBackward, 4);
        //shiftUpButton2.whenPressed (new ShiftUp());
        m_shiftDownButton2 = new JoystickButton(m_drivingStickBackward, 3);
        m_shiftDownButton2.onTrue(new ShiftDown(shifters));

        m_switchCam = new JoystickButton(m_drivingStickForward, 10);
        m_switchCam.onTrue(new SwitchCam(camera));

        m_switchCam2 = new JoystickButton(m_drivingStickBackward, 10);
        m_switchCam2.onTrue(new SwitchCam(camera));

        m_switchToForward = new JoystickButton(m_drivingStickForward, 1);
        m_switchToForward.onTrue(new SwitchToForward(chassis, camera));


        m_switchToBackward = new JoystickButton(m_drivingStickBackward, 1);
        m_switchToBackward.onTrue(new SwitchToBackward(chassis, camera));

        // Button board buttons
        if (m_buttonBoard.getButtonCount() > 0) {
            m_shooterPistonsOut = new JoystickButton(m_buttonBoard, 3);
            m_shooterPistonsOut.onTrue(new ShooterPistonsOut(shooter));
            m_shooterPistonsIn = new JoystickButton(m_buttonBoard, 4);
            m_shooterPistonsIn.onTrue(new ShooterPistonsIn(shooter));
            m_shootBallButtonBoard = new JoystickButton(m_buttonBoard, 6);
            m_shootBallButtonBoard.onTrue(new ShootBall(claw, shooter));
            m_pivotUpButtonBoard = new JoystickButton(m_buttonBoard, 7);
            m_pivotUpButtonBoard.whileTrue(new PivotUp(pivot));
            m_collectBallButtonBoard = new JoystickButton(m_buttonBoard, 8);
            m_collectBallButtonBoard.whileTrue(new CollectBall(claw, shooter));
            m_releaseBallButtonBoard = new JoystickButton(m_buttonBoard, 9);
            m_releaseBallButtonBoard.whileTrue(new ReleaseBall(claw, shooter));
            m_pivotDownButtonBoard = new JoystickButton(m_buttonBoard, 10);
            m_pivotDownButtonBoard.whileTrue(new PivotDown(pivot));
            m_flapUpButtonBoard = new JoystickButton(m_buttonBoard, 11);
            m_flapUpButtonBoard.whileTrue(new FlapUp(flap));
            m_flapDownButtonBoard = new JoystickButton(m_buttonBoard, 12);
            m_flapDownButtonBoard.whileTrue(new FlapDown(flap));
        }

        //GamePad Buttons

        // these work for both claw and shooter
        m_collectBallButton = new JoystickButton(m_gamePad, 5);
        m_collectBallButton.whileTrue(new CollectBall(claw, shooter));
        m_releaseBallButton = new JoystickButton(m_gamePad, 6);
        m_releaseBallButton.whileTrue(new ReleaseBall(claw, shooter));
        m_shooterStop = new JoystickButton(m_gamePad, 1);
        m_shooterStop.onTrue(new StopShooterWheels(claw, shooter));

        //flap: rocker = drivers want to use to control movement of flap at full speed, w/o rocker goes until limit switch

        m_flapUp = new JoystickButton(m_gamePad, 8); //switched 7 & 8 again
        m_flapUp.whileTrue(new FlapUp(flap)); //false because it is not rocker button
        m_flapDown = new JoystickButton(m_gamePad, 7);
        m_flapDown.whileTrue(new FlapDown(flap));
        //flapUpRocker = new JoystickButton(buttonBoard, 5);
        //flapUpRocker.whileTrue(new FlapUp(true); //true because using rocker
        //flapDownRocker = new JoystickButton(buttonBoard, 6);
        //flapDownRocker.whileTrue(new FlapUp(true)); //^^

        //pivot
        m_pivotUp = new JoystickButton(m_gamePad, 3); //switched 2 & 3 again
        m_pivotUp.whileTrue(new PivotUp(pivot));
        m_pivotDown = new JoystickButton(m_gamePad, 2);
        m_pivotDown.whileTrue(new PivotDown(pivot));
        m_pivotMiddle = new JoystickButton(m_gamePad, 4); //this is always left unused
        m_pivotMiddle.whileTrue(new PivotMiddle(pivot));

        //test nav board
        m_testDesiredRotationAngle = new JoystickButton(m_drivingStickForward, 7);
        m_testDesiredRotationAngle.onTrue(new RotateToDesiredAngle(chassis, .2, 90));

        m_resetEncoders = new JoystickButton(m_gamePad, 9);
        m_resetEncoders.onTrue(new ResetEncoderDistance(chassis, flap, pivot));

        //ROZIE STUFF!!!!
        if (ROZIE_DRIVE) {
            m_rozieShiftDownButton = new JoystickButton(m_roziePad, 3);
            m_rozieShiftDownButton.onTrue(new ShiftDown(shifters));
            m_rozieFlapUp = new JoystickButton(m_roziePad, 8); //switched 7 & 8 again
            m_rozieFlapUp.whileTrue(new FlapUp(flap)); //false because it is not rocker button
            m_rozieFlapDown = new JoystickButton(m_roziePad, 7);
            m_rozieFlapDown.whileTrue(new FlapDown(flap));
            m_roziePivotUp = new JoystickButton(m_roziePad, 3); //switched 2 & 3 again
            m_roziePivotUp.whileTrue(new PivotUp(pivot));
            m_roziePivotDown = new JoystickButton(m_roziePad, 2);
            m_roziePivotDown.whileTrue(new PivotDown(pivot));
        }


    }

    public double getOperatorStickThrottle() {
        return m_gamePad.getThrottle();
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

    /**
     * Get Autonomous Mode Selector
     * <p>
     * Read a physical pushbutton switch attached to a USB gamepad controller,
     * returning an integer that matches the current readout of the switch.
     *
     * @return int ranging 0-15
     */
    public int getAutonSelector() {
        // Each of the four "button" inputs corresponds to a bit of a binary number
        // encoding the current selection. To simplify wiring, buttons 2-5 were used.
        return 1 * (m_autonSelector.getRawButton(2) ? 1 : 0)
            + 2 * (m_autonSelector.getRawButton(3) ? 1 : 0)
            + 4 * (m_autonSelector.getRawButton(4) ? 1 : 0)
            + 8 * (m_autonSelector.getRawButton(5) ? 1 : 0);
    }
}
