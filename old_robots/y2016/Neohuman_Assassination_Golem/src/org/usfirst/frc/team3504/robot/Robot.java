package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3504.robot.commands.DriveByJoystick;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoDoNothing;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoDriveBackwards;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoLowBarAndScore;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoLowBarAndTurn;
import org.usfirst.frc.team3504.robot.commands.autonomous.FlapThenLowBar;
import org.usfirst.frc.team3504.robot.subsystems.Camera;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;
import org.usfirst.frc.team3504.robot.subsystems.Claw;
import org.usfirst.frc.team3504.robot.subsystems.Flap;
import org.usfirst.frc.team3504.robot.subsystems.Pivot;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;
import org.usfirst.frc.team3504.robot.subsystems.Shooter;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    /* Declare variables for all subsystems and OI here,
     * but don't initialize them until robotInit() is called.
     * (Initializing them here leads to very unclear error messages
     * if any of them throw an exception.)
     */
    private final OI m_oi;
    private final Chassis m_chassis;
    private final Shifters m_shifters;
    private final Flap m_flap;
    private final Claw m_claw;
    private final Pivot m_pivot;
    private final Camera m_camera;
//	private final LEDLights ledlights;
    private final Shooter m_shooter;

    private Command m_autonomousCommand;
    private final SendableChooser<Command> m_autoChooser;

    public Robot() {
        // Start by initializing each subsystem
        m_shifters = new Shifters();
        m_chassis = new Chassis(m_shifters);
        m_flap = new Flap();
        m_pivot = new Pivot();
        m_camera = new Camera();
        //ledlights = new LEDLights();

        if (RobotMap.USING_CLAW) {
            m_claw = new Claw();
            m_shooter = null;
        }
        else {
            m_claw = null;
            m_shooter = new Shooter();
        }

        // After all subsystems are set up, create the Operator Interface.
        // If you call new OI() before the subsystems are created, it will fail.
        m_oi = new OI(m_chassis, m_shifters, m_claw, m_shooter, m_flap, m_pivot, m_camera);


        // Populate the SmartDashboard menu for choosing the autonomous command to run
        m_autoChooser = new SendableChooser<>();
        //drive backwards:
        m_autoChooser.addDefault("Do Nothing", new AutoDoNothing(m_chassis));
        m_autoChooser.addObject("Reach Defense", new AutoDriveBackwards(m_chassis, 101, .4)); //55
        m_autoChooser.addObject("LowBar", new FlapThenLowBar(m_chassis, m_flap,  156, .4)); //works 110
        m_autoChooser.addObject("Moat", new AutoDriveBackwards(m_chassis, 156, 1)); //works 60
        m_autoChooser.addObject("LowBar and Score", new AutoLowBarAndScore(m_chassis, m_flap, m_pivot, m_claw));
        m_autoChooser.addObject("LowBar and Turn", new AutoLowBarAndTurn(m_chassis, m_flap));
        //drive forwards:
        m_autoChooser.addObject("Rough Terrain", new AutoDriveBackwards(m_chassis, 156, .4)); //works 110
        m_autoChooser.addObject("Ramparts", new AutoDriveBackwards(m_chassis, 186, .4)); //140
        m_autoChooser.addObject("RockWall", new AutoDriveBackwards(m_chassis, 196, .6)); //works //150

        //autoChooser.addObject("Slow Drive", new AutoDriveSlowly(100));

        SmartDashboard.putData("Autochooser: ", m_autoChooser);
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        // Default commands
        m_chassis.setDefaultCommand( new DriveByJoystick(m_oi, m_chassis) );
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
     * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
     * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
     * below the Gyro
     *
     * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
     * or additional comparisons to the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() {
        m_autonomousCommand = m_autoChooser.getSelected();

        // schedule the autonomous command (example)
        if (m_autonomousCommand != null) { m_autonomousCommand.start(); }

 //       Robot.ledlights.autoLights();

        // Start the robot out in low gear when starting autonomous
        m_shifters.shiftLeft(Shifters.Speed.kLow);
        m_shifters.shiftRight(Shifters.Speed.kLow);
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
       // Robot.chassis.ahrsToSmartDashboard();
        SmartDashboard.putNumber("FlapEncoder", m_flap.getFlapEncoderDistance());
        SmartDashboard.putNumber("Pivot Encoder", m_pivot.getEncoderDistance());
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (m_autonomousCommand != null) { m_autonomousCommand.cancel(); }

        // Start the robot out in low gear when starting teleop
        m_shifters.shiftLeft(Shifters.Speed.kLow);
        m_shifters.shiftRight(Shifters.Speed.kLow);

        m_chassis.resetEncoderDistance();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
      // Robot.chassis.ahrsToSmartDashboard();
       SmartDashboard.putNumber("FlapEncoder",m_flap.getFlapEncoderDistance());
       SmartDashboard.putNumber("Pivot Encoder", m_pivot.getEncoderDistance());
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {
    }
}
