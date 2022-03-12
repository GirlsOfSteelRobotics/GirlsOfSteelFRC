package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class FourBallTrajectories {

    public static Command fourBallPart1(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/4Ball_Part1", TrajectoryUtils.getTrajectoryConfig(), chassis);
    }

    public static Command fourBallPart2(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/4Ball_Part2.path", TrajectoryUtils.getTrajectoryConfig(), chassis);
    }

    public static Command fourBallPart3(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/4Ball_Part3path", TrajectoryUtils.getTrajectoryConfig(), chassis);
    }

    public static Command fourBallPart4(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/4Ball_Part4path", TrajectoryUtils.getTrajectoryConfig(), chassis);
    }

}
