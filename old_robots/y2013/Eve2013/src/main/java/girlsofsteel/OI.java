package girlsofsteel;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import girlsofsteel.commands.Blocking;
import girlsofsteel.commands.CloseBottomGrip;
import girlsofsteel.commands.CloseTopGrip;
import girlsofsteel.commands.DisableRotation;
import girlsofsteel.commands.Drive;
import girlsofsteel.commands.OpenAllGrippers;
import girlsofsteel.commands.PushPullShooterPiston;
import girlsofsteel.commands.RetractClimberPiston;
import girlsofsteel.commands.Rotate;
import girlsofsteel.commands.Shoot;
import girlsofsteel.commands.StartClimbMotors;
import girlsofsteel.commands.StopChassis;
import girlsofsteel.commands.StopClimbMotors;
import girlsofsteel.commands.TipRobotOver;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Climber;
import girlsofsteel.subsystems.DriveFlag;
import girlsofsteel.subsystems.Feeder;
import girlsofsteel.subsystems.Gripper;
import girlsofsteel.subsystems.Shooter;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

@SuppressWarnings({"PMD.TooManyFields"})
public class OI {

    public static final double JAG_SPEED = 1.0;
    public static final double ENCODER_SPEED = 50;
    public static final double VOLTAGE_SPEED = 12.0;

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
    private final Joystick m_driverJoystick;
    private final Joystick m_operatorJoystick;

    //Driver Buttons
    private final JoystickButton m_startDrive;
    private final JoystickButton m_stopChassis;
    private final JoystickButton m_gyroDrive;
    private final JoystickButton m_normalDrive;
    private final JoystickButton m_liningDrive;
    private final JoystickButton m_disableRotation;
    private final JoystickButton m_rotateShootingBackRight;
    private final JoystickButton m_rotateShootingBackLeft;

    //Operator Buttons
    private final JoystickButton m_prepShoot;
    private final JoystickButton m_loadFrisbee;
    private final JoystickButton m_tipOver;
    private final JoystickButton m_retract;
    private final JoystickButton m_closeBottomGrip;
    private final JoystickButton m_openBottomGrip;
    private final JoystickButton m_closeTopGrip;
    private final JoystickButton m_climb;
    private final JoystickButton m_stopClimbing;
    private final JoystickButton m_stopClimbing2;
    private final JoystickButton m_toggleBlocker;

    public OI(Chassis chassis, DriveFlag drive, Climber climber, Feeder feeder, Shooter shooter, Gripper gripper) {
        //Defining Joysticks
        m_driverJoystick = new Joystick(DRIVER_JOYSTICK_PORT);
        m_operatorJoystick = new Joystick(SHOOTER_JOYSTICK_PORT);

        //Defining Driver Buttons - microsoft joytick
        m_startDrive = new JoystickButton(m_driverJoystick, 9);
        m_startDrive.whenPressed(new Drive(this, chassis, drive, 1.0, 0.5, false));
        m_stopChassis = new JoystickButton(m_driverJoystick, 7);
        m_stopChassis.whenPressed(new StopChassis(chassis, drive));
        m_gyroDrive = new JoystickButton(m_driverJoystick, 11);
        m_gyroDrive.whenPressed(new Drive(this, chassis, drive, 1.0, 0.5, true));
        m_normalDrive = new JoystickButton(m_driverJoystick, 10);
        m_normalDrive.whenPressed(new Drive(this, chassis, drive, 1.0, 0.5, false));
        m_liningDrive = new JoystickButton(m_driverJoystick, 12);
        m_liningDrive.whenPressed(new Drive(this, chassis, drive, 0.5, 0.25, true));
        //disables driver rotation
        m_disableRotation = new JoystickButton(m_driverJoystick, 2);
        m_disableRotation.whileHeld(new DisableRotation(chassis));
//        rotateRight = new JoystickButton(driverJoystick, 4);
//        rotateRight.whenPressed(new Rotate(Chassis.FEEDER_RIGHT, true));
//        rotateLeft = new JoystickButton(driverJoystick, 3);
//        rotateLeft.whenPressed(new Rotate(Chassis.FEEDER_LEFT, true));
        m_rotateShootingBackRight = new JoystickButton(m_driverJoystick, 6);
        m_rotateShootingBackRight.whenPressed(new Rotate(chassis,
            //  ShooterCamera.getTopDiffAngle() + ShooterCamera.getLocationOffsetAngle(), false));
            90, false));
        m_rotateShootingBackLeft = new JoystickButton(m_driverJoystick, 5);
        m_rotateShootingBackLeft.whenPressed(new Rotate(chassis,
//                ShooterCamera.getSideDiffAngle() + ShooterCamera.getLocationOffsetAngle(), false));
            -90, false));
//        Working blocker raising code
//        raiseBlocker = new JoystickButton(driverJoystick, 12);
//        raiseBlocker.whenPressed(new RaiseBlocker());
//        lowerBlocker = new JoystickButton(driverJoystick, 8);
//        lowerBlocker.whenPressed(new LowerBlocker());


        //Defining Operator Buttons
        m_prepShoot = new JoystickButton(m_operatorJoystick, R1);
        m_prepShoot.whileHeld(new Shoot(shooter, 0.9));
        //prepShootPyramid = new JoystickButton(operatorJoystick, L1);
        //prepShootPyramid.whileHeld(new Shoot(0.85));
        m_loadFrisbee = new JoystickButton(m_operatorJoystick, X);
        m_loadFrisbee.whileHeld(new PushPullShooterPiston(feeder, shooter));
        m_tipOver = new JoystickButton(m_operatorJoystick, SQUARE);
        m_tipOver.whenPressed(new TipRobotOver(climber));
        m_retract = new JoystickButton(m_operatorJoystick, TRIANGLE);
        m_retract.whenPressed(new RetractClimberPiston(climber));
        m_closeBottomGrip = new JoystickButton(m_operatorJoystick, SELECT);
        m_closeBottomGrip.whenPressed(new CloseBottomGrip(gripper));
        m_openBottomGrip = new JoystickButton(m_operatorJoystick, START);
        m_openBottomGrip.whenPressed(new OpenAllGrippers(gripper));
        m_closeTopGrip = new JoystickButton(m_operatorJoystick, HOME);
        m_closeTopGrip.whenPressed(new CloseTopGrip());
        m_climb = new JoystickButton(m_operatorJoystick, CIRCLE);
        m_climb.whileHeld(new StartClimbMotors(climber));
        m_stopClimbing = new JoystickButton(m_operatorJoystick, L2);
        m_stopClimbing.whenPressed(new StopClimbMotors(climber));
        m_stopClimbing2 = new JoystickButton(m_operatorJoystick, R2);
        m_stopClimbing2.whenPressed(new StopClimbMotors(climber));

        //Raising and lowering the blocker using a toggle
        m_toggleBlocker = new JoystickButton(m_operatorJoystick, L1);
        m_toggleBlocker.whenPressed(new Blocking(feeder));
    }

    public Joystick getDrivingJoystick() {

        return m_driverJoystick;

    }//end getDriverJoystick
}
