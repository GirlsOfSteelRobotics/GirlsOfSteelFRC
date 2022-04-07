package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.commands.autonomous.FollowTrajectory;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class SabotageTrajectories {
    public static Command sabotageHighPart1(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/sabotagehigh_part1", TrajectoryUtils.getSlowerTrajectoryConfig(), chassis);
    }

    public static Command sabotageLowPart2(ChassisSubsystem chassis) {
        return TrajectoryUtils.createTrajectory("PathWeaver/Paths/sabotagelow_part2", TrajectoryUtils.getSlowerTrajectoryConfig(), chassis);
    }

    public static Command sabotageLowPart3(ChassisSubsystem chassis) {
        return TrajectoryUtils.createTrajectory("PathWeaver/Paths/sabotagelow_part3", TrajectoryUtils.getSlowerTrajectoryConfig(), chassis);
    }

    public static Command sabotageHighPart2(ChassisSubsystem chassis) {
        return TrajectoryUtils.createTrajectory("PathWeaver/Paths/sabotagehigh_part2", TrajectoryUtils.getSlowerTrajectoryConfig(), chassis);
    }

    public static double getPartTwoStartAngle(ChassisSubsystem chassis) {
        FollowTrajectory trajectory = TrajectoryUtils.createTrajectory("PathWeaver/Paths/sabotagehigh_part2", TrajectoryUtils.getSlowerTrajectoryConfig(), chassis);
        return trajectory.getInitialPose().getRotation().getDegrees();
    }

    public static FollowTrajectory sabotageHighPart3(ChassisSubsystem chassis) {
        return TrajectoryUtils.createTrajectory("PathWeaver/Paths/sabotagehigh_part3", TrajectoryUtils.getSlowerTrajectoryConfig().setReversed(true), chassis);
    }
}
