package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TestTrajectoryStraight {
    public static CommandBase backFrom7(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/straight", TrajectoryUtils.getDefaultTrajectoryConfig(), chassis);
    }

}
