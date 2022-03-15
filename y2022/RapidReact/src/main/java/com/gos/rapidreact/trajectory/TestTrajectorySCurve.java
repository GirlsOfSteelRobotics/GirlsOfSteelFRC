package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TestTrajectorySCurve {
    public static CommandBase scurve(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/scurve", TrajectoryUtils.getDefaultTrajectoryConfig(), chassis);
    }
}
