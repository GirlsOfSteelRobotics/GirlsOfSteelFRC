package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class Trajectory47 {
    public static Command from4to7(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/47", TrajectoryUtils.getTrajectoryConfig(), chassis);
    }
}
