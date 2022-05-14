package com.gos.codelabs.pid.commands.auton;

import com.gos.codelabs.pid.Constants;
import com.gos.codelabs.pid.auton_modes.TrajectoryFactory;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import com.gos.codelabs.pid.subsystems.ChassisSubsystem;

public final class DriveTrajectoryCommand {
    public static CommandBase createWithVelocity(ChassisSubsystem drivetrain, Trajectory trajectory, boolean resetPosition) {
        RamseteCommand ramseteCommand =
                new RamseteCommand(
                        trajectory,
                        drivetrain::getPose,
                        new RamseteController(TrajectoryFactory.AutoConstants.RAMSETE_B, TrajectoryFactory.AutoConstants.RAMSETE_ZETA),
                        Constants.DrivetrainConstants.DRIVE_KINEMATICS,
                        drivetrain::driveWithVelocity,
                        drivetrain);

        CommandBase runThenStop = ramseteCommand.andThen(() -> drivetrain.stop());
        if (resetPosition) {
            Command resetPositionCommand = new SetRobotPoseCommand(drivetrain, trajectory.getInitialPose());
            return resetPositionCommand.andThen(runThenStop); // NOPMD
        }

        return runThenStop;
    }

    private DriveTrajectoryCommand() {

    }
}
