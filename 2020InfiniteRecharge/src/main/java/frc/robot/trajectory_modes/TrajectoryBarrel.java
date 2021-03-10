package frc.robot.trajectory_modes;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Chassis;

public class TrajectoryBarrel {
    public static Command barrel(Chassis chassis) {
        return TrajectoryUtils.startTrajectory("pathweaver_athome/Barrel/PathWeaver/Paths/barrel1", TrajectoryModeFactory.getTrajectoryConfig(), chassis);
    }
}
