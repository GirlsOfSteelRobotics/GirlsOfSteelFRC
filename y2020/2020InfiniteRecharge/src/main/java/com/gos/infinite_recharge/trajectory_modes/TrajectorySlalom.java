package com.gos.infinite_recharge.trajectory_modes;

import com.gos.infinite_recharge.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.Command;

public class TrajectorySlalom {
    //reads slalom and gives autonomous
    public static Command slalom(Chassis chassis) {
        return TrajectoryUtils.startTrajectory("pathweaver_athome/Slalom/PathWeaver/Paths/Slalom1", TrajectoryModeFactory.getTrajectoryConfig(), chassis);
    }
}
