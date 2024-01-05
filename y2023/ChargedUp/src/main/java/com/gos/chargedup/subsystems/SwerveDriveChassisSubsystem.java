package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import com.gos.lib.rev.swerve.RevSwerveChassis;
import com.gos.lib.rev.swerve.RevSwerveChassisConstants;
import com.gos.lib.rev.swerve.RevSwerveModuleConstants;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.snobotv2.module_wrappers.phoenix5.Pigeon2Wrapper;

public class SwerveDriveChassisSubsystem extends BaseChassis {

    private static final double WHEEL_BASE = 0.381;
    private static final double TRACK_WIDTH = 0.381;

    public static final double MAX_TRANSLATION_SPEED = 2.9;
    public static final double MAX_ROTATION_SPEED = Units.degreesToRadians(180);

    private final RevSwerveChassis m_swerveDrive;

    public SwerveDriveChassisSubsystem() {
        RevSwerveChassisConstants swerveConstants = new RevSwerveChassisConstants(
            Constants.FRONT_LEFT_WHEEL, Constants.FRONT_LEFT_AZIMUTH,
            Constants.BACK_LEFT_WHEEL, Constants.BACK_LEFT_AZIMUTH,
            Constants.FRONT_RIGHT_WHEEL, Constants.FRONT_RIGHT_AZIMUTH,
            Constants.BACK_RIGHT_WHEEL, Constants.BACK_RIGHT_AZIMUTH,
            RevSwerveModuleConstants.DriveMotorTeeth.T14,
            WHEEL_BASE, TRACK_WIDTH,
            MAX_TRANSLATION_SPEED,
            MAX_ROTATION_SPEED
        );
        m_swerveDrive = new RevSwerveChassis(swerveConstants, m_gyro::getRotation2d, new Pigeon2Wrapper(m_gyro));

        AutoBuilder.configureHolonomic(
            this::getPose,
            this::resetOdometry,
            this::getChassisSpeed,
            this::setChassisSpeed,
            new HolonomicPathFollowerConfig(
                new PIDConstants(5, 0, 0),
                new PIDConstants(5, 0, 0),
                MAX_TRANSLATION_SPEED,
                WHEEL_BASE,
                new ReplanningConfig(),
                0.02),
            this
        );
    }

    @Override
    protected void lockDriveTrain() {
        m_swerveDrive.setWheelsToXShape();
    }

    @Override
    protected void unlockDriveTrain() {
        // TODO implement
    }

    @Override
    public void periodic() {
        m_swerveDrive.periodic();

        m_field.setOdometry(m_swerveDrive.getOdometryPosition());
        m_field.setPoseEstimate(m_swerveDrive.getEstimatedPosition());
    }

    @Override
    public void simulationPeriodic() {
        m_swerveDrive.updateSimulator();
    }

    @Override
    public void setChassisSpeed(ChassisSpeeds speed) {
        m_swerveDrive.setChassisSpeeds(speed);
    }

    @Override
    public ChassisSpeeds getChassisSpeed() {
        return m_swerveDrive.getChassisSpeed();
    }

    @Override
    public void clearStickyFaults() {
        m_swerveDrive.clearStickyFaults();
    }

    @Override
    public Pose2d getPose() {
        return m_swerveDrive.getEstimatedPosition();
    }

    @Override
    public void stop() {
        m_swerveDrive.stop();
    }

    @Override
    public void turnToAngle(double angleGoal) {
        // TODO implement
    }

    @Override
    public void autoEngage() {
        // TODO implement
    }

    @Override
    public void resetOdometry(Pose2d pose2d) {
        m_swerveDrive.resetOdometry(pose2d);
    }

    @Override
    public Command createFollowPathCommand(PathPlannerPath path, boolean resetPose) {
        Command followPathCommand = AutoBuilder.followPathWithEvents(path);
        if (resetPose) {
            Pose2d pose = path.getPreviewStartingHolonomicPose();
            return Commands.runOnce(() -> resetOdometry(pose)).andThen(followPathCommand);
        }
        return followPathCommand;
    }

    @Override
    public Command createDriveToPointNoFlipCommand(Pose2d start, Pose2d end, boolean reverse) {
        // TODO implement
        return new InstantCommand();
    }

    @Override
    public Command createSyncOdometryWithPoseEstimatorCommand() {
        return runOnce(m_swerveDrive::syncOdometryWithPoseEstimator).withName("Sync Odometry /w Pose");
    }

    @Override
    public Command createSelfTestMotorsCommand() {
        // TODO implement
        return new SequentialCommandGroup();
    }

    public Command commandSetChassisSpeed(ChassisSpeeds chassisSp) {
        return this.run(() -> setChassisSpeed(chassisSp)).withName("Set Chassis Speeds" + chassisSp);
    }

    public Command commandLockDrivetrain() {
        return this.run(this::lockDriveTrain).withName("Lock Wheels Command");
    }

    public Command commandSetModuleState(int moduleId, double degrees, double velocity) {
        return this.run(() -> m_swerveDrive.setModuleState(moduleId, degrees, velocity)).withName("Module " + moduleId + "(" + degrees + ", " + velocity + ")");
    }

    public Command commandSetPercentSpeeds(double percentAzimuth, double percentWheel) {
        return this.run(() -> m_swerveDrive.setChassisSpeedsPercent(percentWheel, percentAzimuth));
    }

}
