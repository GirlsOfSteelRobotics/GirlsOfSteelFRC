package frc.robot.trajectory_modes;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Chassis;

public class TrajectorySlalom {
    //reads slalom and gives autonomous
    public static Command slalom(Chassis chassis) {
        return TrajectoryUtils.startTrajectory("pathweaver_athome/Slalom/PathWeaver/Paths/Slalom1", TrajectoryModeFactory.getTrajectoryConfig(), chassis);
    }
}
