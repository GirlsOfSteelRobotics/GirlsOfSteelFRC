// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.crescendo2024;

import com.gos.crescendo2024.auton.Autos;
import com.gos.crescendo2024.commands.ArmPivotJoystickCommand;
import com.gos.crescendo2024.commands.TeleopSwerveDrive;
import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // Subsystems
    private final ChassisSubsystem m_chassisSubsystem;

    private final ArmPivotSubsystem m_armPivotSubsystem;
    private final ShooterSubsystem m_shooterSubsystem;
    private final Autos m_autonomousFactory;


    private final CommandXboxController m_driverController =
        new CommandXboxController(Constants.DRIVER_JOYSTICK);

    private final CommandXboxController m_operatorController =
        new CommandXboxController(Constants.OPERATOR_JOYSTICK);


    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        m_chassisSubsystem = new ChassisSubsystem();

        m_shooterSubsystem = new ShooterSubsystem();

        m_autonomousFactory = new Autos();

        m_armPivotSubsystem = new ArmPivotSubsystem();


        // Configure the trigger bindings
        configureBindings();

        createTestCommands();

        if (RobotBase.isSimulation()) {
            DriverStationSim.setEnabled(true);
        }
    }

    private void createTestCommands() {
        ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("test commands");
        shuffleboardTab.add("shooterTuning", m_shooterSubsystem.createTunePercentShootCommand());
        shuffleboardTab.add("testShooter50", m_shooterSubsystem.createTestShooter(50));
        shuffleboardTab.add("testShooter100", m_shooterSubsystem.createTestShooter(100));
        shuffleboardTab.add("resetShooter", m_shooterSubsystem.createResetShooter());

        shuffleboardTab.add("Chassis to 45", m_chassisSubsystem.createTurnToAngleCommand(45));
        shuffleboardTab.add("Chassis to 90", m_chassisSubsystem.createTurnToAngleCommand(90));
        shuffleboardTab.add("Chassis to -180", m_chassisSubsystem.createTurnToAngleCommand(-180));
        shuffleboardTab.add("Chassis to -45", m_chassisSubsystem.createTurnToAngleCommand(-45));



    }



    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings() {
        m_chassisSubsystem.setDefaultCommand(new TeleopSwerveDrive(m_chassisSubsystem, m_driverController));

        m_armPivotSubsystem.setDefaultCommand(new ArmPivotJoystickCommand(m_armPivotSubsystem, m_operatorController));
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        return m_autonomousFactory.getSelectedAutonomous();
    }
}
