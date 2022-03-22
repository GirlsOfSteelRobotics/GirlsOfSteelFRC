package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj2.command.Command;

public class FourBallTrajectories {

    public static Command fourBallPart1(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/4Ball_Part1", TrajectoryUtils.getSlowerTrajectoryConfig(), chassis);
    }

    public static Command fourBallLowPart2(ChassisSubsystem chassis) {
        TrajectoryConfig config = TrajectoryUtils.getDefaultTrajectoryConfig();
        config.setReversed(true);
        return TrajectoryUtils.createTrajectory("PathWeaver/Paths/4Ball_Part2.path", config, chassis);
    }

    public static Command fourBallLowPart3(ChassisSubsystem chassis) {
        return TrajectoryUtils.createTrajectory("PathWeaver/Paths/4Ball_Part3path", TrajectoryUtils.getDefaultTrajectoryConfig(), chassis);
    }

    public static Command fourBallLowPart4(ChassisSubsystem chassis) {
        TrajectoryConfig config = TrajectoryUtils.getDefaultTrajectoryConfig();
        config.setReversed(true);
        return TrajectoryUtils.createTrajectory("PathWeaver/Paths/4Ball_Part4path", config, chassis);
    }

    public static Command fourBallHighPart3(ChassisSubsystem chassis) {
        TrajectoryConfig config = TrajectoryUtils.getDefaultTrajectoryConfig();
        return TrajectoryUtils.createTrajectory("PathWeaver/Paths/4Ball_High_Part3", config, chassis);
    }

    public static Command fourBallHighPart4(ChassisSubsystem chassis) {
        TrajectoryConfig config = TrajectoryUtils.getDefaultTrajectoryConfig();
        config.setReversed(true);
        return TrajectoryUtils.createTrajectory("PathWeaver/Paths/4Ball_High_Part4", config, chassis);
    }

}
