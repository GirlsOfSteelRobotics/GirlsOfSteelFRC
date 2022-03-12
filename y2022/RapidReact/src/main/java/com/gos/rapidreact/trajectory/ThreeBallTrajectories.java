package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ThreeBallTrajectories {

    public static CommandBase tarmacToFirstBall(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/B5", TrajectoryUtils.getTrajectoryConfig(), chassis);
    }

    public static Command firstBallToSecondBall(ChassisSubsystem chassis) {
        return TrajectoryUtils.createTrajectory("PathWeaver/Paths/54", TrajectoryUtils.getTrajectoryConfig(), chassis);
    }
}
