// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.chargedup;


import com.gos.chargedup.autonomous.AutonomousFactory;
import com.gos.chargedup.commands.CurvatureDriveCommand;
import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;

import com.gos.chargedup.subsystems.ClawSubsystem;
import edu.wpi.first.wpilibj.RobotBase;
import com.gos.chargedup.subsystems.LEDManagerSubsystem;

import com.gos.chargedup.subsystems.IntakeSubsystem;
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
    // The robot's subsystems and commands are defined here...

    private final ClawSubsystem m_claw = new ClawSubsystem();

    private final IntakeSubsystem m_intake = new IntakeSubsystem();
    private final ChassisSubsystem m_chassisSubsystem = new ChassisSubsystem();

    private final ArmSubsystem m_arm = new ArmSubsystem();
    private final AutonomousFactory m_autonomousFactory;

    // Replace with CommandPS4Controller or CommandJoystick if needed
    private final CommandXboxController m_driverController =
        new CommandXboxController(Constants.DRIVER_CONTROLLER_PORT);

    private final CommandXboxController m_operatorController =
        new CommandXboxController(Constants.OPERATOR_CONTROLLER_PORT);

    private final LEDManagerSubsystem m_ledManagerSubsystem = new LEDManagerSubsystem(m_driverController); //NOPMD


    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the trigger bindings
        configureBindings();

        m_autonomousFactory = new AutonomousFactory(m_exampleSubsystem, m_chassisSubsystem);
        m_autonomousFactory = new AutonomousFactory();

        if (RobotBase.isSimulation()) {
            DriverStationSim.setEnabled(true);
        }

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
        m_operatorController.x().whileTrue(m_claw.createMoveClawIntakeInCommand());
        m_operatorController.y().whileTrue(m_claw.createMoveClawIntakeOutCommand());

        // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
        // cancelling on release.
        m_chassisSubsystem.setDefaultCommand(new CurvatureDriveCommand(m_chassisSubsystem, m_driverController));

        m_driverController.a().whileTrue(m_chassisSubsystem.createAutoEngageCommand());

        m_operatorController.a().whileTrue(m_arm.commandPivotArmUp());
        m_operatorController.b().whileTrue(m_arm.commandPivotArmDown());
        m_driverController.b().whileTrue((m_intake.createExtendSolenoidCommand()));
        m_driverController.a().whileTrue((m_intake.createRetractSolenoidCommand()));
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        return m_autonomousFactory.getAutonomousCommand();
    }
}
