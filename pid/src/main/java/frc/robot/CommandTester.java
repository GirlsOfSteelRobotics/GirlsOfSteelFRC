package frc.robot;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.auton.TrajectoryFactory;
import frc.robot.commands.ElevatorToPositionCommand;
import frc.robot.commands.ShooterRpmCommand;
import frc.robot.commands.auton.DriveStraightDistanceCustomControlCommand;
import frc.robot.commands.auton.DriveStraightDistancePositionControlCommand;
import frc.robot.commands.auton.DriveStraightDistanceSmartMotionControlCommand;
import frc.robot.commands.auton.SetRobotPoseCommand;
import frc.robot.commands.tuning.FindChassisTurningCompensationCommand;
import frc.robot.commands.tuning.FindElevatorGravityCompensationCommand;
import frc.robot.commands.tuning.FindShooterFFGainCommand;
import frc.robot.commands.tuning.TuneChassisVelocityCommand;
import frc.robot.commands.tuning.TuneShooterRpmCommand;
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

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
