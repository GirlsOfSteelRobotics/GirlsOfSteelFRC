package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command;

public class FiveBallTrajectories {

    public static Command tarmacToFirstBall(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/B5", TrajectoryUtils.getDefaultTrajectoryConfig(), chassis);
    }

    public static Command firstBallToSecondBall(ChassisSubsystem chassis) {
        return TrajectoryUtils.createTrajectory("PathWeaver/Paths/54", TrajectoryUtils.getDefaultTrajectoryConfig(), chassis);
    }

    public static Command from4to7(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/47", TrajectoryUtils.getDefaultTrajectoryConfig(), chassis);
    }
}
