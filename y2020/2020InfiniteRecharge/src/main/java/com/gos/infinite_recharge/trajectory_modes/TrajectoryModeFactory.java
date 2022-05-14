/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                                                         */
/* Open Source Software - may be modified and shared by FRC teams. The code     */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                                                                                             */
/*----------------------------------------------------------------------------*/

package com.gos.infinite_recharge.trajectory_modes;

import java.util.List;

import com.gos.infinite_recharge.Constants;
import com.gos.infinite_recharge.Constants.DriveConstants;
import com.gos.infinite_recharge.subsystems.Chassis;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.infinite_recharge.commands.autonomous.FollowTrajectory;
import com.gos.infinite_recharge.commands.autonomous.FollowTrajectory.AutoConstants;


public class TrajectoryModeFactory extends SequentialCommandGroup {

    private static final double AUTO_LINE_X = 122;
    private static final double OUTER_PORT_CENTER_Y = -98;

    private static final double FRONT_TRENCH_X = 207;
    private static final double FRONT_TRENCH_Y = -31;

    private static final double CONTROL_PANEL_X = 327;
    private static final double CONTROL_PANEL_Y = -31;

    public Command getTrajectoryAutoLineToFrontOfTrench(Chassis chassis) {


        Trajectory trajectoryAutoLineToFrontOfTrench = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(Units.inchesToMeters(AUTO_LINE_X), Units.inchesToMeters(OUTER_PORT_CENTER_Y), new Rotation2d(0)), //starting position in front of goal
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(),
            new Pose2d(Units.inchesToMeters(FRONT_TRENCH_X), Units.inchesToMeters(FRONT_TRENCH_Y), new Rotation2d(0)),
            // Pass config
            getTrajectoryConfig()
        );
        return new FollowTrajectory(trajectoryAutoLineToFrontOfTrench, chassis);
    }

    public Command getTrajectoryFrontOfTrenchToControlPanel(Chassis chassis) {

        Trajectory trajectoryFrontOfTrenchToControlPanel = TrajectoryGenerator.generateTrajectory(
            new Pose2d(Units.inchesToMeters(FRONT_TRENCH_X), Units.inchesToMeters(FRONT_TRENCH_Y), new Rotation2d(0)),
            List.of(),
            new Pose2d(Units.inchesToMeters(CONTROL_PANEL_X), Units.inchesToMeters(CONTROL_PANEL_Y), new Rotation2d(0)),
            getTrajectoryConfig(AutoConstants.SLOW_SPEED_METERS_PER_SECOND, AutoConstants.SLOW_ACCELERATION_METERS_PER_SECOND_SQUARED)
        );
        return new FollowTrajectory(trajectoryFrontOfTrenchToControlPanel, chassis);
    }

    public Command getTrajectoryControlPanelToFrontOfTrench(Chassis chassis) {

        TrajectoryConfig getTrajectoryConfig = getTrajectoryConfig();
        getTrajectoryConfig.setReversed(true);
        Trajectory trajectoryControlPanelToFrontOfTrench = TrajectoryGenerator.generateTrajectory(
            new Pose2d(Units.inchesToMeters(CONTROL_PANEL_X), Units.inchesToMeters(CONTROL_PANEL_Y), new Rotation2d(0)),
            List.of(),
            new Pose2d(Units.inchesToMeters(FRONT_TRENCH_X), Units.inchesToMeters(FRONT_TRENCH_Y), new Rotation2d(0)),
            getTrajectoryConfig
        );
        return new FollowTrajectory(trajectoryControlPanelToFrontOfTrench, chassis);
    }

    public Command getTrajectoryFrontOfTrenchToAutoLine(Chassis chassis) {
        TrajectoryConfig getTrajectoryConfig = getTrajectoryConfig();
        getTrajectoryConfig.setReversed(true);

        Trajectory trajectoryFrontOfTrenchToAutoLine = TrajectoryGenerator.generateTrajectory(
            new Pose2d(Units.inchesToMeters(FRONT_TRENCH_X), Units.inchesToMeters(FRONT_TRENCH_Y), new Rotation2d(0)),
            List.of(),
            new Pose2d(Units.inchesToMeters(AUTO_LINE_X), Units.inchesToMeters(OUTER_PORT_CENTER_Y), new Rotation2d(0)),
            getTrajectoryConfig
        );
        return new FollowTrajectory(trajectoryFrontOfTrenchToAutoLine, chassis);
    }

    public Command getTrajectoryControlPanelToAutoLine(Chassis chassis) {
        TrajectoryConfig trajectoryConfig = getTrajectoryConfig(AutoConstants.FAST_SPEED_METERS_PER_SECOND, AutoConstants.FAST_ACCELERATION_METERS_PER_SECOND_SQUARED);
        trajectoryConfig.setReversed(true);

        Trajectory trajectoryFrontOfTrenchToAutoLine = TrajectoryGenerator.generateTrajectory(
                new Pose2d(Units.inchesToMeters(CONTROL_PANEL_X), Units.inchesToMeters(CONTROL_PANEL_Y), new Rotation2d(0)),
                List.of(),
                new Pose2d(Units.inchesToMeters(AUTO_LINE_X), Units.inchesToMeters(OUTER_PORT_CENTER_Y), new Rotation2d(0)),
                trajectoryConfig
        );
        return new FollowTrajectory(trajectoryFrontOfTrenchToAutoLine, chassis);
    }

    public Command getTrajectoryRightSideToControlPanel(Chassis chassis) {
        TrajectoryConfig getTrajectoryConfig = getTrajectoryConfig(AutoConstants.SLOW_SPEED_METERS_PER_SECOND, AutoConstants.SLOW_ACCELERATION_METERS_PER_SECOND_SQUARED);


        Trajectory trajectoryFrontOfTrenchToAutoLine = TrajectoryGenerator.generateTrajectory(
                new Pose2d(Units.inchesToMeters(AUTO_LINE_X), Units.inchesToMeters(FRONT_TRENCH_Y), new Rotation2d(0)),
                List.of(),
                new Pose2d(Units.inchesToMeters(CONTROL_PANEL_X), Units.inchesToMeters(CONTROL_PANEL_Y), new Rotation2d(0)),
                getTrajectoryConfig
        );
        return new FollowTrajectory(trajectoryFrontOfTrenchToAutoLine, chassis);
    }

    public Command getTrajectoryControlPanelToRightSide(Chassis chassis) {
        TrajectoryConfig getTrajectoryConfig = getTrajectoryConfig(AutoConstants.FAST_SPEED_METERS_PER_SECOND, AutoConstants.FAST_ACCELERATION_METERS_PER_SECOND_SQUARED);

        getTrajectoryConfig.setReversed(true);

        Trajectory trajectoryFrontOfTrenchToAutoLine = TrajectoryGenerator.generateTrajectory(
                new Pose2d(Units.inchesToMeters(CONTROL_PANEL_X), Units.inchesToMeters(CONTROL_PANEL_Y), new Rotation2d(0)),
                List.of(),
                new Pose2d(Units.inchesToMeters(128), Units.inchesToMeters(FRONT_TRENCH_Y), new Rotation2d(0)),
                getTrajectoryConfig
        );
        return new FollowTrajectory(trajectoryFrontOfTrenchToAutoLine, chassis);
    }

    public Command getTrajectoryCenterToRendezvous(Chassis chassis) {
        TrajectoryConfig getTrajectoryConfig = getTrajectoryConfig(AutoConstants.SLOW_SPEED_METERS_PER_SECOND, AutoConstants.SLOW_ACCELERATION_METERS_PER_SECOND_SQUARED);

        Trajectory trajectoryFrontOfTrenchToAutoLine = TrajectoryGenerator.generateTrajectory(
                new Pose2d(Units.inchesToMeters(AUTO_LINE_X), Units.inchesToMeters(-127), new Rotation2d(0)),
                List.of(),
                new Pose2d(Units.inchesToMeters(227), Units.inchesToMeters(-146), new Rotation2d(0)),
                getTrajectoryConfig
        );
        return new FollowTrajectory(trajectoryFrontOfTrenchToAutoLine, chassis);
    }

    public Command getTrajectoryRendezvousToCenter(Chassis chassis) {
        TrajectoryConfig getTrajectoryConfig = getTrajectoryConfig(AutoConstants.SLOW_SPEED_METERS_PER_SECOND, AutoConstants.SLOW_ACCELERATION_METERS_PER_SECOND_SQUARED);
        getTrajectoryConfig.setReversed(true);

        Trajectory trajectoryFrontOfTrenchToAutoLine = TrajectoryGenerator.generateTrajectory(
                new Pose2d(Units.inchesToMeters(227), Units.inchesToMeters(-146), new Rotation2d(0)),
                List.of(),
                new Pose2d(Units.inchesToMeters(AUTO_LINE_X), Units.inchesToMeters(-127), new Rotation2d(0)),
                getTrajectoryConfig
        );
        return new FollowTrajectory(trajectoryFrontOfTrenchToAutoLine, chassis);
    }

    public Command getTrajectoryAutoLineToOpponentsTrench(Chassis chassis) {
        TrajectoryConfig getTrajectoryConfig = getTrajectoryConfig(AutoConstants.NORMAL_SPEED_METERS_PER_SECOND, AutoConstants.NORMAL_ACCELERATION_METERS_PER_SECOND_SQUARED);

        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(Units.inchesToMeters(Constants.AUTO_LINE_LEFT_X), Units.inchesToMeters(Constants.AUTO_LINE_LEFT_Y), new Rotation2d(0)),
                List.of(),
                new Pose2d(Units.inchesToMeters(Constants.OPPONENTS_TRENCH_X), Units.inchesToMeters(Constants.OPPONENTS_TRENCH_Y), new Rotation2d(Constants.OPPONENTS_TRENCH_ANGLE)),
                getTrajectoryConfig
        );
        return new FollowTrajectory(trajectory, chassis);
    }

    public Command getTrajectoryOpponentsTrenchToPickUpCell(Chassis chassis) {
        TrajectoryConfig getTrajectoryConfig = getTrajectoryConfig(AutoConstants.SLOW_SPEED_METERS_PER_SECOND, AutoConstants.SLOW_ACCELERATION_METERS_PER_SECOND_SQUARED);

        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(Units.inchesToMeters(Constants.OPPONENTS_TRENCH_X), Units.inchesToMeters(Constants.OPPONENTS_TRENCH_Y), new Rotation2d(Constants.OPPONENTS_TRENCH_ANGLE)),
                List.of(),
                new Pose2d(Units.inchesToMeters(Constants.OPPONENTS_TRENCH_CELLS_X), Units.inchesToMeters(Constants.OPPONENTS_TRENCH_CELLS_Y), new Rotation2d(Constants.OPPONENTS_TRENCH_ANGLE)),
                getTrajectoryConfig
        );
        return new FollowTrajectory(trajectory, chassis);
    }

    public Command getTrajectoryOpponentsTrenchToAutoLine(Chassis chassis) {
        TrajectoryConfig getTrajectoryConfig = getTrajectoryConfig(AutoConstants.FAST_SPEED_METERS_PER_SECOND, AutoConstants.FAST_ACCELERATION_METERS_PER_SECOND_SQUARED);
        getTrajectoryConfig.setReversed(true);
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(Units.inchesToMeters(Constants.OPPONENTS_TRENCH_CELLS_X), Units.inchesToMeters(Constants.OPPONENTS_TRENCH_CELLS_Y), new Rotation2d(0)),
                List.of(),
                new Pose2d(Units.inchesToMeters(Constants.AUTO_LINE_LEFT_X), Units.inchesToMeters(Constants.AUTO_LINE_LEFT_Y), new Rotation2d(Constants.AUTO_LINE_LEFT_SHOOT_ANGLE)),
                getTrajectoryConfig
        );
        return new FollowTrajectory(trajectory, chassis);
    }

    public static TrajectoryConfig getTrajectoryConfig(double maxSpeedMetersPerSecond, double maxAccelerationMetersPerSecondSquared) {
        var autoVoltageConstraint =
            new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(DriveConstants.KS_VOLTS,
                                        DriveConstants.KV_VOLT_SECONDS_PER_METER,
                                        DriveConstants.KA_VOLT_SECONDS_SQUARED_PER_METER),
                DriveConstants.DRIVE_KINEMATICS,
                DriveConstants.MAX_VOLTAGE);

        return new TrajectoryConfig(maxSpeedMetersPerSecond,
                maxAccelerationMetersPerSecondSquared)
        // Add kinematics to ensure max speed is actually obeyed
        .setKinematics(DriveConstants.DRIVE_KINEMATICS)
        // Apply the voltage constraint
        .addConstraint(autoVoltageConstraint);
    }

    public static TrajectoryConfig getTrajectoryConfig() {
        return  getTrajectoryConfig(AutoConstants.NORMAL_SPEED_METERS_PER_SECOND, AutoConstants.NORMAL_ACCELERATION_METERS_PER_SECOND_SQUARED);

    }
}
