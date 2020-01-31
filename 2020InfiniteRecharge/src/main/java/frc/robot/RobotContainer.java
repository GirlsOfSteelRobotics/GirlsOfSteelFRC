/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                                                */
/* Open Source Software - may be modified and shared by FRC teams. The code     */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                                                                                             */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.DriveByJoystick;
import frc.robot.commands.autonomous.DriveDistance;
import frc.robot.commands.autonomous.GoToPosition;
import frc.robot.commands.autonomous.TimedDriveStraight;
import frc.robot.commands.autonomous.TurnToAngle;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterConveyor;
import frc.robot.subsystems.ShooterIntake;
import frc.robot.subsystems.Winch;
import frc.robot.subsystems.Lift;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
@SuppressWarnings("PMD.SingularField")
public class RobotContainer {
    // The robot's subsystems and commands are defined here...

    private final Chassis m_chassis;
    private final ControlPanel m_controlPanel;
    private final Limelight m_limelight;
    private final Shooter m_shooter;
    private final ShooterConveyor m_shooterConveyor;
    private final ShooterIntake m_shooterIntake;
    private final Winch m_winch;
    private final Lift m_lift;
    private final OI m_oi;
    private final SendableChooser<Command> m_sendableChooser;

    /**
     * The container for the robot.    Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {

        //Add subsystems in this section:
        m_chassis = new Chassis();
        m_controlPanel = new ControlPanel();
        m_limelight = new Limelight();
        m_shooter = new Shooter();
        m_shooterConveyor = new ShooterConveyor();
        m_shooterIntake = new ShooterIntake();
        m_winch = new Winch();
        m_lift = new Lift();
        m_sendableChooser = new SendableChooser<>();
        double dX = 27;
        double dY = 13.5;
        m_sendableChooser.addOption("Test. Go To Position", new GoToPosition(m_chassis, 0.0, 0.0, 1, 1));
        m_sendableChooser.addOption("Test. Go To Position Right", new GoToPosition(m_chassis, dX, 0.0, 1, 1));
        m_sendableChooser.addOption("Test. Go To Position Left", new GoToPosition(m_chassis, -dX, 0.0, 1, 1));
        m_sendableChooser.addOption("Test. Go To Position Up", new GoToPosition(m_chassis, 0.0, dY, 1, 1));
        m_sendableChooser.addOption("Test. Go To Position Down", new GoToPosition(m_chassis, 0.0, -dY, 1, 1));
        m_sendableChooser.addOption("Test. Go To Position Up-Left", new GoToPosition(m_chassis, -dX, dY, 1, 1));
        m_sendableChooser.addOption("Test. Go To Position Up-Right", new GoToPosition(m_chassis, dX, dY, 1, 1));
        m_sendableChooser.addOption("Test. Go To Position Down-Left", new GoToPosition(m_chassis, -dX, -dY, 1, 1));
        m_sendableChooser.addOption("Test. Go To Position Down-Right", new GoToPosition(m_chassis, dX, -dY, 1, 1));
        m_sendableChooser.addOption("Test. Turn To Angle Positive", new TurnToAngle(m_chassis, 90, 1));
        m_sendableChooser.addOption("Test. Turn To Angle Negative", new TurnToAngle(m_chassis, -90, 1));
        m_sendableChooser.addOption("Test. Drive Distance Forward", new DriveDistance(m_chassis, 60, 1));
        m_sendableChooser.addOption("Test. Drive Distance Backward", new DriveDistance(m_chassis, -60, 1));
        m_sendableChooser.addOption("Test. Timed Drive Straight Forward", new TimedDriveStraight(m_chassis, 2, 0.5));
        m_sendableChooser.addOption("Test. Timed Drive Straight Backward", new TimedDriveStraight(m_chassis, 2, -0.5));
        SmartDashboard.putData("Auto Mode", m_sendableChooser);

        // This line has to be after all of the subsystems are created!
        m_oi = new OI(m_chassis, m_limelight, m_shooter, m_shooterIntake, m_shooterConveyor, m_lift, m_winch);

        m_chassis.setDefaultCommand(new DriveByJoystick(m_chassis, m_oi));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return m_sendableChooser.getSelected();
    }

    public Chassis getChassis()    {
        return m_chassis;
    }
}
