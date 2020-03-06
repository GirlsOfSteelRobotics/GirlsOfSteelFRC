package frc.robot.commands.autonomous;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Chassis;

public class FollowTrajectory extends SequentialCommandGroup {

    public static class AutoConstants {
        public static final double slowSpeedMetersPerSecond = Units.inchesToMeters(48);
        public static final double slowAccelerationMetersPerSecondSquared = Units.inchesToMeters(96);
        public static final double normalSpeedMetersPerSecond = Units.inchesToMeters(72);
        public static final double normalAccelerationMetersPerSecondSquared = Units.inchesToMeters(60);
        public static final double fastSpeedMetersPerSecond = Units.inchesToMeters(120);
        public static final double fastAccelerationMetersPerSecondSquared = Units.inchesToMeters(120);

        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;

    }

    public static class DriveConstants {
        public static final double ksVolts = 0.179;
        public static final double kvVoltSecondsPerMeter = 0.0653;
        public static final double kaVoltSecondsSquaredPerMeter = 0.00754;
        public static final double maxVoltage = 10;

        public static final double kTrackwidthMeters = 1.1554881713809029;
        public static final DifferentialDriveKinematics kDriveKinematics =
            new DifferentialDriveKinematics(kTrackwidthMeters);
    }

    private final NetworkTableEntry mIdealTableEntry;
    private final NetworkTableEntry mMeasuredTableEntry;
    private final Chassis m_chassis;
    private final Trajectory m_trajectory;
    private final Timer m_timer;

    private double m_goalVelocityLeft;
    private double m_goalVelocityRight;


    public FollowTrajectory(Trajectory trajectory, Chassis chassis) {

        this.m_chassis = chassis;
        this.m_trajectory = trajectory;
        this.m_timer = new Timer();

        NetworkTable trajectoryTable = NetworkTableInstance.getDefault().getTable("CoordinateGui").getSubTable("Ramsete Namespace");
        trajectoryTable.getEntry(".type").setString("Ramsete Namespace");

        mMeasuredTableEntry = trajectoryTable.getEntry("Measured");
        mIdealTableEntry = trajectoryTable.getEntry("Ideal");
        if (mIdealTableEntry.getString("").isEmpty()) {
            setIdealTrajectory(m_trajectory);
        }
        
        RamseteCommand ramseteCommand = new RamseteCommand(
            trajectory,
            m_chassis::getPose,
            new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta),
            DriveConstants.kDriveKinematics,
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
        System.out.println("Setting goal velocity: " + m_goalVelocityLeft + ", " + m_goalVelocityRight);
        m_chassis.smartVelocityControl(m_goalVelocityLeft, m_goalVelocityRight);
    }

    @Override
    public void initialize(){
        super.initialize();
        m_timer.start();
        setIdealTrajectory(m_trajectory);
    }

    @Override
    public void execute() {
        super.execute();

        StringBuilder output = new StringBuilder();
        output
            .append(m_timer.get()).append(",")
            .append(m_chassis.getX()).append(",")
            .append(m_chassis.getY()).append(',')
            .append(m_chassis.getHeading()).append(",")
            .append(m_goalVelocityLeft).append(",")
            .append(m_goalVelocityRight).append(",")
            .append(m_chassis.getLeftEncoderSpeed()).append(",")
            .append(m_chassis.getRightEncoderSpeed()).append(",");

        mMeasuredTableEntry.setString(output.toString());
    }

    private void setIdealTrajectory(Trajectory trajectory) {
        StringBuilder output = new StringBuilder();

        for (Trajectory.State state : trajectory.getStates()) {

            double heading = state.poseMeters.getRotation().getDegrees();
            double xInches = Units.metersToInches(state.poseMeters.getTranslation().getX());
            double yInches = Units.metersToInches(state.poseMeters.getTranslation().getY());
            output
                .append(state.timeSeconds).append(",")
                .append(Units.metersToInches(state.velocityMetersPerSecond)).append(",")
                .append(xInches).append(",")
                .append(yInches).append(',')
                .append(heading).append(",");
        }

        mIdealTableEntry.forceSetString("");
        mIdealTableEntry.setString(output.toString());
    }

    @Override
    public String getName()
    {
        return "FollowTrajectory";
    }
}
