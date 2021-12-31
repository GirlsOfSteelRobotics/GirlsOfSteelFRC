/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.ultimate_ascent;

import com.gos.ultimate_ascent.commands.RunClimberBackwards;
import com.gos.ultimate_ascent.subsystems.Chassis;
import com.gos.ultimate_ascent.subsystems.Climber;
import com.gos.ultimate_ascent.subsystems.DriveFlag;
import com.gos.ultimate_ascent.subsystems.Feeder;
import com.gos.ultimate_ascent.subsystems.Gripper;
import com.gos.ultimate_ascent.subsystems.Shooter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.ultimate_ascent.commands.Drive;
import com.gos.ultimate_ascent.commands.OpenAllGrippers;
import com.gos.ultimate_ascent.objects.AutonomousChooser;
import com.gos.ultimate_ascent.objects.PositionInfo;
import com.gos.ultimate_ascent.objects.ShooterCamera;
import com.gos.ultimate_ascent.tests.ShooterJags;

//import com.gos.girlsofsteel.commands.LightSensorFeeder;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Eve2013 extends IterativeRobot {

    private AutonomousChooser m_autonomous;

    private final OI m_oi;
    private final Feeder m_feeder;
    private final Shooter m_shooter;
    private final Climber m_climber;
    private final Chassis m_chassis;
    private final DriveFlag m_drive;
    private final Gripper m_gripper;

    @SuppressWarnings("PMD.CloseResource")
    public Eve2013() {
        m_feeder = new Feeder();
        m_shooter = new Shooter();
        m_climber = new Climber();
        m_chassis = new Chassis();
        m_drive = new DriveFlag();

        DigitalInput topOpenBottomCloseSwitch = new DigitalInput(RobotMap.TOP_GRIPPER_OPEN_BOTTOM_GRIPPER_CLOSE_SWITCH);
        DigitalInput topCloseMiddleOppenSwitch = new DigitalInput(RobotMap.TOP_GRIPPER_CLOSE_MIDDLE_GRIPPER_OPEN_SWITCH);
        m_gripper = new Gripper(topOpenBottomCloseSwitch, topCloseMiddleOppenSwitch, RobotMap.OPEN_TOP_GRIPPER_SOLENOID, RobotMap.CLOSE_TOP_GRIPPER_SOLENOID);

        m_oi = new OI(m_chassis, m_drive, m_climber, m_feeder, m_shooter, m_gripper);
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {

        // Initialize all subsystems
        PositionInfo.init();
        SmartDashboard.putBoolean("Reset Netbook", true);


        SmartDashboard.putData(new RunClimberBackwards(m_climber));

        SmartDashboard.putData(new ShooterJags(m_feeder, m_shooter));

        //Drivers
        m_autonomous = new AutonomousChooser(m_chassis, m_shooter, m_feeder);

        SmartDashboard.putBoolean("Press Shoot?", false);
        SmartDashboard.putNumber("Battery Voltage", RobotController.getBatteryVoltage());
        SmartDashboard.putString("SELECT", "close bottom grip");
        SmartDashboard.putString("SQUARE", "tilt");
        SmartDashboard.putString("CIRCLE", "start chains");
        SmartDashboard.putString("TRIANGLE", "retract tilt");
        SmartDashboard.putString("START", "opens grips");
        SmartDashboard.putString("R2 and L2", "stop climbing");
        SmartDashboard.putString("L1", "toggle blocker");

    }

    @Override
    public void autonomousInit() {
        //Testing

        //Drivers
        SmartDashboard.putBoolean("Press Shoot?", false);
        SmartDashboard.putNumber("Battery Voltage", RobotController.getBatteryVoltage());
        SmartDashboard.putString("SELECT", "close bottom grip");
        SmartDashboard.putString("SQUARE", "tilt");
        SmartDashboard.putString("CIRCLE", "start chains");
        SmartDashboard.putString("TRIANGLE", "retract tilt");
        SmartDashboard.putString("START", "opens grips");
        SmartDashboard.putString("R2 and L2", "stop climbing");
        SmartDashboard.putString("L1", "toggle blocker");
        new OpenAllGrippers(m_gripper).start();
        m_autonomous.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        //Testing
        //        SmartDashboard.putNumber("camera angle offset", ShooterCamera.getDiffAngle());

        //Drivers
        SmartDashboard.putBoolean("Press Shoot?", false);
        SmartDashboard.putNumber("Battery Voltage", RobotController.getBatteryVoltage());
        SmartDashboard.putString("SELECT", "close bottom grip");
        SmartDashboard.putString("SQUARE", "tilt");
        SmartDashboard.putString("CIRCLE", "start chains");
        SmartDashboard.putString("TRIANGLE", "retract tilt");
        SmartDashboard.putString("START", "opens grips");
        SmartDashboard.putString("R2 and L2", "stop climbing");
        SmartDashboard.putString("L1", "toggle blocker");
        Scheduler.getInstance().run();
        //        SmartDashboard.putBoolean("Camera is connected?", ShooterCamera.isConnected());
        //SmartDashboard.putBoolean("Camera is connected?", ClimberCamera.isConnected());
        //        SmartDashboard.putBoolean("Target is found?", ShooterCamera.foundTarget());
        //     SmartDashboard.putBoolean("Right position to shoot?", ShooterCamera.atShootPosition());
    }

    @Override
    public void teleopInit() {
        //Testing
        //        SmartDashboard.putNumber("camera angle offset", ShooterCamera.getDiffAngle());
        //  System.out.println("Gryo: " + CommandBase.chassis.getGyroAngle());

        //Drivers
        SmartDashboard.putBoolean("Press Shoot?", false);
        SmartDashboard.putNumber("Battery Voltage", RobotController.getBatteryVoltage());
        SmartDashboard.putString("SELECT", "close bottom grip");
        SmartDashboard.putString("SQUARE", "tilt");
        SmartDashboard.putString("CIRCLE", "start chains");
        SmartDashboard.putString("TRIANGLE", "retract tilt");
        SmartDashboard.putString("START", "opens grips");
        SmartDashboard.putString("R2 and L2", "stop climbing");
        SmartDashboard.putString("L1", "toggle blocker");
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        //new OpenAllGrippers().start();
        if (m_autonomous != null) {
            m_autonomous.cancel();
        }
        new Drive(m_oi, m_chassis, m_drive, 1.0, 0.5, true).start();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        //Testing
        //        SmartDashboard.putNumber("camera angle offset", ShooterCamera.getDiffAngle());
        //    System.out.println("Gryo: " + CommandBase.chassis.getGyroAngle());

        //Drivers
        SmartDashboard.putBoolean("Press Shoot?", m_shooter.isTimeToShoot());
        SmartDashboard.putNumber("Battery Voltage", RobotController.getBatteryVoltage());
        SmartDashboard.putString("SELECT", "close bottom grip");
        SmartDashboard.putString("SQUARE", "tilt");
        SmartDashboard.putString("CIRCLE", "start chains");
        SmartDashboard.putString("TRIANGLE", "retract tilt");
        SmartDashboard.putString("START", "opens grips");
        SmartDashboard.putString("R2 and L2", "stop climbing");
        SmartDashboard.putString("L1", "toggle blocker");
        Scheduler.getInstance().run();
        SmartDashboard.putBoolean("Robot Found Side Target", ShooterCamera.foundSideTarget());
        SmartDashboard.putBoolean("Robot Found Top Target", ShooterCamera.foundTopTarget());
        SmartDashboard.putNumber("Side Target Diff Angle", ShooterCamera.getSideDiffAngle());
        SmartDashboard.putNumber("Top Target Diff Angle", ShooterCamera.getTopDiffAngle());
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {
        //Testing
        //        SmartDashboard.putNumber("camera angle offset", ShooterCamera.getDiffAngle());

        //Drivers
        SmartDashboard.putBoolean("Press Shoot?", false);
        SmartDashboard.putNumber("Battery Voltage", RobotController.getBatteryVoltage());
        SmartDashboard.putString("SELECT", "close bottom grip");
        SmartDashboard.putString("SQUARE", "tilt");
        SmartDashboard.putString("CIRCLE", "start chains");
        SmartDashboard.putString("TRIANGLE", "retract tilt");
        SmartDashboard.putString("START", "opens grips");
        SmartDashboard.putString("R2 and L2", "stop climbing");
        SmartDashboard.putString("L1", "toggle blocker");
    }

    @Override
    public void disabledPeriodic() {
        //Testing
        //        SmartDashboard.putNumber("camera angle offset", ShooterCamera.getDiffAngle());
        //       System.out.println("Gryo: " + CommandBase.chassis.getGyroAngle());

        //Drivers
        SmartDashboard.putBoolean("Press Shoot?", false);
        SmartDashboard.putNumber("Battery Voltage", RobotController.getBatteryVoltage());
        SmartDashboard.putString("SELECT", "close bottom grip");
        SmartDashboard.putString("SQUARE", "tilt");
        SmartDashboard.putString("CIRCLE", "start chains");
        SmartDashboard.putString("TRIANGLE", "retract tilt");
        SmartDashboard.putString("START", "opens grips");
        SmartDashboard.putString("R2 and L2", "stop climbing");
        SmartDashboard.putString("L1", "toggle blocker");
    } //from KiwiDrive code

    @Override
    public void disabledInit() {
        //Testing
        //        SmartDashboard.putNumber("camera angle offset", ShooterCamera.getDiffAngle());
        //        System.out.println("Gryo: " + CommandBase.chassis.getGyroAngle());

        //Drivers
        SmartDashboard.putBoolean("Press Shoot?", false);
        SmartDashboard.putNumber("Battery Voltage", RobotController.getBatteryVoltage());
        SmartDashboard.putString("SELECT", "close bottom grip");
        SmartDashboard.putString("SQUARE", "tilt");
        SmartDashboard.putString("CIRCLE", "start chains");
        SmartDashboard.putString("TRIANGLE", "retract tilt");
        SmartDashboard.putString("START", "opens grips");
        SmartDashboard.putString("R2 and L2", "stop climbing");
        SmartDashboard.putString("L1", "toggle blocker");
    } //from KiwiDrive code
}
