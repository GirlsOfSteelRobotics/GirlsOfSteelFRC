package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class TrajectoryA2 {
    public static Command fromAto2(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/A2", TrajectoryUtils.getDefaultTrajectoryConfig(), chassis);
    }
}
