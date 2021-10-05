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
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public enum DriveStyle {
        joystickArcade, gamePadArcade, joystickTank, gamePadTank, amazonDrive
    }

    ;

    private DriveStyle driveStyle;

    private Joystick operatorGamePad = new Joystick(0);
    private Joystick drivingGamePad = new Joystick(1);
    private Joystick amazonGamePad = new Joystick(1);
    //private Joystick drivingJoystickOne = new Joystick (1);
    //private Joystick drivingJoystickTwo = new Joystick (2);

    private JoystickButton shifterUp;
    private JoystickButton shifterDown;
    //private JoystickButton driveByDistanceLow;
    //private JoystickButton driveByMotionProfile;

    private JoystickButton liftUp;
    private JoystickButton liftDown;
    private JoystickButton liftToSwitch;
    private JoystickButton liftEnterRecovery;
    private JoystickButton liftToGround;

    private JoystickButton wristIn;
    private JoystickButton wristOut;

    private JoystickButton collect;
    private JoystickButton releaseFast;
    private JoystickButton releaseSlow;

    private JoystickButton climb;
    private JoystickButton unClimb;

    private JoystickButton motionMagic;

    public OI() {
        // Set which joystick axis is retrieved by its getTwist() method
        drivingGamePad.setTwistChannel(3);
        amazonGamePad.setTwistChannel(4);

        /* BUTTON MAPPING */

        shifterDown = new JoystickButton(amazonGamePad, 6);
        shifterUp = new JoystickButton(amazonGamePad, 5);

        liftUp = new JoystickButton(operatorGamePad, 6);
        liftDown = new JoystickButton(operatorGamePad, 8);
        liftToSwitch = new JoystickButton(operatorGamePad, 1);
        liftToGround = new JoystickButton(operatorGamePad, 2);
        liftEnterRecovery = new JoystickButton(operatorGamePad, 11);

        wristIn = new JoystickButton(operatorGamePad, 5);
        wristOut = new JoystickButton(operatorGamePad, 7);

        collect = new JoystickButton(operatorGamePad, 4);
        //releaseFast = new JoystickButton(operatorGamePad, 10);
        releaseSlow = new JoystickButton(operatorGamePad, 3);

        climb = new JoystickButton(operatorGamePad, 10);
        unClimb = new JoystickButton(operatorGamePad, 9);

        motionMagic = new JoystickButton(amazonGamePad, 4);

        /* BUTTON ACTIONS */

        shifterDown.whenPressed(new ShiftDown());
        shifterUp.whenPressed(new ShiftUp());
        //driveByDistanceLow.whenPressed(new DriveByDistance(50.0, Shifters.Speed.kLow));

        //turn left:
        //driveByMotionProfile.whenPressed(new DriveByMotionProfile("/home/lvuser/shortTurn2018.dat", "/home/lvuser/longTurn2018.dat"));

        liftUp.whileHeld(new LiftUp());
        liftDown.whileHeld(new LiftDown());
        liftToSwitch.whenPressed(new SwitchPosition());
        liftToGround.whenPressed(new CollectPosition());
        liftEnterRecovery.whenPressed(new LiftEnterRecoveryMode());

        wristIn.whileHeld(new WristIn());
        wristOut.whileHeld(new WristOut());

        collect.whileHeld(new Collect());
        //releaseFast.whileHeld(new ReleaseFast());
        releaseSlow.whileHeld(new ReleaseSlow());

        climb.whileHeld(new ClimbUp());
        unClimb.whileHeld(new ClimbDown());

        motionMagic.whenPressed(new DriveByMotionMagic(25.0, -90.0));
    }

    //	public double getGamePadLeftUpAndDown() {
    //		return -drivingGamePad.getY();
    //	}
    //
    //	public double getGamePadRightUpAndDown() {
    //		return -drivingGamePad.getTwist();
    //	}
    //
    //	public double getGamePadLeftSideToSide() {
    //		return drivingGamePad.getX();
    //	}
    //
    //	public double getGamePadRightSideToSide(){
    //		return drivingGamePad.getZ();
    //	}

    public double getAmazonLeftUpAndDown() {
        return -amazonGamePad.getY();
    }

    public double getAmazonRightSideToSide() {
        return amazonGamePad.getTwist();
    }

    //	public double getJoystickOneUpAndDown() {
    //		return -drivingJoystickOne.getY();
    //	}
    //
    //	public double getJoystickOneSideToSide() {
    //		return drivingJoystickOne.getX();
    //	}
    //
    //	public double getJoystickTwoUpAndDown() {
    //		return -drivingJoystickTwo.getY();
    //	}
    //
    //	public double getJoystickTwoSideToSide() {
    //		return drivingJoystickTwo.getX();
    //	}
    //
    public void setDriveStyle() {
        //		if (!RobotMap.dio1.get()) {
        //			driveStyle = DriveStyle.joystickArcade;
        //		} else if (!RobotMap.dio2.get()) {
        //			driveStyle = DriveStyle.gamePadArcade;
        //		} else if (!RobotMap.dio3.get()) {
        //			driveStyle = DriveStyle.joystickTank;
        //		} else if (!RobotMap.dio4.get()) {
        //			driveStyle = DriveStyle.gamePadTank;
        //		} else if (!RobotMap.dio5.get()) {
        //			driveStyle = DriveStyle.amazonDrive;
        //		} else {
        //			System.out.println("NO DRIVE MODE SELECTED. \nDefaulting to Joystick Arcade...");
        //			driveStyle = DriveStyle.joystickArcade;
        //		}

        driveStyle = DriveStyle.amazonDrive;
        System.out.println("Drive Mode: " + driveStyle);
    }

    public DriveStyle getDriveStyle() {
        return driveStyle;
    }

    public boolean isThrottle() {
        return amazonGamePad.getRawAxis(3) > .5;
    }

    public boolean isSpeedy() {
        return amazonGamePad.getRawAxis(2) > .5;
    }
}
