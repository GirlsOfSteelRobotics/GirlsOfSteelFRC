package frc.robot.auton;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.commands.auton.DriveTrajectoryCommand;
import frc.robot.subsystems.ChassisSubsystem;

import java.util.List;

public final class TrajectoryFactory {

    public static final class AutoConstants {
        public static final double kMaxSpeedMetersPerSecond = Units.inchesToMeters(120);
        public static final double kMaxAccelerationMetersPerSecondSquared = Units.inchesToMeters(120);

        // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;
    }

    private TrajectoryFactory() {

    }

    public static Command getTestStraightAcrossFieldTrajectory(ChassisSubsystem chassis) {

        TrajectoryConfig config = getTrajectoryConfig();

        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(Units.inchesToMeters(10), Units.inchesToMeters(0), Rotation2d.fromDegrees(90)),
                List.of(),
                new Pose2d(Units.inchesToMeters(10), Units.inchesToMeters(12 * 27), Rotation2d.fromDegrees(90)),
                config);

        return DriveTrajectoryCommand.createWithVelocity(chassis, trajectory, true);
    }

    public static Command getTestSCurveTrajectory(ChassisSubsystem chassis) {

        TrajectoryConfig config = getTrajectoryConfig();

        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(2, 2, new Rotation2d()),
                List.of(),
                new Pose2d(6, 4, new Rotation2d()),
                config);

        return DriveTrajectoryCommand.createWithVelocity(chassis, trajectory, true);
    }


    public static Command getTestStraightForwardTestTrajectory(ChassisSubsystem chassis) {

        TrajectoryConfig config = getTrajectoryConfig();

        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(Units.inchesToMeters(0), Units.inchesToMeters(40), new Rotation2d()),
                List.of(),
                new Pose2d(Units.inchesToMeters(240), Units.inchesToMeters(40), new Rotation2d()),
                config);

        return DriveTrajectoryCommand.createWithVelocity(chassis, trajectory, true);
    }

    public static Command getTestStraightBackwardsTestTrajectory(ChassisSubsystem chassis) {

        TrajectoryConfig config = getTrajectoryConfig();
        config.setReversed(true);

        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(Units.inchesToMeters(240), Units.inchesToMeters(20), new Rotation2d()),
                List.of(),
                new Pose2d(Units.inchesToMeters(0), Units.inchesToMeters(20), new Rotation2d()),
                config);

        return DriveTrajectoryCommand.createWithVelocity(chassis, trajectory, true);
    }

    public static TrajectoryConfig getTrajectoryConfig() {

        // Create a voltage constraint to ensure we don't accelerate too fast
        var autoVoltageConstraint =
                new DifferentialDriveVoltageConstraint(
                        new SimpleMotorFeedforward(
                                Constants.DrivetrainConstants.ksVolts,
                                Constants.DrivetrainConstants.kvVoltSecondsPerMeter,
                                Constants.DrivetrainConstants.kaVoltSecondsSquaredPerMeter),
                            Constants.DrivetrainConstants.kDriveKinematics,
                        10);

        // Create config for trajectory
        return new TrajectoryConfig(
                        AutoConstants.kMaxSpeedMetersPerSecond,
                        AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                        // Add kinematics to ensure max speed is actually obeyed
                        .setKinematics(Constants.DrivetrainConstants.kDriveKinematics)
                        // Apply the voltage constraint
                        .addConstraint(autoVoltageConstraint);
    }
}
