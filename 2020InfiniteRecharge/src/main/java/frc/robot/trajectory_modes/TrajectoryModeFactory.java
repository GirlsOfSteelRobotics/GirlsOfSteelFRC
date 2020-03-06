/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                                                         */
/* Open Source Software - may be modified and shared by FRC teams. The code     */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                                                                                             */
/*----------------------------------------------------------------------------*/

package frc.robot.trajectory_modes;

import java.util.List;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.autonomous.FollowTrajectory;
import frc.robot.commands.autonomous.FollowTrajectory.AutoConstants;
import frc.robot.commands.autonomous.FollowTrajectory.DriveConstants;
import frc.robot.subsystems.*;


public class TrajectoryModeFactory extends SequentialCommandGroup {



    /**
     * Creates a new AutomatedConveyorIntake.
     */
    public TrajectoryModeFactory(Chassis chassis) {
    }
        
    public Command getTrajectoryAutoLineToFrontOfTrench(Chassis chassis) {
       

        Trajectory trajectoryAutoLineToFrontOfTrench = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(Units.inchesToMeters(122), Units.inchesToMeters(-98), new Rotation2d(0)), //starting position in front of goal
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(),
            new Pose2d(Units.inchesToMeters(207), Units.inchesToMeters(-31), new Rotation2d(0)),
            // Pass config
            getTrajectoryConfig()
        );
        return new FollowTrajectory(trajectoryAutoLineToFrontOfTrench, chassis);
    }
    
    public Command getTrajectoryFrontOfTrenchToControlPanel(Chassis chassis) {

        Trajectory trajectoryFrontOfTrenchToControlPanel = TrajectoryGenerator.generateTrajectory(
            new Pose2d(Units.inchesToMeters(207), Units.inchesToMeters(-31), new Rotation2d(0)),
            List.of(),
            new Pose2d(Units.inchesToMeters(327), Units.inchesToMeters(-31), new Rotation2d(0)),
            getTrajectoryConfig(AutoConstants.slowSpeedMetersPerSecond, AutoConstants.slowAccelerationMetersPerSecondSquared)
        );
        return new FollowTrajectory(trajectoryFrontOfTrenchToControlPanel, chassis);
    }

    public Command getTrajectoryControlPanelToFrontOfTrench(Chassis chassis) {

        TrajectoryConfig getTrajectoryConfig = getTrajectoryConfig();
        getTrajectoryConfig.setReversed(true);
        Trajectory trajectoryControlPanelToFrontOfTrench = TrajectoryGenerator.generateTrajectory(
            new Pose2d(Units.inchesToMeters(327), Units.inchesToMeters(-31), new Rotation2d(0)),
            List.of(),
            new Pose2d(Units.inchesToMeters(207), Units.inchesToMeters(-31), new Rotation2d(0)),
            getTrajectoryConfig
        );
        return new FollowTrajectory(trajectoryControlPanelToFrontOfTrench, chassis);
    }

    public Command getTrajectoryFrontOfTrenchToAutoLine(Chassis chassis) {
        TrajectoryConfig getTrajectoryConfig = getTrajectoryConfig();
        getTrajectoryConfig.setReversed(true);

        Trajectory trajectoryFrontOfTrenchToAutoLine = TrajectoryGenerator.generateTrajectory(
            new Pose2d(Units.inchesToMeters(207), Units.inchesToMeters(-31), new Rotation2d(0)),
            List.of(),
            new Pose2d(Units.inchesToMeters(122), Units.inchesToMeters(-98), new Rotation2d(0)),
            getTrajectoryConfig
        );
        return new FollowTrajectory(trajectoryFrontOfTrenchToAutoLine, chassis);
    }

    public Command getTrajectoryControlPanelToAutoLine(Chassis chassis) {
        TrajectoryConfig trajectoryConfig = getTrajectoryConfig(AutoConstants.fastSpeedMetersPerSecond, AutoConstants.fastAccelerationMetersPerSecondSquared);
        trajectoryConfig.setReversed(true);

        Trajectory trajectoryFrontOfTrenchToAutoLine = TrajectoryGenerator.generateTrajectory(
                new Pose2d(Units.inchesToMeters(327), Units.inchesToMeters(-31), new Rotation2d(0)),
                List.of(),
                new Pose2d(Units.inchesToMeters(122), Units.inchesToMeters(-98), new Rotation2d(0)),
                trajectoryConfig
        );
        return new FollowTrajectory(trajectoryFrontOfTrenchToAutoLine, chassis);
    }

    public Command getTrajectoryRightSideToControlPanel(Chassis chassis) {
        TrajectoryConfig getTrajectoryConfig = getTrajectoryConfig();
        getTrajectoryConfig.setReversed(true);

        Trajectory trajectoryFrontOfTrenchToAutoLine = TrajectoryGenerator.generateTrajectory(
                new Pose2d(Units.inchesToMeters(122), Units.inchesToMeters(-31), new Rotation2d(0)),
                List.of(),
                new Pose2d(Units.inchesToMeters(327), Units.inchesToMeters(-31), new Rotation2d(0)),
                getTrajectoryConfig
        );
        return new FollowTrajectory(trajectoryFrontOfTrenchToAutoLine, chassis);
    }

    private TrajectoryConfig getTrajectoryConfig(double kMaxSpeedMetersPerSecond, double kMaxAccelerationMetersPerSecondSquared) {
        var autoVoltageConstraint =
        new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(DriveConstants.ksVolts,
                                    DriveConstants.kvVoltSecondsPerMeter,
                                    DriveConstants.kaVoltSecondsSquaredPerMeter),
            DriveConstants.kDriveKinematics,
            DriveConstants.maxVoltage);

    TrajectoryConfig config =
        new TrajectoryConfig(kMaxSpeedMetersPerSecond,
                kMaxAccelerationMetersPerSecondSquared)
        // Add kinematics to ensure max speed is actually obeyed
        .setKinematics(DriveConstants.kDriveKinematics)
        // Apply the voltage constraint
        .addConstraint(autoVoltageConstraint);

        return config;
    }

    private TrajectoryConfig getTrajectoryConfig() {
        return  getTrajectoryConfig(AutoConstants.normalSpeedMetersPerSecond, AutoConstants.normalAccelerationMetersPerSecondSquared);

    }
}
