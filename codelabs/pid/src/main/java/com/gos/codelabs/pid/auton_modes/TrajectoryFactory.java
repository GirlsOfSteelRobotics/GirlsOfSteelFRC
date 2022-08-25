package com.gos.codelabs.pid.auton_modes;

import com.gos.lib.properties.PropertyManager;
import com.gos.codelabs.pid.Constants;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.math.util.Units;
import com.gos.codelabs.pid.commands.auton.DriveTrajectoryCommand;
import com.gos.codelabs.pid.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.List;

public final class TrajectoryFactory {

    public static final class AutoConstants {
        public static final GosDoubleProperty MAX_SPEED_METERS_PER_SECOND = new GosDoubleProperty(false, "Trajectory.MaxVelocity", Units.inchesToMeters(12));
        public static final GosDoubleProperty MAX_ACCELERATION_METERS_PER_SECOND_SQUARED = new GosDoubleProperty(false, "Trajectory.MaxAcceleration", Units.inchesToMeters(12));

    }

    private TrajectoryFactory() {

    }

    public static CommandBase getTestStraightAcrossFieldTrajectory(ChassisSubsystem chassis) {


        return DriveTrajectoryCommand.createWithVelocity(chassis, () -> {

            TrajectoryConfig config = getTrajectoryConfig();

            return TrajectoryGenerator.generateTrajectory(
                    new Pose2d(Units.inchesToMeters(10), Units.inchesToMeters(0), Rotation2d.fromDegrees(90)),
                    List.of(),
                    new Pose2d(Units.inchesToMeters(10), Units.inchesToMeters(12 * 27), Rotation2d.fromDegrees(90)),
                    config);
        }, true);
    }

    public static CommandBase getTestSCurveTrajectory(ChassisSubsystem chassis) {

        return DriveTrajectoryCommand.createWithVelocity(chassis, () -> {
            TrajectoryConfig config = getTrajectoryConfig();

            return TrajectoryGenerator.generateTrajectory(
                    new Pose2d(2, 2, new Rotation2d()),
                    List.of(),
                    new Pose2d(6, 4, new Rotation2d()),
                    config);
        }, true);
    }


    public static CommandBase getTestStraightForwardTestTrajectory(ChassisSubsystem chassis) {

        return DriveTrajectoryCommand.createWithVelocity(chassis, () -> {
            TrajectoryConfig config = getTrajectoryConfig();

            return TrajectoryGenerator.generateTrajectory(
                    new Pose2d(Units.inchesToMeters(0), Units.inchesToMeters(40), new Rotation2d()),
                    List.of(),
                    new Pose2d(Units.inchesToMeters(240), Units.inchesToMeters(40), new Rotation2d()),
                    config);
        }, true);
    }

    public static CommandBase getTestStraightBackwardsTestTrajectory(ChassisSubsystem chassis) {
        return DriveTrajectoryCommand.createWithVelocity(chassis, () -> {
            TrajectoryConfig config = getTrajectoryConfig();
            config.setReversed(true);

            return TrajectoryGenerator.generateTrajectory(
                    new Pose2d(Units.inchesToMeters(240), Units.inchesToMeters(20), new Rotation2d()),
                    List.of(),
                    new Pose2d(Units.inchesToMeters(0), Units.inchesToMeters(20), new Rotation2d()),
                    config);
        }, true);
    }

    public static TrajectoryConfig getTrajectoryConfig() {

        // Create a voltage constraint to ensure we don't accelerate too fast
        var autoVoltageConstraint =
                new DifferentialDriveVoltageConstraint(
                        new SimpleMotorFeedforward(
                                Constants.DrivetrainConstants.KS_VOLTS,
                                Constants.DrivetrainConstants.KV_VOLT_SECONDS_PER_METER,
                                Constants.DrivetrainConstants.KA_VOLT_SECONDS_SQUARED_PER_METER),
                            Constants.DrivetrainConstants.DRIVE_KINEMATICS,
                        10);

        // Create config for trajectory
        return new TrajectoryConfig(
                        AutoConstants.MAX_SPEED_METERS_PER_SECOND.getValue(),
                        AutoConstants.MAX_ACCELERATION_METERS_PER_SECOND_SQUARED.getValue())
                        // Add kinematics to ensure max speed is actually obeyed
                        .setKinematics(Constants.DrivetrainConstants.DRIVE_KINEMATICS)
                        // Apply the voltage constraint
                        .addConstraint(autoVoltageConstraint);
    }
}
