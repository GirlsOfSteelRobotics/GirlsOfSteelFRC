package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class Trajectory54 {
    public static Command from5to4(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/54", TrajectoryUtils.getTrajectoryConfig(), chassis);
    }
}
