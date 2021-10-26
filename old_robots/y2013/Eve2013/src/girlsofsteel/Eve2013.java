/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package girlsofsteel;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.commands.CommandBase;
import girlsofsteel.commands.*;
import girlsofsteel.tests.*;
import girlsofsteel.objects.AutonomousChooser;
import girlsofsteel.objects.PositionInfo;
import girlsofsteel.objects.ShooterCamera;

//import girlsofsteel.commands.LightSensorFeeder;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Eve2013 extends IterativeRobot {

    AutonomousChooser autonomous;
    DriverStation driver = DriverStation.getInstance();

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

        // Initialize all subsystems
        CommandBase.init();
        PositionInfo.init();
        SmartDashboard.putBoolean("Reset Netbook", true);

        SmartDashboard.putData(new RunClimberBackwards());

        SmartDashboard.putData(new ShooterJags());

        //Drivers
        autonomous = new AutonomousChooser();

        SmartDashboard.putBoolean("Press Shoot?", false);
        SmartDashboard.putNumber ("Battery Voltage", driver.getBatteryVoltage());
        SmartDashboard.putString("SELECT", "close bottom grip");
        SmartDashboard.putString("SQUARE", "tilt");
        SmartDashboard.putString("CIRCLE", "start chains");
        SmartDashboard.putString("TRIANGLE", "retract tilt");
        SmartDashboard.putString("START", "opens grips");
        SmartDashboard.putString("R2 and L2", "stop climbing");
        SmartDashboard.putString("L1", "toggle blocker");

    }

    public void autonomousInit() {
        //Testing
//        SmartDashboard.putNumber("camera angle offset", ShooterCamera.getDiffAngle());

        //Drivers
        SmartDashboard.putBoolean("Press Shoot?", false);
        SmartDashboard.putNumber ("Battery Voltage", driver.getBatteryVoltage());
        SmartDashboard.putString("SELECT", "close bottom grip");
        SmartDashboard.putString("SQUARE", "tilt");
        SmartDashboard.putString("CIRCLE", "start chains");
        SmartDashboard.putString("TRIANGLE", "retract tilt");
        SmartDashboard.putString("START", "opens grips");
        SmartDashboard.putString("R2 and L2", "stop climbing");
        SmartDashboard.putString("L1", "toggle blocker");
        new OpenAllGrippers().start();
        autonomous.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        //Testing
//        SmartDashboard.putNumber("camera angle offset", ShooterCamera.getDiffAngle());

        //Drivers
        SmartDashboard.putBoolean("Press Shoot?", false);
        SmartDashboard.putNumber ("Battery Voltage", driver.getBatteryVoltage());
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

    public void teleopInit() {
        //Testing
//        SmartDashboard.putNumber("camera angle offset", ShooterCamera.getDiffAngle());
      //  System.out.println("Gryo: " + CommandBase.chassis.getGyroAngle());

        //Drivers
        SmartDashboard.putBoolean("Press Shoot?", false);
        SmartDashboard.putNumber ("Battery Voltage", driver.getBatteryVoltage());
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
        if (autonomous != null) {
            autonomous.cancel();
        }
        new Drive(1.0, 0.5, true).start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        //Testing
//        SmartDashboard.putNumber("camera angle offset", ShooterCamera.getDiffAngle());
    //    System.out.println("Gryo: " + CommandBase.chassis.getGyroAngle());

        //Drivers
        SmartDashboard.putBoolean("Press Shoot?", CommandBase.shooter.isTimeToShoot());
        SmartDashboard.putNumber ("Battery Voltage", driver.getBatteryVoltage());
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
        SmartDashboard.putNumber ("Side Target Diff Angle", ShooterCamera.getSideDiffAngle());
        SmartDashboard.putNumber ("Top Target Diff Angle", ShooterCamera.getTopDiffAngle());
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        //Testing
//        SmartDashboard.putNumber("camera angle offset", ShooterCamera.getDiffAngle());

        //Drivers
        SmartDashboard.putBoolean("Press Shoot?", false);
        SmartDashboard.putNumber ("Battery Voltage", driver.getBatteryVoltage());
        SmartDashboard.putString("SELECT", "close bottom grip");
        SmartDashboard.putString("SQUARE", "tilt");
        SmartDashboard.putString("CIRCLE", "start chains");
        SmartDashboard.putString("TRIANGLE", "retract tilt");
        SmartDashboard.putString("START", "opens grips");
        SmartDashboard.putString("R2 and L2", "stop climbing");
        SmartDashboard.putString("L1", "toggle blocker");
        LiveWindow.run();
    }

    public void disabledPeriodic() {
        //Testing
//        SmartDashboard.putNumber("camera angle offset", ShooterCamera.getDiffAngle());
 //       System.out.println("Gryo: " + CommandBase.chassis.getGyroAngle());

        //Drivers
        SmartDashboard.putBoolean("Press Shoot?", false);
        SmartDashboard.putNumber ("Battery Voltage", driver.getBatteryVoltage());
        SmartDashboard.putString("SELECT", "close bottom grip");
        SmartDashboard.putString("SQUARE", "tilt");
        SmartDashboard.putString("CIRCLE", "start chains");
        SmartDashboard.putString("TRIANGLE", "retract tilt");
        SmartDashboard.putString("START", "opens grips");
        SmartDashboard.putString("R2 and L2", "stop climbing");
        SmartDashboard.putString("L1", "toggle blocker");
    }//from KiwiDrive code

    public void disabledInit() {
        //Testing
//        SmartDashboard.putNumber("camera angle offset", ShooterCamera.getDiffAngle());
//        System.out.println("Gryo: " + CommandBase.chassis.getGyroAngle());

        //Drivers
        SmartDashboard.putBoolean("Press Shoot?", false);
        SmartDashboard.putNumber ("Battery Voltage", driver.getBatteryVoltage());
        SmartDashboard.putString("SELECT", "close bottom grip");
        SmartDashboard.putString("SQUARE", "tilt");
        SmartDashboard.putString("CIRCLE", "start chains");
        SmartDashboard.putString("TRIANGLE", "retract tilt");
        SmartDashboard.putString("START", "opens grips");
        SmartDashboard.putString("R2 and L2", "stop climbing");
        SmartDashboard.putString("L1", "toggle blocker");
    }//from KiwiDrive code
}
