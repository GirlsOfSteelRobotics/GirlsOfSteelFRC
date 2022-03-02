package com.gos.recycle_rush.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.recycle_rush.robot.commands.autonomous.AutoCollector;
import com.gos.recycle_rush.robot.commands.autonomous.AutoDriveBackwards;
import com.gos.recycle_rush.robot.commands.autonomous.AutoDriveForward;
import com.gos.recycle_rush.robot.commands.autonomous.AutoDriveLeft;
import com.gos.recycle_rush.robot.commands.autonomous.AutoDriveRight;
import com.gos.recycle_rush.robot.commands.autonomous.AutoFirstPickup;
import com.gos.recycle_rush.robot.commands.autonomous.AutoLift;
import com.gos.recycle_rush.robot.commands.autonomous.AutoOneTote;
import com.gos.recycle_rush.robot.commands.autonomous.AutoPlow;
import com.gos.recycle_rush.robot.commands.autonomous.AutoToteAndContainer;
import com.gos.recycle_rush.robot.commands.autonomous.AutoTurnClockwise;
import com.gos.recycle_rush.robot.commands.autonomous.AutoTurnCounterClockwise;
import com.gos.recycle_rush.robot.commands.autonomous.Lifting;
import com.gos.recycle_rush.robot.commands.autonomous.Release;
import com.gos.recycle_rush.robot.commands.drive.DriveByJoystick;
import com.gos.recycle_rush.robot.commands.lifter.LiftByJoystick;
import com.gos.recycle_rush.robot.subsystems.Chassis;
import com.gos.recycle_rush.robot.subsystems.Collector;
import com.gos.recycle_rush.robot.subsystems.Lifter;
import com.gos.recycle_rush.robot.subsystems.Shack;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

    private final Chassis m_chassis;
    private final Collector m_collector;
    private final Lifter m_lifter;
    private final OI m_oi;
    private final Shack m_shack;
    private final SendableChooser m_autoChooser;
    private Command m_autonomousCommand;

    public Robot() {
        m_chassis = new Chassis();
        m_collector = new Collector();
        m_lifter = new Lifter();
        m_shack = new Shack();
        m_oi = new OI(m_chassis, m_collector, m_shack);
        m_autoChooser = new SendableChooser();
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        m_chassis.resetGyro();

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

        SmartDashboard.putData("Auto mode", m_autoChooser);
        SmartDashboard.putData("AutoCollector", new AutoCollector(m_collector));
        SmartDashboard.putData("AutoDriveBackwards", new AutoDriveBackwards(m_chassis, 50));
        SmartDashboard.putData("AutoDriveForward", new AutoDriveForward(m_chassis, 50));
        SmartDashboard.putData("AutoDriveLeft", new AutoDriveLeft(m_chassis, 50));
        SmartDashboard.putData("AutoDriveRight", new AutoDriveRight(m_chassis, 50));
        SmartDashboard.putData("AutoFirstPickup", new AutoFirstPickup(m_chassis, 10));
        SmartDashboard.putData("AutoLift 0", new AutoLift(m_lifter, Lifter.DISTANCE_ZERO_TOTES));
        SmartDashboard.putData("AutoLift 1", new AutoLift(m_lifter, Lifter.DISTANCE_ONE_TOTE));
        SmartDashboard.putData("AutoLift 2", new AutoLift(m_lifter, Lifter.DISTANCE_TWO_TOTES));
        SmartDashboard.putData("AutoLift 3", new AutoLift(m_lifter, Lifter.DISTANCE_THREE_TOTES));
        SmartDashboard.putData("AutoLift 4", new AutoLift(m_lifter, Lifter.DISTANCE_FOUR_TOTES));
        SmartDashboard.putData("AutoOneTote", new AutoOneTote(m_chassis, m_collector, m_shack, m_lifter));
        SmartDashboard.putData("AutoPlow", new AutoPlow(m_chassis, m_shack, m_collector, m_lifter));
        SmartDashboard.putData("AutoToteAndContaienr", new AutoToteAndContainer(m_chassis, m_shack, m_collector, m_lifter));
        SmartDashboard.putData("AutoTurnClockwise", new AutoTurnClockwise(m_chassis));
        SmartDashboard.putData("AutoTurnCounterClockwise", new AutoTurnCounterClockwise(m_chassis));
        SmartDashboard.putData("Lifting", new Lifting(m_shack, m_collector, m_lifter));
        SmartDashboard.putData("Release", new Release(m_shack, m_collector));

        // Default commands
        m_lifter.setDefaultCommand(new LiftByJoystick(m_oi, m_lifter));
        m_chassis.setDefaultCommand(new DriveByJoystick(m_oi, m_chassis));

    }

    @Override
    public void disabledPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        // schedule the autonomous command (example)
        m_autonomousCommand = (Command) m_autoChooser.getSelected();
        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
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
        CommandScheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {
    }

}
