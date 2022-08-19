package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class OneBallTrajectory {
    public static Command oneBallHigh(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/OneBallHigh", TrajectoryUtils.getSlowerTrajectoryConfig(), chassis);
    }
}
