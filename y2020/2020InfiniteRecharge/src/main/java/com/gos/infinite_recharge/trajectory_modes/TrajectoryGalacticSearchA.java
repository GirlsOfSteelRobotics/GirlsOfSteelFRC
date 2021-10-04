package com.gos.infinite_recharge.trajectory_modes;

import com.gos.infinite_recharge.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.Command;

public class TrajectoryGalacticSearchA {
    public static Command galacticSearchARed(Chassis chassis) {
        return TrajectoryUtils.startTrajectory("pathweaver_athome/GalacticSearchA/PathWeaver/Paths/GalacticSearchRed", TrajectoryModeFactory.getTrajectoryConfig(), chassis);
    }

    public static Command galacticSearchABlue(Chassis chassis) {
        return TrajectoryUtils.startTrajectory("pathweaver_athome/GalacticSearchA/PathWeaver/Paths/GalacticSearchBlue", TrajectoryModeFactory.getTrajectoryConfig(), chassis);
    }
}
