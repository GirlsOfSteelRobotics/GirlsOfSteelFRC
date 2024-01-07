package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class TwoBallTrajectory {
    public static Command twoBallHighPart1(ChassisSubsystem chassis) {
        return TrajectoryUtils.startTrajectory("PathWeaver/Paths/TwoBallHighPart1", TrajectoryUtils.getSlowerTrajectoryConfig(), chassis);
    }
}
