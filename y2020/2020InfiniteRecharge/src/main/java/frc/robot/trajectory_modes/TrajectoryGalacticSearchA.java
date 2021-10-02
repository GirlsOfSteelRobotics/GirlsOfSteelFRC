package frc.robot.trajectory_modes;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Chassis;

public class TrajectoryGalacticSearchA {
    public static Command galacticSearchARed(Chassis chassis) {
        return TrajectoryUtils.startTrajectory("pathweaver_athome/GalacticSearchA/PathWeaver/Paths/GalacticSearchRed", TrajectoryModeFactory.getTrajectoryConfig(), chassis);
    }

    public static Command galacticSearchABlue(Chassis chassis) {
        return TrajectoryUtils.startTrajectory("pathweaver_athome/GalacticSearchA/PathWeaver/Paths/GalacticSearchBlue", TrajectoryModeFactory.getTrajectoryConfig(), chassis);
    }
}
