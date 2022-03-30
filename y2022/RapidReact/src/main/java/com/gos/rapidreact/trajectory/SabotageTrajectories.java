package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class SabotageTrajectories {
    public static Command sabotageLowPart2(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/sabotagelow_part2", TrajectoryUtils.getSlowerTrajectoryConfig(), chassis);
    }

    public static Command sabotageLowPart3(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/sabotagelow_part3", TrajectoryUtils.getSlowerTrajectoryConfig(), chassis);
    }

    public static Command sabotageHighPart2(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/sabotagehigh_part2", TrajectoryUtils.getSlowerTrajectoryConfig(), chassis);
    }

    public static Command sabotageHighPart3(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/sabotagehigh_part3", TrajectoryUtils.getSlowerTrajectoryConfig(), chassis);
    }
}
