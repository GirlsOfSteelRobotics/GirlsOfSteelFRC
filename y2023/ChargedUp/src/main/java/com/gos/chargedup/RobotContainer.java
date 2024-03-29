// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.chargedup;

import com.gos.chargedup.autonomous.AutonomousFactory;
import com.gos.chargedup.commands.AutoAimChassisToNodeOnTheFly;
import com.gos.chargedup.commands.ChecklistTestAll;
import com.gos.chargedup.commands.CombinedCommandsUtil;
import com.gos.chargedup.commands.CurvatureDriveCommand;
import com.gos.chargedup.commands.SwerveChassisJoystickCommand;
import com.gos.chargedup.commands.TeleopDockingArcadeDriveCommand;
import com.gos.chargedup.commands.TeleopMediumArcadeDriveCommand;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystemInterface;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.gos.chargedup.subsystems.LEDManagerSubsystem;
import com.gos.chargedup.subsystems.SwerveDriveChassisSubsystem;
import com.gos.chargedup.subsystems.TankDriveChassisSubsystem;
import com.gos.lib.properties.PropertyManager;
import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...

    // private final IntakeSubsystem m_intake;
    private final ChassisSubsystemInterface m_chassisSubsystem;
    private final ArmPivotSubsystem m_armPivot;

    private final ArmExtensionSubsystem m_armExtend;
    private final AutonomousFactory m_autonomousFactory;

    private final ClawSubsystem m_claw;

    // Replace with CommandPS4Controller or CommandJoystick if needed
    private final CommandXboxController m_driverController =
        new CommandXboxController(Constants.DRIVER_CONTROLLER_PORT);

    private final CommandXboxController m_operatorController =
        new CommandXboxController(Constants.OPERATOR_CONTROLLER_PORT);

    private final LEDManagerSubsystem m_ledManagerSubsystem;

    private final PneumaticHub m_pneumaticHub;
    private final DoubleSupplier m_pressureSupplier;

    private final PowerDistribution m_powerDistribution;
    private AutoAimNodePositions m_autoAimNodePosition = AutoAimNodePositions.NONE;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer(PneumaticHub pneumaticHub, PowerDistribution powerDistribution) {
        // PropertyManager.setPurgeConstantPreferenceKeys(true);

        // Configure the trigger bindings
        //m_turret = new TurretSubsystem();
        if (Constants.IS_SWERVE) {
            m_chassisSubsystem = new SwerveDriveChassisSubsystem();
        } else {
            m_chassisSubsystem = new TankDriveChassisSubsystem();
        }
        m_claw = new ClawSubsystem();
        m_armPivot = new ArmPivotSubsystem();
        m_armExtend = new ArmExtensionSubsystem();
        // m_intake = new IntakeSubsystem();
        m_autonomousFactory = new AutonomousFactory(m_chassisSubsystem, m_armPivot, m_armExtend, m_claw);

        m_ledManagerSubsystem = new LEDManagerSubsystem(m_chassisSubsystem, m_armPivot, m_claw, m_autonomousFactory); //NOPMD

        m_pneumaticHub = pneumaticHub;
        m_pneumaticHub.enableCompressorAnalog(Constants.MIN_COMPRESSOR_PSI, Constants.MAX_COMPRESSOR_PSI);
        m_pressureSupplier = () -> m_pneumaticHub.getPressure(Constants.PRESSURE_SENSOR_PORT);
        configureBindings();

        m_powerDistribution = powerDistribution;

        if (RobotBase.isSimulation()) {
            DriverStationSim.setEnabled(true);
            DriverStationSim.setAllianceStationId(AllianceStationID.Blue1);
        }

        DriverStation.silenceJoystickConnectionWarning(true);

        SmartDashboard.putData("superStructure", new SuperstructureSendable());
        SmartDashboard.putData("Run checklist", new ChecklistTestAll(m_pressureSupplier, m_chassisSubsystem, m_armPivot, m_armExtend, m_claw));
        SmartDashboard.putData("Select Auto Scoring Position", new ChooseAimTurretCommandSendable());

        createPitCommands(pneumaticHub);
        createTestCommands();
        //automatedTurretCommands();

        if (RobotBase.isReal()) {
            PropertyManager.printDynamicProperties();
        }
        // PropertyManager.purgeExtraKeys();
    }

    @SuppressWarnings({"PMD.NcssCount", "PMD.UnusedPrivateMethod"})
    private void createTestCommands() {
        createTrajectoryTestCommands();
        createChassisTestCommands();
        createClawTestCommands();
        createArmTestCommands();
        createPivotTestCommands();
        createSwerveTrajectoryCommands();
    }

    private void createPitCommands(PneumaticHub pneumaticHub) {
        ShuffleboardTab tab = Shuffleboard.getTab("PitCommands");

        tab.add("Arm Pivot: Reset Encoder", m_armPivot.createResetPivotEncoderCommand());
        tab.add("Arm Pivot: Reset Encoder (0 deg)", m_armPivot.createResetPivotEncoderCommand(0));
        tab.add("Arm Pivot: Reset Encoder (Abs Encoder Val)", m_armPivot.createSyncEncoderToAbsoluteEncoderCommand());
        tab.add("Arm Pivot: to Coast Mode", m_armPivot.createPivotToCoastModeCommand());

        tab.add("Reset Sticky Faults",  createResetStickyFaults());
        tab.add("Compressor: Disable", Commands.runEnd(pneumaticHub::disableCompressor, () -> pneumaticHub.enableCompressorAnalog(Constants.MIN_COMPRESSOR_PSI, Constants.MAX_COMPRESSOR_PSI)));
    }

    private void createTrajectoryTestCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("TrajectoryTestCommands");

        // auto trajectories
        tab.add("Trajectory: Test Line", m_chassisSubsystem.createFollowPathCommand("TestLine"));
        tab.add("Trajectory: Test Mild Curve", m_chassisSubsystem.createFollowPathCommand("TestMildCurve"));
        tab.add("Trajectory: Test S Curve", m_chassisSubsystem.createFollowPathCommand("TestSCurve"));
    }

    private void createSwerveTrajectoryCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("SwerveTestCommands");
        if (Constants.IS_SWERVE) {
            tab.add("Swerve: Test Straight", m_chassisSubsystem.createFollowPathCommand("Swerve Straight Path"));
            tab.add("Swerve: Test Strafe", m_chassisSubsystem.createFollowPathCommand("Swerve Strafe Path"));
            tab.add("Swerve: Test Turn in Line 180", m_chassisSubsystem.createFollowPathCommand("SwerveTurnInPath"));
        }

    }

    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    private void createChassisTestCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("ChassisTestCommands");

        // auto engage
        tab.add("Chassis Auto Engage", m_chassisSubsystem.createAutoEngageCommand());

        // chassis turn PID
        tab.add("Chassis To Angle 0", m_chassisSubsystem.createTurnToAngleCommand(0));
        tab.add("Chassis To Angle 180", m_chassisSubsystem.createTurnToAngleCommand(180));

        // chassis reset odometry test
        tab.add("Chassis set position: (0, 0, 0)", m_chassisSubsystem.createResetOdometryCommand(new Pose2d(0, 0, Rotation2d.fromDegrees(0))));
        tab.add("Chassis set position: (0, 0, 90 deg)", m_chassisSubsystem.createResetOdometryCommand(new Pose2d(0, 0, Rotation2d.fromDegrees(90))));
        tab.add("Chassis set position: (0, 0, -90 deg)", m_chassisSubsystem.createResetOdometryCommand(new Pose2d(0, 0, Rotation2d.fromDegrees(-90))));
        tab.add("Chassis set position: Node 4", m_chassisSubsystem.createResetOdometryCommand(new Pose2d(1.7909518525803976, 2.752448813168305, Rotation2d.fromDegrees(180))));

        tab.add("Chassis: Sync Odometry", m_chassisSubsystem.createSyncOdometryWithPoseEstimatorCommand());


        if (m_chassisSubsystem instanceof TankDriveChassisSubsystem) {
            TankDriveChassisSubsystem tankDrive = (TankDriveChassisSubsystem) m_chassisSubsystem;
            tab.add("Chassis: Tune Velocity", tankDrive.commandChassisVelocity());
            tab.add("Chassis: teleop dock and engage", new TeleopDockingArcadeDriveCommand(tankDrive, m_driverController, m_ledManagerSubsystem));
        } else {
            SwerveDriveChassisSubsystem swerveDrive = (SwerveDriveChassisSubsystem) m_chassisSubsystem;

            //lock wheels
            tab.add("Lock wheels", swerveDrive.commandLockDrivetrain());
            tab.add("Swerve reset position: (0, 0, 0)", swerveDrive.createResetOdometryCommand(new Pose2d(0, 0, Rotation2d.fromDegrees(0))));

            for (int i = 0; i < 4; ++i) {
                tab.add("Swerve Module[" + i + "]   45deg 0mps", swerveDrive.commandSetModuleState(i, 45, 0));
                tab.add("Swerve Module[" + i + "]  180deg 0mps", swerveDrive.commandSetModuleState(i, 180, 0));
                tab.add("Swerve Module[" + i + "] -180deg 0mps", swerveDrive.commandSetModuleState(i, -180, 0));

                tab.add("Swerve Module[" + i + "] 0deg -1mps", swerveDrive.commandSetModuleState(i, 0, -1));
                tab.add("Swerve Module[" + i + "] 0deg  1mps", swerveDrive.commandSetModuleState(i, 0, 1));
                tab.add("Swerve Module[" + i + "] 0deg  2mps", swerveDrive.commandSetModuleState(i, 0, 2));
            }

            tab.add("Chassis Speed (1,0,0)", swerveDrive.commandSetChassisSpeed(new ChassisSpeeds(1, 0, 0)));
            tab.add("Chassis Speed (0,1,0)", swerveDrive.commandSetChassisSpeed(new ChassisSpeeds(0, 1, 0)));
            tab.add("Chassis Speed (0,0,360deg)", swerveDrive.commandSetChassisSpeed(new ChassisSpeeds(0, 0, Units.degreesToRadians(360))));

            tab.add("Chassis Speeds Percent 0% 5%", swerveDrive.commandSetPercentSpeeds(0, 0.05));
            tab.add("Chassis Speeds Percent 0% 100%", swerveDrive.commandSetPercentSpeeds(0, 1));
        }
    }

    private void createPivotTestCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("ArmPivotTestCommands");

        // arm pivot
        tab.add("Arm Pivot: Pivot Down", m_armPivot.createPivotDownCommand());
        tab.add("Arm Pivot: Pivot Up", m_armPivot.createPivotUpCommand());

        tab.add("Arm Pivot: Angle PID - -30 degrees", debugArmPid(-30));
        tab.add("Arm Pivot: Angle PID - -10 degrees", debugArmPid(-10));
        tab.add("Arm Pivot: Angle PID - 0 degrees", debugArmPid(0));
        tab.add("Arm Pivot: Angle PID - 10 degrees", debugArmPid(14));

        // tab.add("Arm Pivot: Gravity Offset Tune", m_armPivot.tuneGravityOffsetPID());
    }

    private void createArmTestCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("ArmExtensionTestCommands");

        tab.add("Arm Piston: Full Retract", m_armExtend.createFullRetractCommand());
        tab.add("Arm Piston: Mid Retract", m_armExtend.createMiddleExtensionCommand());
        tab.add("Arm Piston: Full Extend", m_armExtend.createFullExtensionCommand());

        tab.add("Arm Piston: Bottom Extended", m_armExtend.createExtendBottomPistonCommand());
        tab.add("Arm Piston: Bottom Retracted", m_armExtend.createRetractBottomPistonCommand());
        tab.add("Arm Piston: Top Extended", m_armExtend.createExtendTopPistonCommand());
        tab.add("Arm Piston: Top Retracted", m_armExtend.createRetractTopPistonCommand());
    }

    private void createClawTestCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("ClawTestCommands");

        tab.add("Claw: In", m_claw.createMoveClawIntakeInCommand());
        tab.add("Claw: Out", m_claw.createMoveClawIntakeOutCommand());
    }

    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * CommandXboxController}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link CommandPS4Controller
     * PS4} controllers or {@link CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings() {

        if (m_chassisSubsystem instanceof TankDriveChassisSubsystem) {
            TankDriveChassisSubsystem tankDrive = (TankDriveChassisSubsystem) m_chassisSubsystem;
            m_chassisSubsystem.setDefaultCommand(new CurvatureDriveCommand(tankDrive, m_driverController));
            m_driverController.leftBumper().whileTrue(new TeleopDockingArcadeDriveCommand(tankDrive, m_driverController, m_ledManagerSubsystem));
            m_driverController.leftTrigger().whileTrue(new TeleopMediumArcadeDriveCommand(tankDrive, m_driverController));
        } else {
            m_chassisSubsystem.setDefaultCommand(new SwerveChassisJoystickCommand((SwerveDriveChassisSubsystem) m_chassisSubsystem, m_driverController));
        }
        //m_claw.setDefaultCommand(m_claw.createHoldPiece());

        // Driver
        m_driverController.x().whileTrue(m_chassisSubsystem.createDeferredDriveToPointCommand(Constants.ROBOT_LEFT_BLUE_PICK_UP_POINT, false));
        m_driverController.b().whileTrue(m_chassisSubsystem.createDeferredDriveToPointCommand(Constants.ROBOT_RIGHT_BLUE_PICK_UP_POINT, false));
        //m_driverController.rightBumper().whileTrue(m_ledManagerSubsystem.commandConeGamePieceSignal());
        //m_driverController.rightTrigger().whileTrue(m_ledManagerSubsystem.commandCubeGamePieceSignal());
        m_driverController.povUp().whileTrue(m_chassisSubsystem.createAutoEngageCommand());
        SmartDashboard.putData("Auto Aim Chassis", new AutoAimChassisToNodeOnTheFly(() -> m_autoAimNodePosition, m_armPivot, m_chassisSubsystem, m_ledManagerSubsystem, m_armExtend));

        // Operator
        //Trigger leftJoystickAsButtonRight = new Trigger(() -> m_operatorController.getLeftX() > .5);
        //Trigger leftJoystickAsButtonLeft = new Trigger(() -> m_operatorController.getLeftX() < -.5);
        Trigger leftJoystickAsButtonDown = new Trigger(() -> m_operatorController.getLeftY() > .5);
        Trigger leftJoystickAsButtonUp = new Trigger(() -> m_operatorController.getLeftY() < -.5);
        //leftJoystickAsButtonRight.whileTrue(m_turret.commandMoveTurretCounterClockwise());
        //leftJoystickAsButtonLeft.whileTrue(m_turret.commandMoveTurretClockwise());
        leftJoystickAsButtonUp.whileTrue(m_armPivot.createPivotUpCommand());
        leftJoystickAsButtonDown.whileTrue(m_armPivot.createPivotDownCommand());
        m_operatorController.y().whileTrue(m_ledManagerSubsystem.commandConeGamePieceSignal());
        m_operatorController.b().whileTrue(m_ledManagerSubsystem.commandCubeGamePieceSignal());
        m_operatorController.a().whileTrue(m_claw.createTeleopMoveClawIntakeInCommand(m_operatorController));
        m_operatorController.x().whileTrue(m_claw.createMoveClawIntakeOutCommand());
        m_operatorController.povUp().whileTrue(CombinedCommandsUtil.armToHpPickup(m_armPivot, m_armExtend));
        m_operatorController.povDown().whileTrue(CombinedCommandsUtil.goToGroundPickup(m_armPivot, m_armExtend));
        m_operatorController.povLeft().whileTrue(CombinedCommandsUtil.goHome(m_armPivot, m_armExtend));
        m_operatorController.povRight().whileTrue(m_armPivot.createGoToAutoNodePositionCommand(() -> m_autoAimNodePosition));

        m_operatorController.leftBumper().whileTrue(m_armExtend.createFullExtensionCommand());
        m_operatorController.rightBumper().whileTrue(m_armExtend.createFullRetractCommand());
        m_operatorController.rightTrigger().whileTrue(m_armExtend.createMiddleExtensionCommand());

        m_operatorController.leftTrigger().whileTrue(m_armPivot.createMoveArmToPieceScorePositionAndHoldCommand(AutoPivotHeight.MEDIUM, GamePieceType.CONE));

        // Backup manual controls for debugging
        // m_driverController.povRight().whileTrue(m_chassisSubsystem.createTurnPID(0));
        // m_driverController.povLeft().whileTrue(m_chassisSubsystem.createTurnPID(180));
        // m_driverController.y().whileTrue(new TestOnePieceAndLeaveCommunityThreeCommandGroup(m_chassisSubsystem));

        // m_operatorController.y().whileTrue(debugArmPid(0));
        // m_operatorController.a().whileTrue(debugArmPid(-10));
        // m_operatorController.b().whileTrue(debugArmPid(-30));
        // m_operatorController.x().whileTrue(debugArmPid(14));
        //m_operatorController.povLeft().whileTrue(m_armPivot.commandHpPickupHold());
        // m_operatorController.povUp().whileTrue(m_armPivot.tuneGravityOffsetPID());

        // m_operatorController.povRight().whileTrue(m_armExtend.commandBottomPistonExtended());
        // m_operatorController.povLeft().whileTrue(m_armExtend.commandBottomPistonRetracted());

        // m_operatorController.leftBumper().whileTrue(m_arm.commandBottomPistonExtended());
        // m_operatorController.rightBumper().whileTrue(m_arm.commandBottomPistonRetracted());
        // m_operatorController.rightTrigger().whileTrue(m_arm.commandTopPistonExtended());
        // m_operatorController.leftTrigger().whileTrue(m_arm.commandTopPistonRetracted());
    }

    private Command debugArmPid(double angle) {
        return m_armPivot.createPivotToAngleAndHoldCommand(angle);
        //.andThen(m_claw.createMoveClawIntakeOutWithTimeoutCommand());
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


    private void resetStickyFaults() {
        m_pneumaticHub.clearStickyFaults();
        m_powerDistribution.clearStickyFaults();
        m_chassisSubsystem.clearStickyFaults();
        m_armPivot.clearStickyFaults();
        m_claw.clearStickyFaults();
    }

    private Command createResetStickyFaults() {
        return Commands.run(this::resetStickyFaults).ignoringDisable(true);
    }


    private class SuperstructureSendable implements Sendable {

        @Override
        public void initSendable(SendableBuilder builder) {
            builder.setSmartDashboardType(SmartDashboardNames.SUPER_STRUCTURE);

            builder.addDoubleProperty(
                SmartDashboardNames.ARM_ANGLE, m_armPivot::getFeedbackAngleDeg, null);
            builder.addDoubleProperty(
                SmartDashboardNames.ARM_GOAL_ANGLE, m_armPivot::getArmAngleGoal, null);
            builder.addBooleanProperty(
                SmartDashboardNames.ARM_EXTENSION1, m_armExtend::isBottomPistonIn, null);
            builder.addBooleanProperty(
                SmartDashboardNames.ARM_EXTENSION2, m_armExtend::isTopPistonIn, null);
            builder.addDoubleProperty(
                SmartDashboardNames.ARM_SPEED, m_armPivot::getArmMotorSpeed, null);
            // builder.addDoubleProperty(
            //     SmartDashboardNames.INTAKE_SPEED, m_intake::getIntakeRollerSpeed, null);
            // builder.addBooleanProperty(
            //     SmartDashboardNames.INTAKE_DOWN, m_intake::isIntakeDown, null);
            //builder.addDoubleProperty(
            //    SmartDashboardNames.TURRET_SPEED, m_turret::getTurretSpeed, null);
            //builder.addDoubleProperty(
            //    SmartDashboardNames.TURRET_ANGLE, m_turret::getTurretAngleDeg, null);
            //builder.addDoubleProperty(
            //    SmartDashboardNames.TURRET_GOAL_ANGLE, m_turret::getTurretAngleGoalDeg, null);
            builder.addDoubleProperty(
                SmartDashboardNames.ROBOT_ANGLE, () -> m_chassisSubsystem.getPose().getRotation().getDegrees(), null);

        }
    }

    private class ChooseAimTurretCommandSendable implements Sendable {

        @Override
        public void initSendable(SendableBuilder builder) {
            builder.setSmartDashboardType("ScoringPosition");

            builder.addDoubleProperty(
                "SelectedPosition", null, this::handleScoringPosition);
            builder.addStringProperty("AsString", () -> m_autoAimNodePosition.toString(), null);
        }

        private void handleScoringPosition(double d) {
            try {
                m_autoAimNodePosition = AutoAimNodePositions.values()[(int) d];
            } catch (Exception ex) { // NOPMD
                m_autoAimNodePosition = AutoAimNodePositions.NONE;
                ex.printStackTrace(); // NOPMD
            }
        }
    }
}
