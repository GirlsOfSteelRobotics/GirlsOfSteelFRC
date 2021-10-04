package com.gos.infinite_recharge.trajectory_modes;

import com.gos.infinite_recharge.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.Command;

public class TrajectoryBarrel {
    public static Command barrel(Chassis chassis) {
        return TrajectoryUtils.startTrajectory("pathweaver_athome/Barrel/PathWeaver/Paths/barrel1", TrajectoryModeFactory.getTrajectoryConfig(), chassis);
    }
}
