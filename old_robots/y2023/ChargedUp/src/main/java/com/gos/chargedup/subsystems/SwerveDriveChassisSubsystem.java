package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import com.gos.lib.GetAllianceUtil;
import com.gos.lib.rev.RevMotorControllerModel;
import com.gos.lib.rev.RevMotorModel;
import com.gos.lib.rev.swerve.RevSwerveChassis;
import com.gos.lib.rev.swerve.config.RevSwerveChassisConstants;
import com.gos.lib.rev.swerve.config.RevSwerveChassisConstantsBuilder;
import com.gos.lib.rev.swerve.config.SwerveGearingKit;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.json.simple.parser.ParseException;
import org.snobotv2.module_wrappers.phoenix6.Pigeon2Wrapper;

import java.io.IOException;

public class SwerveDriveChassisSubsystem extends BaseChassis {

    private static final double WHEEL_BASE = 0.381;
    private static final double TRACK_WIDTH = 0.381;

    public static final double MAX_TRANSLATION_SPEED = 2.9;
    public static final double MAX_ROTATION_SPEED = Units.degreesToRadians(180);

    private final RevSwerveChassis m_swerveDrive;

    public SwerveDriveChassisSubsystem() {
        RevSwerveChassisConstantsBuilder chassisBuilder = new RevSwerveChassisConstantsBuilder()
            .withFrontLeftConfig(Constants.FRONT_LEFT_WHEEL, Constants.FRONT_LEFT_AZIMUTH)
            .withFrontRightConfig(Constants.FRONT_RIGHT_WHEEL, Constants.FRONT_RIGHT_AZIMUTH)
            .withRearLeftConfig(Constants.BACK_LEFT_WHEEL, Constants.BACK_LEFT_AZIMUTH)
            .withRearRightConfig(Constants.BACK_RIGHT_WHEEL, Constants.BACK_RIGHT_AZIMUTH)
            .withDrivingMotorType(RevMotorModel.VORTEX, RevMotorControllerModel.SPARK_FLEX)
            .withTrackwidth(TRACK_WIDTH)
            .withWheelBase(WHEEL_BASE)
            .withMaxTranslationSpeed(MAX_TRANSLATION_SPEED)
            .withMaxRotationSpeed(MAX_ROTATION_SPEED)
            .withGearing(SwerveGearingKit.HIGH);

        RevSwerveChassisConstants swerveConstants = chassisBuilder.build(true);


        m_swerveDrive = new RevSwerveChassis(swerveConstants, m_gyro::getRotation2d, new Pigeon2Wrapper(m_gyro));

        RobotConfig config;
        try {
            config = RobotConfig.fromGUISettings();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        AutoBuilder.configure(
            this::getPose,
            this::resetOdometry,
            this::getChassisSpeed,
            this::setChassisSpeed,
            new PPHolonomicDriveController(
                new PIDConstants(5, 0, 0),
                new PIDConstants(10, 0, 0)),
            config,
            GetAllianceUtil::isRedAlliance,
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
        Command followPathCommand = AutoBuilder.followPath(path);
        if (resetPose) { // NOPMD
            // TODO this probably isn't good
            //            Pose2d pose = path.getPreviewStartingHolonomicPose();
            //            return Commands.runOnce(() -> resetOdometry(pose)).andThen(followPathCommand);
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
