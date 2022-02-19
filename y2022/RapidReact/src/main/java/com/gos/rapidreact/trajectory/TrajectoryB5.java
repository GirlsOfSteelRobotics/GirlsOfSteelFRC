package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class TrajectoryB5 {
    public static Command b5(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/B5", TrajectoryUtils.getTrajectoryConfig(), chassis);
    }
}
