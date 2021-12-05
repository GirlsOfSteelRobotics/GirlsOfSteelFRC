package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3504.robot.commands.AutonomousCommand;
import org.usfirst.frc.team3504.robot.commands.DriveByJoystick;
import org.usfirst.frc.team3504.robot.subsystems.AccessoryMotors;
import org.usfirst.frc.team3504.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3504.robot.subsystems.Manipulator;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    // Declare and initialize each subsystem
    // The constructor will create motor and sensor objects and do other setup
    public static final DriveSystem driveSystem = new DriveSystem();
    public static final AccessoryMotors accessoryMotors = new AccessoryMotors();
    public static final Shifters shifters = new Shifters();
    public static final Manipulator manipulator = new Manipulator();
    public static OI oi;

    private Command autonomousCommand;
    private SendableChooser chooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        // Initialize the operator interface (joysticks and button mappings)
        oi = new OI();

        // Allow the driver to choose the autonomous command from a SmartDashboard menu
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", new AutonomousCommand());
        // chooser.addObject("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", chooser);
        SmartDashboard.putData(new DriveByJoystick());
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    @Override
    public void disabledInit(){

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
     * using the dashboard. The sendable chooser code works with the Java SmartDashboard.
     *
     * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
     */
    @Override
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();

        // schedule the autonomous command (example)
        if (autonomousCommand != null) { autonomousCommand.start(); }
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
        if (autonomousCommand != null) { autonomousCommand.cancel(); }
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
