package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class TestTrajectoryCurve {
    public static Command curve(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/curve", TrajectoryUtils.getDefaultTrajectoryConfig(), chassis);
    }
}
