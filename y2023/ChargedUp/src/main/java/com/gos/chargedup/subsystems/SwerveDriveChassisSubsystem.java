package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import com.gos.chargedup.GosSwerveAutoBuilder;
import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.properties.pid.WpiPidPropertyBuilder;
import com.gos.lib.rev.swerve.RevSwerveChassis;
import com.gos.lib.rev.swerve.RevSwerveChassisConstants;
import com.gos.lib.rev.swerve.RevSwerveModuleConstants;
import com.pathplanner.lib.auto.BaseAutoBuilder;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.snobotv2.module_wrappers.ctre.CtrePigeonImuWrapper;

import java.util.Map;
import java.util.function.Consumer;

public class SwerveDriveChassisSubsystem extends BaseChassis {

    private static final double WHEEL_BASE = 0.381;
    private static final double TRACK_WIDTH = 0.381;

    public static final double MAX_TRANSLATION_SPEED = 2.9;
    public static final double MAX_ROTATION_SPEED = Units.degreesToRadians(180);

    private final PIDController m_xTranslationPidController;
    private final PIDController m_yTranslationPidController;
    private final PIDController m_rotationPidController;
    private final PidProperty m_xTranslationPidProperties;
    private final PidProperty m_yTranslationPidProperties;
    private final PidProperty m_rotationPidProperties;

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
        m_swerveDrive = new RevSwerveChassis(swerveConstants, m_gyro::getRotation2d, new CtrePigeonImuWrapper(m_gyro));

        m_xTranslationPidController = new PIDController(0, 0, 0);
        m_xTranslationPidProperties = new WpiPidPropertyBuilder("SwerveTranslation", false, m_xTranslationPidController)
            .addP(0)
            .addD(0)
            .build();

        m_yTranslationPidController = new PIDController(0, 0, 0);
        m_yTranslationPidProperties = new WpiPidPropertyBuilder("SwerveTranslation", false, m_yTranslationPidController)
            .addP(0)
            .addD(0)
            .build();

        m_rotationPidController = new PIDController(0, 0, 0);
        m_rotationPidProperties = new WpiPidPropertyBuilder("SwerveRotation", false, m_rotationPidController)
            .addP(0)
            .addD(0)
            .build();

        PPSwerveControllerCommand.setLoggingCallbacks(
            m_field::setTrajectory,
            m_field::setTrajectorySetpoint,
            this::logTrajectoryChassisSetpoint,
            this::logTrajectoryErrors
        );
    }

    private void logTrajectoryChassisSetpoint(ChassisSpeeds chassisSpeeds) {
        m_trajectoryVelocitySetpointX.setNumber(chassisSpeeds.vxMetersPerSecond);
        m_trajectoryVelocitySetpointY.setNumber(chassisSpeeds.vyMetersPerSecond);
        m_trajectoryVelocitySetpointAngle.setNumber(chassisSpeeds.omegaRadiansPerSecond);
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
        m_xTranslationPidProperties.updateIfChanged();
        m_yTranslationPidProperties.updateIfChanged();
        m_rotationPidProperties.updateIfChanged();

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
    protected BaseAutoBuilder createPathPlannerAutoBuilder(Map<String, Command> eventMap, Consumer<Pose2d> poseSetter) {
        return new GosSwerveAutoBuilder(
            this::getPose,
            poseSetter,
            m_xTranslationPidController,
            m_yTranslationPidController,
            m_rotationPidController,
            this::setChassisSpeed,
            eventMap,
            this
        );
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
    public CommandBase createDriveToPointNoFlipCommand(Pose2d start, Pose2d end, boolean reverse) {
        // TODO implement
        return new InstantCommand();
    }

    @Override
    public CommandBase createSyncOdometryWithPoseEstimatorCommand() {
        return runOnce(m_swerveDrive::syncOdometryWithPoseEstimator).withName("Sync Odometry /w Pose");
    }

    @Override
    public CommandBase createSelfTestMotorsCommand() {
        // TODO implement
        return new SequentialCommandGroup();
    }

    public CommandBase commandSetChassisSpeed(ChassisSpeeds chassisSp) {
        return this.run(() -> setChassisSpeed(chassisSp)).withName("Set Chassis Speeds" + chassisSp);
    }

    public CommandBase commandLockDrivetrain() {
        return this.run(this::lockDriveTrain).withName("Lock Wheels Command");
    }

    public CommandBase commandSetModuleState(int moduleId, double degrees, double velocity) {
        return this.run(() -> m_swerveDrive.setModuleState(moduleId, degrees, velocity)).withName("Module " + moduleId + "(" + degrees + ", " + velocity + ")");
    }

    public CommandBase commandSetPercentSpeeds(double percentAzimuth, double percentWheel) {
        return this.run(() -> m_swerveDrive.setChassisSpeedsPercent(percentWheel, percentAzimuth));
    }

}
