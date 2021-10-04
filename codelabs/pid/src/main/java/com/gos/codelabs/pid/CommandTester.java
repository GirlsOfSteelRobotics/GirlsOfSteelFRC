package com.gos.codelabs.pid;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import com.gos.codelabs.pid.auton.TrajectoryFactory;
import com.gos.codelabs.pid.commands.ElevatorToPositionCommand;
import com.gos.codelabs.pid.commands.ShooterRpmCommand;
import com.gos.codelabs.pid.commands.auton.DriveStraightDistanceCustomControlCommand;
import com.gos.codelabs.pid.commands.auton.DriveStraightDistancePositionControlCommand;
import com.gos.codelabs.pid.commands.auton.DriveStraightDistanceSmartMotionControlCommand;
import com.gos.codelabs.pid.commands.auton.SetRobotPoseCommand;
import com.gos.codelabs.pid.commands.tuning.FindChassisTurningCompensationCommand;
import com.gos.codelabs.pid.commands.tuning.FindElevatorGravityCompensationCommand;
import com.gos.codelabs.pid.commands.tuning.FindShooterFFGainCommand;
import com.gos.codelabs.pid.commands.tuning.TuneChassisVelocityCommand;
import com.gos.codelabs.pid.commands.tuning.TuneShooterRpmCommand;
import com.gos.codelabs.pid.subsystems.ChassisSubsystem;
import com.gos.codelabs.pid.subsystems.ElevatorSubsystem;
import com.gos.codelabs.pid.subsystems.ShooterSubsystem;

public class CommandTester {

    public CommandTester(RobotContainer robotContainer) {

        // Alias for readability
        ChassisSubsystem chassis = robotContainer.getChassis();
        ElevatorSubsystem elevator = robotContainer.getElevator();
        ShooterSubsystem shooter = robotContainer.getShooter();

        ////////////////////////////////
        // Teleop
        ////////////////////////////////

        // Elevator
        createObservableCommand("Lift To Position Low", new ElevatorToPositionCommand(elevator, ElevatorSubsystem.Positions.LOW));
        createObservableCommand("Lift To Position Mid", new ElevatorToPositionCommand(elevator, ElevatorSubsystem.Positions.MID));
        createObservableCommand("Lift To Position High", new ElevatorToPositionCommand(elevator, ElevatorSubsystem.Positions.HIGH));

        // Shooter
        createObservableCommand("Shooter RPM 1500", new ShooterRpmCommand(shooter, 1500));
        createObservableCommand("Shooter RPM 2000", new ShooterRpmCommand(shooter, 2000));

        ////////////////////////////////
        // Autonomous
        ////////////////////////////////

        // Tuning
        createObservableCommand("Tuning.ChassisTurningComp", new FindChassisTurningCompensationCommand(chassis));
        createObservableCommand("Tuning.ChassisVelocity", new TuneChassisVelocityCommand(chassis));
        createObservableCommand("Tuning.ElevatorGravityComp", new FindElevatorGravityCompensationCommand(elevator));
        createObservableCommand("Tuning.ShooterFF", new FindShooterFFGainCommand(shooter));
        createObservableCommand("Tuning.ShooterRPM", new TuneShooterRpmCommand(shooter));

        // Position Driving
        double driveDistanceCommand = 180;
        createObservableCommand("Drive Distance (Custom) Forwards", new DriveStraightDistanceCustomControlCommand(chassis, Units.inchesToMeters(driveDistanceCommand)));
        createObservableCommand("Drive Distance (Custom) Backwards", new DriveStraightDistanceCustomControlCommand(chassis, Units.inchesToMeters(-driveDistanceCommand)));
        createObservableCommand("Drive Distance (Position) Forwards", new DriveStraightDistancePositionControlCommand(chassis, Units.inchesToMeters(driveDistanceCommand)));
        createObservableCommand("Drive Distance (Position) Backwards", new DriveStraightDistancePositionControlCommand(chassis, Units.inchesToMeters(-driveDistanceCommand)));
        createObservableCommand("Drive Distance (SM) Forwards", new DriveStraightDistanceSmartMotionControlCommand(chassis, Units.inchesToMeters(driveDistanceCommand)));
        createObservableCommand("Drive Distance (SM) Backwards", new DriveStraightDistanceSmartMotionControlCommand(chassis, Units.inchesToMeters(-driveDistanceCommand)));

        // Trajectory Driving
        createObservableCommand("SetStartingPosition (-90)", new SetRobotPoseCommand(chassis, new Pose2d(2, 5, Rotation2d.fromDegrees(-90))));
        createObservableCommand("SetStartingPosition (0)", new SetRobotPoseCommand(chassis, new Pose2d(2, 5, Rotation2d.fromDegrees(0))));
        createObservableCommand("SetStartingPosition (90)", new SetRobotPoseCommand(chassis, new Pose2d(2, 5, Rotation2d.fromDegrees(90))));
        createObservableCommand("Trajectory.Straight Forward", TrajectoryFactory.getTestStraightForwardTestTrajectory(chassis));
        createObservableCommand("Trajectory.Straight Backwards", TrajectoryFactory.getTestStraightBackwardsTestTrajectory(chassis));
        createObservableCommand("Trajectory.Across Field", TrajectoryFactory.getTestStraightAcrossFieldTrajectory(chassis));
        createObservableCommand("Trajectory.S-Curve", TrajectoryFactory.getTestSCurveTrajectory(chassis));

    }

    private void createObservableCommand(String name, Command command) {
        Command observable =  command.andThen(() -> SmartDashboard.putBoolean("Auton Running", false)).withName(name);
        Shuffleboard.getTab("Command Tester").add(name, (Sendable) observable);
    }
}
