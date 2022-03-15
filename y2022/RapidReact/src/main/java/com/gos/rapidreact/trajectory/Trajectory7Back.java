package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class Trajectory7Back {
    public static Command backFrom7(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/7Back", TrajectoryUtils.getDefaultTrajectoryConfig(), chassis);
    }
}
