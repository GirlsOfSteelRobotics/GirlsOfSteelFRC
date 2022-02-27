package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TrajectoryB5 {
    public static CommandBase fromBto5(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/B5", TrajectoryUtils.getTrajectoryConfig(), chassis);
    }
}
