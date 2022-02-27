package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TrajectoryB54 {
    public static CommandBase fromBto5to4(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/B54", TrajectoryUtils.getTrajectoryConfig(), chassis);
    }
}
