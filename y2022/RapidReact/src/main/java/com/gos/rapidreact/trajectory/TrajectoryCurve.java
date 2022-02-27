package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TrajectoryCurve {
    public static CommandBase curve(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/curve", TrajectoryUtils.getTrajectoryConfig(), chassis);
    }
}
