package com.gos.ultimate_ascent;

import com.gos.ultimate_ascent.commands.RetractClimberPiston;
import com.gos.ultimate_ascent.commands.Rotate;
import com.gos.ultimate_ascent.commands.Shoot;
import com.gos.ultimate_ascent.commands.StartClimbMotors;
import com.gos.ultimate_ascent.commands.StopChassis;
import com.gos.ultimate_ascent.commands.StopClimbMotors;
import com.gos.ultimate_ascent.commands.TipRobotOver;
import com.gos.ultimate_ascent.subsystems.Chassis;
import com.gos.ultimate_ascent.subsystems.Climber;
import com.gos.ultimate_ascent.subsystems.DriveFlag;
import com.gos.ultimate_ascent.subsystems.Feeder;
import com.gos.ultimate_ascent.subsystems.Gripper;
import com.gos.ultimate_ascent.subsystems.Shooter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import com.gos.ultimate_ascent.commands.Blocking;
import com.gos.ultimate_ascent.commands.CloseBottomGrip;
import com.gos.ultimate_ascent.commands.CloseTopGrip;
import com.gos.ultimate_ascent.commands.DisableRotation;
import com.gos.ultimate_ascent.commands.Drive;
import com.gos.ultimate_ascent.commands.OpenAllGrippers;
import com.gos.ultimate_ascent.commands.PushPullShooterPiston;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

@SuppressWarnings({"PMD.TooManyFields"})
public class OI {

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
        m_startDrive.onTrue(new Drive(m_driverJoystick, chassis, drive, 1.0, 0.5, false));
        m_stopChassis = new JoystickButton(m_driverJoystick, 7);
        m_stopChassis.onTrue(new StopChassis(chassis, drive));
        m_gyroDrive = new JoystickButton(m_driverJoystick, 11);
        m_gyroDrive.onTrue(new Drive(m_driverJoystick, chassis, drive, 1.0, 0.5, true));
        m_normalDrive = new JoystickButton(m_driverJoystick, 10);
        m_normalDrive.onTrue(new Drive(m_driverJoystick, chassis, drive, 1.0, 0.5, false));
        m_liningDrive = new JoystickButton(m_driverJoystick, 12);
        m_liningDrive.onTrue(new Drive(m_driverJoystick, chassis, drive, 0.5, 0.25, true));
        //disables driver rotation
        m_disableRotation = new JoystickButton(m_driverJoystick, 2);
        m_disableRotation.whileTrue(new DisableRotation(chassis));
        //        rotateRight = new JoystickButton(driverJoystick, 4);
        //        rotateRight.onTrue(new Rotate(Chassis.FEEDER_RIGHT, true));
        //        rotateLeft = new JoystickButton(driverJoystick, 3);
        //        rotateLeft.onTrue(new Rotate(Chassis.FEEDER_LEFT, true));
        m_rotateShootingBackRight = new JoystickButton(m_driverJoystick, 6);
        m_rotateShootingBackRight.onTrue(new Rotate(chassis,
            //  ShooterCamera.getTopDiffAngle() + ShooterCamera.getLocationOffsetAngle(), false));
            90, false));
        m_rotateShootingBackLeft = new JoystickButton(m_driverJoystick, 5);
        m_rotateShootingBackLeft.onTrue(new Rotate(chassis,
            //                ShooterCamera.getSideDiffAngle() + ShooterCamera.getLocationOffsetAngle(), false));
            -90, false));
        //        Working blocker raising code
        //        raiseBlocker = new JoystickButton(driverJoystick, 12);
        //        raiseBlocker.onTrue(new RaiseBlocker());
        //        lowerBlocker = new JoystickButton(driverJoystick, 8);
        //        lowerBlocker.onTrue(new LowerBlocker());


        //Defining Operator Buttons
        m_prepShoot = new JoystickButton(m_operatorJoystick, R1);
        m_prepShoot.whileTrue(new Shoot(shooter, 0.9));
        //prepShootPyramid = new JoystickButton(operatorJoystick, L1);
        //prepShootPyramid.whileTrue(new Shoot(0.85));
        m_loadFrisbee = new JoystickButton(m_operatorJoystick, X);
        m_loadFrisbee.whileTrue(new PushPullShooterPiston(feeder, shooter));
        m_tipOver = new JoystickButton(m_operatorJoystick, SQUARE);
        m_tipOver.onTrue(new TipRobotOver(climber));
        m_retract = new JoystickButton(m_operatorJoystick, TRIANGLE);
        m_retract.onTrue(new RetractClimberPiston(climber));
        m_closeBottomGrip = new JoystickButton(m_operatorJoystick, SELECT);
        m_closeBottomGrip.onTrue(new CloseBottomGrip(gripper));
        m_openBottomGrip = new JoystickButton(m_operatorJoystick, START);
        m_openBottomGrip.onTrue(new OpenAllGrippers(gripper));
        m_closeTopGrip = new JoystickButton(m_operatorJoystick, HOME);
        m_closeTopGrip.onTrue(new CloseTopGrip());
        m_climb = new JoystickButton(m_operatorJoystick, CIRCLE);
        m_climb.whileTrue(new StartClimbMotors(climber));
        m_stopClimbing = new JoystickButton(m_operatorJoystick, L2);
        m_stopClimbing.onTrue(new StopClimbMotors(climber));
        m_stopClimbing2 = new JoystickButton(m_operatorJoystick, R2);
        m_stopClimbing2.onTrue(new StopClimbMotors(climber));

        //Raising and lowering the blocker using a toggle
        m_toggleBlocker = new JoystickButton(m_operatorJoystick, L1);
        m_toggleBlocker.onTrue(new Blocking(feeder));
    }

    public Joystick getDrivingJoystick() {

        return m_driverJoystick;

    } //end getDriverJoystick
}
