// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.rebuilt;


import com.gos.lib.properties.PropertyManager;
import com.gos.rebuilt.commands.FireOnTheRunCommand;
import com.gos.rebuilt.commands.StaringCommand;
import com.gos.rebuilt.subsystems.ClimberSubsystem;

import com.gos.rebuilt.autos.AutoFactory;
import com.gos.rebuilt.choreo_gen.DebugPathsTab;
import com.gos.rebuilt.commands.CombinedCommand;
import com.gos.rebuilt.commands.JoystickFieldRelativeDriveCommand;
import com.gos.rebuilt.commands.PivotJoyCommand;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import com.gos.rebuilt.subsystems.FeederSubsystem;
import com.gos.rebuilt.subsystems.IntakeSubsystem;
import com.gos.rebuilt.subsystems.LEDSubsystem;
import com.gos.rebuilt.subsystems.PivotSubsystem;
import com.gos.rebuilt.subsystems.PizzaSubsystem;
import com.gos.rebuilt.subsystems.ShooterSubsystem;
import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import com.gos.rebuilt.subsystems.SuperStructureViz;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.generated.TunerConstants;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...

    // Replace with CommandPS4Controller or CommandJoystick if needed
    private final ClimberSubsystem m_climberSubsystem;
    private final CommandXboxController m_driverController; //NOPMD
    private final CommandXboxController m_operatorController;

    private final ChassisSubsystem m_chassis;
    private final IntakeSubsystem m_intakeSubsystem;
    private final ShooterSubsystem m_shooterSubsystem;
    private final PizzaSubsystem m_pizzaSubsystem;
    private final PivotSubsystem m_pivotSubsystem;
    private final FeederSubsystem m_feederSubsystem;
    private final LEDSubsystem m_ledSUbsystem; //NOPMD
    private final CombinedCommand m_combinedCommand;


    private final DebugPathsTab m_debugPathsTab;

    private final SuperStructureViz m_superStructureViz;  // NOPMD

    private final AutoFactory m_autoFactory;


    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {

        PropertyManager.setPurgeConstantPreferenceKeys(true);
        m_driverController = new CommandXboxController(0);
        m_climberSubsystem = new ClimberSubsystem();
        m_operatorController = new CommandXboxController(1);

        m_chassis = TunerConstants.createDrivetrain();
        m_intakeSubsystem = new IntakeSubsystem();
        m_shooterSubsystem = new ShooterSubsystem();
        m_pizzaSubsystem = new PizzaSubsystem();
        m_pivotSubsystem = new PivotSubsystem();
        m_feederSubsystem = new FeederSubsystem();
        m_ledSUbsystem = new LEDSubsystem(m_shooterSubsystem, m_chassis);
        m_combinedCommand = new CombinedCommand(m_chassis, m_feederSubsystem, m_pizzaSubsystem, m_intakeSubsystem, m_shooterSubsystem, m_pivotSubsystem);

        m_autoFactory = new AutoFactory(m_chassis, m_combinedCommand);

        if (RobotBase.isSimulation()) {
            DriverStationSim.setAllianceStationId(AllianceStationID.Blue1);
            DriverStationSim.setDsAttached(true);
            DriverStationSim.setEnabled(true);
            DriverStation.silenceJoystickConnectionWarning(true);

        }

        m_superStructureViz = new SuperStructureViz(m_pivotSubsystem, m_pizzaSubsystem, m_chassis, m_shooterSubsystem);

        // Configure the trigger bindings
        configureBindings();
        m_intakeSubsystem.addIntakeDebugCommands();
        m_shooterSubsystem.addShooterDebugCommands();
        m_pizzaSubsystem.addPizzaDebugCommands();
        m_chassis.addChassisDebugCommands();
        m_feederSubsystem.addFeederDebugCommands();
        m_climberSubsystem.addClimberDebugCommands();
        m_pivotSubsystem.addPivotDebugCommands();

        m_combinedCommand.createCombinedCommand(false);

        ShuffleboardTab tab = Shuffleboard.getTab("Shooter RPM");
        tab.add(m_shooterSubsystem.createShootFromDistanceCommand(m_chassis::getDistanceFromHub));

        m_debugPathsTab = new DebugPathsTab(m_chassis);
        m_debugPathsTab.addDebugPathsToShuffleBoard();


        PropertyManager.purgeExtraKeys();
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
        m_chassis.setDefaultCommand(new JoystickFieldRelativeDriveCommand(m_chassis, m_driverController));
        m_pivotSubsystem.setDefaultCommand(new PivotJoyCommand(m_pivotSubsystem, m_operatorController));
        //m_driverController.a().whileTrue(m_combinedCommand.shootBall());
        m_driverController.a().whileTrue(m_combinedCommand.shootBallNoAiming());
        m_driverController.rightBumper().whileTrue(new StaringCommand(m_chassis, m_driverController));


        m_driverController.povUp().whileTrue(m_climberSubsystem.createClimbingUpCommand());
        m_driverController.povDown().whileTrue(m_climberSubsystem.createClimbingDownCommand());

        m_driverController.rightTrigger().whileTrue(new FireOnTheRunCommand(m_driverController, m_chassis, m_feederSubsystem, m_pizzaSubsystem, m_shooterSubsystem));
        //pivot intake,= left trigger
        //shoot on the move = right trigger
        //feed/pass balls = b button\; retract = left bumper


    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        return m_autoFactory.getSelectedAuto();
    }
}
