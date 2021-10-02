package frc.robot.trajectory_modes;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Chassis;

public class TrajectoryGalacticSearchB {
    public static Command galacticSearchBRed(Chassis chassis) {
        return TrajectoryUtils.startTrajectory("pathweaver_athome/GalacticSearchB/PathWeaver/Paths/red.path", TrajectoryModeFactory.getTrajectoryConfig(), chassis);
    }

    public static Command galacticSearchBBlue(Chassis chassis) {
        return TrajectoryUtils.startTrajectory("pathweaver_athome/GalacticSearchB/PathWeaver/Paths/blue.path", TrajectoryModeFactory.getTrajectoryConfig(), chassis);
    }
}
