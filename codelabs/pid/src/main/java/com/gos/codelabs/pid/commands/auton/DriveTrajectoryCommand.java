package com.gos.codelabs.pid.commands.auton;

import com.gos.codelabs.pid.Constants;
import com.gos.codelabs.pid.auton.TrajectoryFactory;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import com.gos.codelabs.pid.subsystems.ChassisSubsystem;

public final class DriveTrajectoryCommand {
    public static Command createWithVelocity(ChassisSubsystem drivetrain, Trajectory trajectory, boolean resetPosition) {
        RamseteCommand ramseteCommand =
                new RamseteCommand(
                        trajectory,
                        drivetrain::getPose,
                        new RamseteController(TrajectoryFactory.AutoConstants.kRamseteB, TrajectoryFactory.AutoConstants.kRamseteZeta),
                        Constants.DrivetrainConstants.kDriveKinematics,
                        drivetrain::driveWithVelocity,
                        drivetrain);

        Command runThenStop = ramseteCommand.andThen(() -> drivetrain.stop());
        if (resetPosition) {
            Command resetPositionCommand = new SetRobotPoseCommand(drivetrain, trajectory.getInitialPose());
            return resetPositionCommand.andThen(runThenStop); // NOPMD
        }

        return runThenStop;
    }

    private DriveTrajectoryCommand() {

    }
}
