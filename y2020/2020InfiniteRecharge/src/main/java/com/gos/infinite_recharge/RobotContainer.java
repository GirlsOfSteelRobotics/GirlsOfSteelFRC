/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                                                */
/* Open Source Software - may be modified and shared by FRC teams. The code     */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                                                                                             */
/*----------------------------------------------------------------------------*/

package com.gos.infinite_recharge;

import com.gos.lib.sensors.LidarLite;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.infinite_recharge.auto_modes.AutoModeFactory;
import com.gos.infinite_recharge.commands.DriveByJoystick;
import com.gos.lib.properties.PropertyManager;
import com.gos.infinite_recharge.subsystems.Chassis;
import com.gos.infinite_recharge.subsystems.ControlPanel;
import com.gos.infinite_recharge.subsystems.Limelight;
import com.gos.infinite_recharge.subsystems.Shooter;
import com.gos.infinite_recharge.subsystems.ShooterConveyor;
import com.gos.infinite_recharge.subsystems.ShooterIntake;
import com.gos.infinite_recharge.subsystems.Winch;
import com.gos.infinite_recharge.subsystems.Lift;
import com.gos.infinite_recharge.subsystems.Camera;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import com.gos.infinite_recharge.trajectory_modes.TrajectoryBarrel;
import com.gos.infinite_recharge.trajectory_modes.TrajectoryBounce;
import com.gos.infinite_recharge.trajectory_modes.TrajectoryGalacticSearchA;
import com.gos.infinite_recharge.trajectory_modes.TrajectoryGalacticSearchB;
import com.gos.infinite_recharge.trajectory_modes.TrajectorySlalom;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
@SuppressWarnings("PMD.SingularField")
public class RobotContainer {

    // The robot's subsystems are declared here
    private final Camera m_camera;
    private final Chassis m_chassis;
    private final ControlPanel m_controlPanel;
    private final Lift m_lift;
    private final LidarLite m_lidarLite;
    private final Limelight m_limelight;
    private final Shooter m_shooter;
    private final ShooterConveyor m_shooterConveyor;
    private final ShooterIntake m_shooterIntake;
    private final Winch m_winch;
    private final AutoModeFactory m_autoModeFactory;
    private final OI m_oi;

    /**
     * The container for the robot. Contains subsystems and the OI (joystick/gamepad) object.
     */
    public RobotContainer() {

        ShuffleboardTab driveDisplayTab = Shuffleboard.getTab("Driver Tab");

        // Create all subsystems in this section:
        m_camera = new Camera(driveDisplayTab);
        m_chassis = new Chassis();
        m_controlPanel = new ControlPanel();
        m_lift = new Lift();
        m_lidarLite = new LidarLite(Constants.DIGITAL_INPUT_LIDAR_LITE);
        m_limelight = new Limelight(driveDisplayTab, m_lidarLite);
        m_shooter = new Shooter(driveDisplayTab, m_limelight);
        m_shooterConveyor = new ShooterConveyor();
        m_shooterIntake = new ShooterIntake();
        m_winch = new Winch(isEuropa());
        m_autoModeFactory = new AutoModeFactory(m_chassis, m_shooter, m_shooterConveyor, m_shooterIntake, m_limelight);

        Shuffleboard.selectTab("Driver Tab");

        // This line has to be after all of the subsystems are created!
        m_oi = new OI(m_chassis, m_controlPanel, m_limelight, m_camera, m_shooter, m_shooterIntake, m_shooterConveyor, m_lift, m_winch);

        // Default command for the chassis is to drive using the joysticks on the driver's gamepad
        m_chassis.setDefaultCommand(new DriveByJoystick(m_chassis, m_oi));

        // Add some testing options to the Autonomous Chooser on the dashboard

        CommandScheduler.getInstance().onCommandInitialize(command ->  System.out.println(command.getName() + " is starting"));
        CommandScheduler.getInstance().onCommandFinish(command ->  System.out.println(command.getName() + " has ended"));
        CommandScheduler.getInstance().onCommandInterrupt(command ->  System.out.println(command.getName() + " was interrupted"));

        PropertyManager.purgeExtraKeys();
        SmartDashboard.putData("SlalomPath", (Sendable) TrajectorySlalom.slalom(m_chassis));
        SmartDashboard.putData("BouncePath", (Sendable) TrajectoryBounce.bounce(m_chassis));
        SmartDashboard.putData("BarrelPath", (Sendable) TrajectoryBarrel.barrel(m_chassis));
        SmartDashboard.putData("GalacticSearchARed", (Sendable) TrajectoryGalacticSearchA.galacticSearchARed(m_chassis));
        SmartDashboard.putData("GalacticSearchABlue", (Sendable) TrajectoryGalacticSearchA.galacticSearchABlue(m_chassis));
        SmartDashboard.putData("GalacticSearchBRed", (Sendable) TrajectoryGalacticSearchB.galacticSearchBRed(m_chassis));
        SmartDashboard.putData("GalacticSearchBBlue", (Sendable) TrajectoryGalacticSearchB.galacticSearchBBlue(m_chassis));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // SequentialCommandGroup group = new SequentialCommandGroup();
        // group.addCommands(new SetStartingPosition(m_chassis, 27 * 12, -13.5 * 12, 0));
        // if (m_sendableChooser.getSelected() != null) {
        //     group.addCommands(m_sendableChooser.getSelected());
        // }
        // return group;
        return m_autoModeFactory.getAutonomousMode();
    }

    public Chassis getChassis()    {
        return m_chassis;
    }

    DigitalInput m_digitalInput = new DigitalInput(Constants.DIGITAL_INPUT_EUROPA);

    public final boolean isEuropa() {
        System.out.println("Is this Europa? " + !m_digitalInput.get());
        return !m_digitalInput.get();
    }

}
