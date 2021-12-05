package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.autonomous.AutoCollector;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoDriveBackwards;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoDriveForward;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoDriveLeft;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoDriveRight;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoFirstPickup;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoLift;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoOneTote;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoPlow;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoToteAndContainer;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoTurnClockwise;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoTurnCounterClockwise;
import org.usfirst.frc.team3504.robot.commands.autonomous.Release;
import org.usfirst.frc.team3504.robot.commands.autonomous.Lifting;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;
import org.usfirst.frc.team3504.robot.subsystems.Camera;
import org.usfirst.frc.team3504.robot.subsystems.Collector;
import org.usfirst.frc.team3504.robot.subsystems.Lifter;
import org.usfirst.frc.team3504.robot.subsystems.Shack;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    public static Chassis chassis;
    public static Camera camera;
    public static Collector collector;
    public static Lifter lifter;
    public static OI oi;
    public static Shack shack;
    private Command autonomousCommand;
    private SendableChooser autoChooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        chassis = new Chassis();
        //camera = new Camera();
        collector = new Collector();
        lifter = new Lifter();
        shack = new Shack();
        Robot.chassis.resetGyro();
        oi = new OI();
        autoChooser = new SendableChooser();
        //autoChooser.addDefault("Right Autonomous", new RightAutonomous());
        //autoChooser.addDefault("AutoDriveRight", new AutoDriveRight(100));
        //autoChooser.addObject("AutoDriveLeft", new AutoDriveLeft(150));
        //autoChooser.addObject("AutoDriveForward", new AutoDriveForward(50));
        //autoChooser.addObject("AutoDriveBackwards", new AutoDriveBackwards(50));
        //autoChooser.addObject("AutoOneTote", new AutoOneTote());
        //autoChooser.addObject("AutoTurnClockwise", new AutoTurnClockwise());
        //autoChooser.addObject("AutoTurnCounterClockwise", new AutoTurnCounterClockwise());
        //autoChooser.addObject("AutoPlow", new AutoPlow());
        //autoChooser.addObject("AutoToteAndContainer", new AutoToteAndContainer());
        //autoChooser.addObject("Lifting", new Lifting());
        //autoChooser.addObject("Release", new Release());

        SmartDashboard.putData("Auto mode", autoChooser);
        SmartDashboard.putData("AutoCollector", new AutoCollector());
        SmartDashboard.putData("AutoDriveBackwards", new AutoDriveBackwards(50));
        SmartDashboard.putData("AutoDriveForward", new AutoDriveForward(50));
        SmartDashboard.putData("AutoDriveLeft", new AutoDriveLeft(50));
        SmartDashboard.putData("AutoDriveRight", new AutoDriveRight(50));
        SmartDashboard.putData("AutoFirstPickup", new AutoFirstPickup());
        SmartDashboard.putData("AutoLift 0", new AutoLift(Lifter.DISTANCE_ZERO_TOTES));
        SmartDashboard.putData("AutoLift 1", new AutoLift(Lifter.DISTANCE_ONE_TOTE));
        SmartDashboard.putData("AutoLift 2", new AutoLift(Lifter.DISTANCE_TWO_TOTES));
        SmartDashboard.putData("AutoLift 3", new AutoLift(Lifter.DISTANCE_THREE_TOTES));
        SmartDashboard.putData("AutoLift 4", new AutoLift(Lifter.DISTANCE_FOUR_TOTES));
        SmartDashboard.putData("AutoOneTote", new AutoOneTote());
        SmartDashboard.putData("AutoPlow", new AutoPlow());
        SmartDashboard.putData("AutoToteAndContaienr", new AutoToteAndContainer());
        SmartDashboard.putData("AutoTurnClockwise", new AutoTurnClockwise());
        SmartDashboard.putData("AutoTurnCounterClockwise", new AutoTurnCounterClockwise());
        SmartDashboard.putData("Lifting", new Lifting());
        SmartDashboard.putData("Release", new Release());

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        // schedule the autonomous command (example)
        autonomousCommand = (Command) autoChooser.getSelected();
        if (autonomousCommand != null) {
            autonomousCommand.start();
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    /**
     * This function is called when the disabled button is hit. You can use it
     * to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit() {
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {
    }

}
