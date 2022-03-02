/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.power_up;

import com.gos.power_up.commands.ClimbDown;
import com.gos.power_up.commands.ClimbUp;
import com.gos.power_up.commands.Collect;
import com.gos.power_up.commands.CollectPosition;
import com.gos.power_up.commands.DriveByJoystick;
import com.gos.power_up.commands.DriveByMotionMagic;
import com.gos.power_up.commands.LiftDown;
import com.gos.power_up.commands.LiftEnterRecoveryMode;
import com.gos.power_up.commands.LiftUp;
import com.gos.power_up.commands.ReleaseSlow;
import com.gos.power_up.commands.ShiftDown;
import com.gos.power_up.commands.ShiftUp;
import com.gos.power_up.commands.SwitchPosition;
import com.gos.power_up.commands.WristIn;
import com.gos.power_up.commands.WristOut;
import com.gos.power_up.subsystems.Chassis;
import com.gos.power_up.subsystems.Climber;
import com.gos.power_up.subsystems.Collector;
import com.gos.power_up.subsystems.Lift;
import com.gos.power_up.subsystems.Shifters;
import com.gos.power_up.subsystems.Wrist;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
@SuppressWarnings("PMD.TooManyFields")
public class OI {

    public enum DriveStyle {
        joystickArcade, gamePadArcade, joystickTank, gamePadTank, amazonDrive
    }

    private DriveStyle m_driveStyle;

    private final Joystick m_operatorGamePad = new Joystick(0);
    private final Joystick m_drivingGamePad = new Joystick(1);
    private final Joystick m_amazonGamePad = new Joystick(1);
    //private Joystick drivingJoystickOne = new Joystick (1);
    //private Joystick drivingJoystickTwo = new Joystick (2);

    //private JoystickButton driveByDistanceLow;
    //private JoystickButton driveByMotionProfile;

    public OI(Chassis chassis, Shifters shifters, Lift lift, Wrist wrist, Climber climber, Collector collector) {
        // Set which joystick axis is retrieved by its getTwist() method
        m_drivingGamePad.setTwistChannel(3);
        m_amazonGamePad.setTwistChannel(4);

        /* BUTTON MAPPING */

        JoystickButton shifterDown = new JoystickButton(m_amazonGamePad, 6);
        JoystickButton shifterUp = new JoystickButton(m_amazonGamePad, 5);

        JoystickButton liftUp = new JoystickButton(m_operatorGamePad, 6);
        JoystickButton liftDown = new JoystickButton(m_operatorGamePad, 8);
        JoystickButton liftToSwitch = new JoystickButton(m_operatorGamePad, 1);
        JoystickButton liftToGround = new JoystickButton(m_operatorGamePad, 2);
        JoystickButton liftEnterRecovery = new JoystickButton(m_operatorGamePad, 11);

        JoystickButton wristIn = new JoystickButton(m_operatorGamePad, 5);
        JoystickButton wristOut = new JoystickButton(m_operatorGamePad, 7);

        JoystickButton collect = new JoystickButton(m_operatorGamePad, 4);
        //releaseFast = new JoystickButton(operatorGamePad, 10);
        JoystickButton releaseSlow = new JoystickButton(m_operatorGamePad, 3);

        JoystickButton climb = new JoystickButton(m_operatorGamePad, 10);
        JoystickButton unClimb = new JoystickButton(m_operatorGamePad, 9);

        JoystickButton motionMagic = new JoystickButton(m_amazonGamePad, 4);

        /* BUTTON ACTIONS */

        shifterDown.whenPressed(new ShiftDown(shifters));
        shifterUp.whenPressed(new ShiftUp(shifters));
        //driveByDistanceLow.whenPressed(new DriveByDistance(50.0, Shifters.Speed.kLow));

        //turn left:
        //driveByMotionProfile.whenPressed(new DriveByMotionProfile("/home/lvuser/shortTurn2018.dat", "/home/lvuser/longTurn2018.dat"));

        liftUp.whileHeld(new LiftUp(lift));
        liftDown.whileHeld(new LiftDown(lift));
        liftToSwitch.whenPressed(new SwitchPosition(lift, wrist));
        liftToGround.whenPressed(new CollectPosition(lift, wrist));
        liftEnterRecovery.whenPressed(new LiftEnterRecoveryMode(lift));

        wristIn.whileHeld(new WristIn(wrist));
        wristOut.whileHeld(new WristOut(wrist));

        collect.whileHeld(new Collect(collector));
        //releaseFast.whileHeld(new ReleaseFast());
        releaseSlow.whileHeld(new ReleaseSlow(collector));

        climb.whileHeld(new ClimbUp(climber));
        unClimb.whileHeld(new ClimbDown(climber));

        motionMagic.whenPressed(new DriveByMotionMagic(chassis, 25.0, -90.0));


        chassis.setDefaultCommand(new DriveByJoystick(chassis, m_amazonGamePad));
    }

    //    public double getGamePadLeftUpAndDown() {
    //        return -drivingGamePad.getY();
    //    }
    //
    //    public double getGamePadRightUpAndDown() {
    //        return -drivingGamePad.getTwist();
    //    }
    //
    //    public double getGamePadLeftSideToSide() {
    //        return drivingGamePad.getX();
    //    }
    //
    //    public double getGamePadRightSideToSide(){
    //        return drivingGamePad.getZ();
    //    }

    public double getAmazonLeftUpAndDown() {
        return -m_amazonGamePad.getY();
    }

    public double getAmazonRightSideToSide() {
        return m_amazonGamePad.getTwist();
    }

    //    public double getJoystickOneUpAndDown() {
    //        return -drivingJoystickOne.getY();
    //    }
    //
    //    public double getJoystickOneSideToSide() {
    //        return drivingJoystickOne.getX();
    //    }
    //
    //    public double getJoystickTwoUpAndDown() {
    //        return -drivingJoystickTwo.getY();
    //    }
    //
    //    public double getJoystickTwoSideToSide() {
    //        return drivingJoystickTwo.getX();
    //    }
    //


    public DriveStyle getDriveStyle() {
        return m_driveStyle;
    }

}
