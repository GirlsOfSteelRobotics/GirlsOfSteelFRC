/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                                                */
/* Open Source Software - may be modified and shared by FRC teams. The code     */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                                                                                             */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.DriveByJoystick;
import frc.robot.commands.autonomous.GoToPosition;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterConveyor;
import frc.robot.subsystems.ShooterIntake;
import frc.robot.subsystems.Winch;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Camera;
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

    private final Camera m_camera;
    private final Chassis m_chassis;
    private final ControlPanel m_controlPanel;
    private final Limelight m_limelight;
    private final Shooter m_shooter;
    private final ShooterConveyor m_shooterConveyor;
    private final ShooterIntake m_shooterIntake;
    private final Winch m_winch;
    private final Lift m_lift;
    private final OI m_oi;

    /**
     * The container for the robot.    Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {

        //Add subsystems in this section:
        m_camera = new Camera ();
        // Todo: change drivercam constant to display camera feed
        m_chassis = new Chassis();
        m_controlPanel = new ControlPanel();
        m_limelight = new Limelight();
        m_shooter = new Shooter();
        m_shooterConveyor = new ShooterConveyor();
        m_shooterIntake = new ShooterIntake();
        m_winch = new Winch();
        m_lift = new Lift();


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
        return new GoToPosition(m_chassis, 27 * 12, -13.5 * 12, 5, 1);
    }

    public Chassis getChassis()    {
        return m_chassis;
    }
}
