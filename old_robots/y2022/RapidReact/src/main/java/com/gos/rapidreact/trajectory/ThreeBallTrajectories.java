package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class ThreeBallTrajectories {

    public static Command tarmacToFirstBall(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/B5", TrajectoryUtils.getDefaultTrajectoryConfig(), chassis);
    }

    public static Command firstBallToSecondBall(ChassisSubsystem chassis) {
        return TrajectoryUtils.createTrajectory("PathWeaver/Paths/54", TrajectoryUtils.getDefaultTrajectoryConfig(), chassis);
    }
}
