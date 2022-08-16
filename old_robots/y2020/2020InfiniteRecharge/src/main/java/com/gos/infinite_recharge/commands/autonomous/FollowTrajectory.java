package com.gos.infinite_recharge.commands.autonomous;

import com.gos.infinite_recharge.Constants;
import com.gos.infinite_recharge.subsystems.Chassis;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.snobotv2.coordinate_gui.RamsetePublisher;

public class FollowTrajectory extends SequentialCommandGroup {

    private final Chassis m_chassis;
    private final Trajectory m_trajectory;
    private final RamsetePublisher m_ramsetePublisher;

    private double m_goalVelocityLeft;
    private double m_goalVelocityRight;

    public static class AutoConstants {
        public static final double SLOW_SPEED_METERS_PER_SECOND = Units.inchesToMeters(48);
        public static final double SLOW_ACCELERATION_METERS_PER_SECOND_SQUARED = Units.inchesToMeters(96);
        public static final double NORMAL_SPEED_METERS_PER_SECOND = Units.inchesToMeters(72);
        public static final double NORMAL_ACCELERATION_METERS_PER_SECOND_SQUARED = Units.inchesToMeters(60);
        public static final double FAST_SPEED_METERS_PER_SECOND = Units.inchesToMeters(120);
        public static final double FAST_ACCELERATION_METERS_PER_SECOND_SQUARED = Units.inchesToMeters(120);

        public static final double RAMSETE_B = 2;
        public static final double RAMSETE_ZETA = 0.7;

    }

    public FollowTrajectory(Trajectory trajectory, Chassis chassis) {

        this.m_chassis = chassis;
        this.m_trajectory = trajectory;

        m_ramsetePublisher = new RamsetePublisher();

        RamseteCommand ramseteCommand = new RamseteCommand(
            trajectory,
            m_chassis::getPose,
            new RamseteController(AutoConstants.RAMSETE_B, AutoConstants.RAMSETE_ZETA),
            Constants.DriveConstants.DRIVE_KINEMATICS,
            this::setVelocityGoal,
            m_chassis
        );

        addCommands(ramseteCommand);
        addCommands(new InstantCommand(() -> m_chassis.stop(), m_chassis));
    }

    private void setVelocityGoal(double leftVelocityMeters, double rightVelocityMeters) {

        double leftVelocityInchesPerSecond = Units.metersToInches(leftVelocityMeters);
        double rightVelocityInchesPerSecond = Units.metersToInches(rightVelocityMeters);
        m_goalVelocityLeft = leftVelocityInchesPerSecond;
        m_goalVelocityRight = rightVelocityInchesPerSecond;
        //System.out.println("Setting goal velocity: " + m_goalVelocityLeft + ", " + m_goalVelocityRight);
        m_chassis.smartVelocityControl(m_goalVelocityLeft, m_goalVelocityRight);
    }

    @Override
    public void initialize() {
        super.initialize();
        m_ramsetePublisher.initialize(m_trajectory);
    }

    @Override
    public void execute() {
        super.execute();

        m_ramsetePublisher.addMeasurement(m_chassis.getPose(),
                new DifferentialDriveWheelSpeeds(Units.inchesToMeters(m_goalVelocityLeft), Units.inchesToMeters(m_goalVelocityRight)),
                new DifferentialDriveWheelSpeeds(Units.inchesToMeters(m_chassis.getLeftEncoderSpeed()), Units.inchesToMeters(m_chassis.getRightEncoderSpeed())));
    }

    @Override
    public String getName() {
        return "FollowTrajectory";
    }
}
