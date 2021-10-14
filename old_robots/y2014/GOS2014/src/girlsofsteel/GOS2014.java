/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package girlsofsteel;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.commands.ArcadeDrive;
import girlsofsteel.commands.CommandBase;
import girlsofsteel.commands.DoNothing;
import girlsofsteel.commands.KickerUsingLimitSwitch;
import girlsofsteel.commands.ManualPositionPIDTuner;
import girlsofsteel.commands.TestKickerEncoder;
import girlsofsteel.commands.TuneManipulatorPID;
import girlsofsteel.objects.AutonomousChooser;
import girlsofsteel.objects.Camera;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class GOS2014 extends IterativeRobot {

    AutonomousChooser auto;
    Command autonomousCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // instantiate the command used for the autonomous period
        autonomousCommand = new DoNothing();

        auto = new AutonomousChooser();

        // Initialize all subsystems
        CommandBase.init();
        Configuration.configureForRobot(Configuration.COMPETITION_ROBOT);
        //SmartDashboard.putData(new TestKickerEncoder());

        SmartDashboard.putData(new KickerUsingLimitSwitch(-1, true));
        SmartDashboard.putData(new TestKickerEncoder());
        SmartDashboard.putData(new TuneManipulatorPID());
        SmartDashboard.putData(new ManualPositionPIDTuner());

   //     SmartDashboard.putData(new FullTester());
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
        //new AutonomousMobility().start();
        // new AutonomousLowGoalHot().start();
        //auto.start();
        autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        SmartDashboard.putBoolean("Robot Is Hot", Camera.isGoalHot());
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.

        autonomousCommand.cancel();
        if(auto != null) {
            auto.cancel();
        }
        new ArcadeDrive().start(); //Starts arcade drive automatically
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        SmartDashboard.putBoolean("Robot Is Hot", Camera.isGoalHot());
        Scheduler.getInstance().run();
        //Configuration.configureForRobot((int) SmartDashboard.getNumber("Robot Configuration"));
//        SmartDashboard.putNumber("robotCameraAngle",(double)CommandBase.camera.getVerticalAngleOffset());
//        System.out.println("Camera Angle: " + (double)CommandBase.camera.getVerticalAngleOffset());
//        SmartDashboard.putNumber("robotDistance",(double)CommandBase.camera.getDistanceToTarget());
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
