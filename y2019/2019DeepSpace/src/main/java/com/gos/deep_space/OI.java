/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space;

import com.gos.deep_space.commands.BabyDriveBackwards;
import com.gos.deep_space.commands.BabyDriveForward;
import com.gos.deep_space.commands.ClimberManual;
import com.gos.deep_space.commands.ClimberToSetPoint;
import com.gos.deep_space.commands.CollectorCollect;
import com.gos.deep_space.commands.CollectorRelease;
import com.gos.deep_space.commands.HatchCollect;
import com.gos.deep_space.commands.HatchRelease;
import com.gos.deep_space.commands.PivotManual;
import com.gos.deep_space.commands.PivotToGround;
import com.gos.deep_space.commands.PivotToRocket;
import com.gos.deep_space.commands.PivotToShip;
import com.gos.deep_space.subsystems.Climber;
import com.gos.deep_space.subsystems.Pivot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public Joystick drivingPad;
    public Joystick operatingPad;

    private final JoystickButton backUp;
    private final JoystickButton backDown;

    private final JoystickButton frontUp;
    private final JoystickButton frontDown;

    private final JoystickButton allUp;
    private final JoystickButton allDown;

    private final POVButton toSecondUp;
    private final POVButton toThirdUp;

    private POVButton allToZero;
    private final POVButton frontToZero;
    private final POVButton backToZero;

    private POVButton robotToThird;
    private POVButton robotToSecond;

    private JoystickButton lidarDrive; // testing purposes only

    private final JoystickButton babyDriveForward;
    private final JoystickButton babyDriveBackward;

    private final JoystickButton collect;
    private final JoystickButton release;

    private final JoystickButton hatchCollect;
    private final JoystickButton hatchRelease;

    private final JoystickButton pivotUp;
    private final JoystickButton pivotDown;
    private final JoystickButton pivotGround;
    private final JoystickButton pivotRocket;
    private final JoystickButton pivotShip;

    private JoystickButton driveByVision;

    public OI() {
        drivingPad = new Joystick(0);
        operatingPad = new Joystick(1);

        // Climber buttons
        frontToZero = new POVButton(drivingPad, 90);
        frontToZero.whenPressed(new ClimberToSetPoint(Climber.FIRST_GOAL_POS, Climber.ClimberType.Front));

        backToZero = new POVButton(drivingPad, 270);
        backToZero.whenPressed(new ClimberToSetPoint(Climber.FIRST_GOAL_POS, Climber.ClimberType.Back));

        toSecondUp = new POVButton(drivingPad, 180);
        toSecondUp.whenPressed(new ClimberToSetPoint(Climber.SECOND_GOAL_POS, Climber.ClimberType.All));

        toThirdUp = new POVButton(drivingPad, 0);
        toThirdUp.whenPressed(new ClimberToSetPoint(Climber.THIRD_GOAL_POS, Climber.ClimberType.All));

        frontDown = new JoystickButton(drivingPad, 2);
        frontDown.whileHeld(new ClimberManual(false, Climber.ClimberType.Front));

        backDown = new JoystickButton(drivingPad, 4);
        backDown.whileHeld(new ClimberManual(false, Climber.ClimberType.Back));

        allUp = new JoystickButton(drivingPad, 5);
        allUp.whileHeld(new ClimberManual(true, Climber.ClimberType.All));

        allDown = new JoystickButton(drivingPad, 6);
        allDown.whileHeld(new ClimberManual(false, Climber.ClimberType.All));

        frontUp = new JoystickButton(drivingPad, 1);
        frontUp.whileHeld(new ClimberManual(true, Climber.ClimberType.Front));

        backUp = new JoystickButton(drivingPad, 3);
        backUp.whileHeld(new ClimberManual(true, Climber.ClimberType.Back));


        // allToZero = new POVButton(drivingPad, 180);
        // allToZero.whenPressed(new ClimberAllToZero());

        // robotToSecond = new POVButton(drivingPad, 270);
        // robotToSecond.whenPressed(new RobotToPlatform(2));

        // robotToThird = new POVButton(drivingPad, 90);
        // robotToThird.whenPressed(new RobotToPlatform(3));

        //Lidar button (testing purposes only)
        // lidarDrive = new JoystickButton(drivingPad, 9);
        // lidarDrive.whenPressed(new LidarDriveForward(82, true));

        // BabyDrive buttons
        babyDriveForward = new JoystickButton(drivingPad, 8);
        babyDriveForward.whileHeld(new BabyDriveForward());

        babyDriveBackward = new JoystickButton(drivingPad, 7);
        babyDriveBackward.whileHeld(new BabyDriveBackwards());

        // Collector buttons
        collect = new JoystickButton(operatingPad, 5);
        collect.whileHeld(new CollectorCollect());

        release = new JoystickButton(operatingPad, 6);
        release.whileHeld(new CollectorRelease());

        // Hatch buttons
        hatchCollect = new JoystickButton(operatingPad, 7);
        hatchCollect.whileHeld(new HatchCollect());

        hatchRelease = new JoystickButton(operatingPad, 8);
        hatchRelease.whileHeld(new HatchRelease());

        // Pivot buttons
        // negative is down, positive is up
        // must start up
        pivotUp = new JoystickButton(operatingPad, 2);
        pivotUp.whileHeld(new PivotManual(Pivot.PivotDirection.Up));

        pivotDown = new JoystickButton(operatingPad, 1);
        pivotDown.whileHeld(new PivotManual(Pivot.PivotDirection.Down));

        pivotGround = new JoystickButton(operatingPad, 3);
        pivotGround.whenPressed(new PivotToGround());

        pivotRocket = new JoystickButton(operatingPad, 4);
        pivotRocket.whenPressed(new PivotToRocket());

        pivotShip = new JoystickButton(operatingPad, 10);
        pivotShip.whenPressed(new PivotToShip());

        // DriveByVision button
        // driveByVision = new JoystickButton (operatingPad, 9);
        // driveByVision.whenPressed(new DriveByVision());
    }

    public double getLeftUpAndDown() {
        return -drivingPad.getY();
    }

    public double getRightSideToSide() {
        return drivingPad.getRawAxis(4);
    }

}
