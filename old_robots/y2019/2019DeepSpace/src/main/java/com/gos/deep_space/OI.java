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
import com.gos.deep_space.commands.DriveByJoystick;
import com.gos.deep_space.commands.HatchCollect;
import com.gos.deep_space.commands.HatchRelease;
import com.gos.deep_space.commands.PivotManual;
import com.gos.deep_space.commands.PivotToGround;
import com.gos.deep_space.commands.PivotToRocket;
import com.gos.deep_space.commands.PivotToShip;
import com.gos.deep_space.subsystems.BabyDrive;
import com.gos.deep_space.subsystems.Blinkin;
import com.gos.deep_space.subsystems.Chassis;
import com.gos.deep_space.subsystems.Climber;
import com.gos.deep_space.subsystems.Collector;
import com.gos.deep_space.subsystems.Hatch;
import com.gos.deep_space.subsystems.Pivot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    private final Joystick m_drivingPad;
    private final Joystick m_operatingPad;

    public OI(Chassis chassis, BabyDrive babyDrive, Blinkin blinkin, Climber climber, Collector collector, Hatch hatch, Pivot pivot) {
        m_drivingPad = new Joystick(0);
        m_operatingPad = new Joystick(1);

        chassis.setDefaultCommand(new DriveByJoystick(chassis, m_drivingPad));

        // Climber buttons
        POVButton frontToZero = new POVButton(m_drivingPad, 90);
        frontToZero.onTrue(new ClimberToSetPoint(climber, blinkin, Climber.FIRST_GOAL_POS, Climber.ClimberType.FRONT));

        POVButton backToZero = new POVButton(m_drivingPad, 270);
        backToZero.onTrue(new ClimberToSetPoint(climber, blinkin, Climber.FIRST_GOAL_POS, Climber.ClimberType.BACK));

        POVButton toSecondUp = new POVButton(m_drivingPad, 180);
        toSecondUp.onTrue(new ClimberToSetPoint(climber, blinkin, Climber.SECOND_GOAL_POS, Climber.ClimberType.ALL));

        POVButton toThirdUp = new POVButton(m_drivingPad, 0);
        toThirdUp.onTrue(new ClimberToSetPoint(climber, blinkin, Climber.THIRD_GOAL_POS, Climber.ClimberType.ALL));

        JoystickButton frontDown = new JoystickButton(m_drivingPad, 2);
        frontDown.whileTrue(new ClimberManual(climber, false, Climber.ClimberType.FRONT));

        JoystickButton backDown = new JoystickButton(m_drivingPad, 4);
        backDown.whileTrue(new ClimberManual(climber, false, Climber.ClimberType.BACK));

        JoystickButton allUp = new JoystickButton(m_drivingPad, 5);
        allUp.whileTrue(new ClimberManual(climber, true, Climber.ClimberType.ALL));

        JoystickButton allDown = new JoystickButton(m_drivingPad, 6);
        allDown.whileTrue(new ClimberManual(climber, false, Climber.ClimberType.ALL));

        JoystickButton frontUp = new JoystickButton(m_drivingPad, 1);
        frontUp.whileTrue(new ClimberManual(climber, true, Climber.ClimberType.FRONT));

        JoystickButton backUp = new JoystickButton(m_drivingPad, 3);
        backUp.whileTrue(new ClimberManual(climber, true, Climber.ClimberType.BACK));


        // allToZero = new POVButton(drivingPad, 180);
        // allToZero.onTrue(new ClimberAllToZero());

        // robotToSecond = new POVButton(drivingPad, 270);
        // robotToSecond.onTrue(new RobotToPlatform(2));

        // robotToThird = new POVButton(drivingPad, 90);
        // robotToThird.onTrue(new RobotToPlatform(3));

        //Lidar button (testing purposes only)
        // lidarDrive = new JoystickButton(drivingPad, 9);
        // lidarDrive.onTrue(new LidarDriveForward(82, true));

        // BabyDrive buttons
        JoystickButton babyDriveForward = new JoystickButton(m_drivingPad, 8);
        babyDriveForward.whileTrue(new BabyDriveForward(babyDrive));

        JoystickButton babyDriveBackward = new JoystickButton(m_drivingPad, 7);
        babyDriveBackward.whileTrue(new BabyDriveBackwards(babyDrive));

        // Collector buttons
        JoystickButton collect = new JoystickButton(m_operatingPad, 5);
        collect.whileTrue(new CollectorCollect(collector));

        JoystickButton release = new JoystickButton(m_operatingPad, 6);
        release.whileTrue(new CollectorRelease(collector));

        // Hatch buttons
        JoystickButton hatchCollect = new JoystickButton(m_operatingPad, 7);
        hatchCollect.whileTrue(new HatchCollect(hatch));

        JoystickButton hatchRelease = new JoystickButton(m_operatingPad, 8);
        hatchRelease.whileTrue(new HatchRelease(hatch));

        // Pivot buttons
        // negative is down, positive is up
        // must start up
        JoystickButton pivotUp = new JoystickButton(m_operatingPad, 2);
        pivotUp.whileTrue(new PivotManual(pivot, Pivot.PivotDirection.UP));

        JoystickButton pivotDown = new JoystickButton(m_operatingPad, 1);
        pivotDown.whileTrue(new PivotManual(pivot, Pivot.PivotDirection.DOWN));

        JoystickButton pivotGround = new JoystickButton(m_operatingPad, 3);
        pivotGround.onTrue(new PivotToGround(pivot));

        JoystickButton pivotRocket = new JoystickButton(m_operatingPad, 4);
        pivotRocket.onTrue(new PivotToRocket(pivot));

        JoystickButton pivotShip = new JoystickButton(m_operatingPad, 10);
        pivotShip.onTrue(new PivotToShip(pivot));

        // DriveByVision button
        // driveByVision = new JoystickButton (operatingPad, 9);
        // driveByVision.onTrue(new DriveByVision());
    }
}
