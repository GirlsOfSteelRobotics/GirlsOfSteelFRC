package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Chassis;

public class FollowTrajectory extends SequentialCommandGroup {

    public static class AutoConstants {
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;

        public static final double kMaxSpeedMetersPerSecond = Units.inchesToMeters(60);
        public static final double kMaxAccelerationMetersPerSecondSquared = Units.inchesToMeters(60);

    }

    public static class DriveConstants {
        public static final double ksVolts = 0.179;
        public static final double kvVoltSecondsPerMeter = 0.0653;
        public static final double kaVoltSecondsSquaredPerMeter = 0.00754;

        public static final double kTrackwidthMeters = 1.1554881713809029;
        public static final DifferentialDriveKinematics kDriveKinematics =
            new DifferentialDriveKinematics(kTrackwidthMeters);
    }

    Chassis m_chassis;


    public FollowTrajectory(Trajectory trajectory, Chassis chassis) {

        // Use requires() here to declare subsystem dependencies
        //super.addRequirements(Shooter); When a subsystem is written, add the requires line back in.
        this.m_chassis = chassis;
        
        RamseteCommand ramseteCommand = new RamseteCommand(
            trajectory,
            m_chassis::getPose,
            new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta),
            DriveConstants.kDriveKinematics,
            m_chassis::smartVelocityControlMeters,
            m_chassis
        );

        addCommands(ramseteCommand);
    }
}
