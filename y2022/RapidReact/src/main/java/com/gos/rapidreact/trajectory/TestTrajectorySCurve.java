package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class TestTrajectorySCurve {
    public static Command scurve(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/scurve", TrajectoryUtils.getDefaultTrajectoryConfig(), chassis);
    }
}
