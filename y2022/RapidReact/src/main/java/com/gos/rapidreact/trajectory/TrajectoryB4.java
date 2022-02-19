package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class TrajectoryB4 {
    public static Command b4(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/B4", TrajectoryUtils.getTrajectoryConfig(), chassis);
    }
}
