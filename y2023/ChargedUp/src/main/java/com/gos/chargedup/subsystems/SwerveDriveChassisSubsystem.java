package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import com.pathplanner.lib.auto.BaseAutoBuilder;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.snobotv2.module_wrappers.ctre.CtrePigeonImuWrapper;
import org.snobotv2.sim_wrappers.SwerveModuleSimWrapper;
import org.snobotv2.sim_wrappers.SwerveSimWrapper;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SwerveDriveChassisSubsystem extends BaseChassis {

    private static final double WHEEL_BASE = 0.381;

    private static final double TRACK_WIDTH = 0.381;

    public static final double MAX_TRANSLATION_SPEED = 2.9;


    public static final double MAX_ROTATION_SPEED = Units.degreesToRadians(180);

    private static final Translation2d FRONT_LEFT_LOCATION = new Translation2d(WHEEL_BASE / 2, TRACK_WIDTH / 2);
    private static final Translation2d FRONT_RIGHT_LOCATION = new Translation2d(WHEEL_BASE / 2, -TRACK_WIDTH / 2);
    private static final Translation2d BACK_LEFT_LOCATION = new Translation2d(-WHEEL_BASE / 2, TRACK_WIDTH / 2);
    private static final Translation2d BACK_RIGHT_LOCATION = new Translation2d(-WHEEL_BASE / 2, -TRACK_WIDTH / 2);

    // Creating my kinematics object using the module locations
    private static final SwerveDriveKinematics SWERVE_KINEMATICS = new SwerveDriveKinematics(
        FRONT_LEFT_LOCATION, FRONT_RIGHT_LOCATION, BACK_LEFT_LOCATION, BACK_RIGHT_LOCATION
    );

    private final SwerveDriveModules m_frontLeft;
    private final SwerveDriveModules m_backLeft;
    private final SwerveDriveModules m_frontRight;
    private final SwerveDriveModules m_backRight;

    private final SwerveDriveModules[] m_modules;

    private final SwerveDriveOdometry m_odometry;
    private final SwerveDrivePoseEstimator m_poseEstimator;


    private SwerveSimWrapper m_simulator;

    public SwerveDriveChassisSubsystem() {

        m_frontLeft = new SwerveDriveModules(Constants.FRONT_LEFT_WHEEL, Constants.FRONT_LEFT_AZIMUTH, "FL");
        m_frontRight = new SwerveDriveModules(Constants.FRONT_RIGHT_WHEEL, Constants.FRONT_RIGHT_AZIMUTH, "FR");
        m_backLeft = new SwerveDriveModules(Constants.BACK_LEFT_WHEEL, Constants.BACK_LEFT_AZIMUTH, "BL");
        m_backRight = new SwerveDriveModules(Constants.BACK_RIGHT_WHEEL, Constants.BACK_RIGHT_AZIMUTH, "BR");
        m_modules = new SwerveDriveModules[]{m_frontLeft, m_frontRight, m_backLeft, m_backRight};

        m_odometry = new SwerveDriveOdometry(
            SWERVE_KINEMATICS, m_gyro.getRotation2d(),
            getModulePositions(), new Pose2d());

        m_poseEstimator = new SwerveDrivePoseEstimator(SWERVE_KINEMATICS, m_gyro.getRotation2d(), getModulePositions(), new Pose2d());

        if (RobotBase.isSimulation()) {
            List<SwerveModuleSimWrapper> moduleSims = List.of(
                m_frontLeft.getSimWrapper(),
                m_frontRight.getSimWrapper(),
                m_backLeft.getSimWrapper(),
                m_backRight.getSimWrapper());
            m_simulator = new SwerveSimWrapper(WHEEL_BASE, TRACK_WIDTH, 64.0, 1.0, moduleSims, new CtrePigeonImuWrapper(m_gyro));
        }
    }

    @Override
    protected void lockDriveTrain() {
        // TODO implement
    }

    @Override
    protected void unlockDriveTrain() {
        // TODO implement
    }

    public final SwerveModulePosition[] getModulePositions() {
        SwerveModulePosition[] modulePositions = new SwerveModulePosition[4];
        for (int i = 0; i < 4; i += 1) {
            modulePositions[i] = m_modules[i].getModulePosition();
        }
        return modulePositions;
    }

    public SwerveModuleState[] getModuleStates() {
        SwerveModuleState[] modulePositions = new SwerveModuleState[4];
        for (int i = 0; i < 4; i += 1) {
            modulePositions[i] = m_modules[i].getState();
        }
        return modulePositions;
    }

    @Override
    public void periodic() {
        m_odometry.update(m_gyro.getRotation2d(), getModulePositions());
        m_field.setOdometry(m_odometry.getPoseMeters());
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }

    public void setSpeeds(ChassisSpeeds speedsInp) {
        SwerveModuleState[] moduleStates = SWERVE_KINEMATICS.toSwerveModuleStates(speedsInp);
        setModuleStates(moduleStates);
    }

    public void setModuleStates(SwerveModuleState... moduleStates) {
        for (int i = 0; i < 4; i++) {
            m_modules[i].setState(moduleStates[i]);
        }
    }

    public void setModuleState(int moduleId, double degrees, double velocity) {
        m_modules[moduleId].setState(new SwerveModuleState(velocity, Rotation2d.fromDegrees(degrees)));
    }

    @Override
    public Pose2d getPose() {
        // TODO should be pose estimator
        return m_odometry.getPoseMeters();
    }

    @Override
    public void stop() {
        // TODO implement
    }

    @Override
    public void turnPID(double angleGoal) {
        // TODO implement
    }

    @Override
    public void autoEngage() {
        // TODO implement
    }

    @Override
    public void resetOdometry(Pose2d pose2d) {
        m_odometry.resetPosition(m_gyro.getRotation2d(), getModulePositions(), pose2d);
        m_poseEstimator.resetPosition(m_gyro.getRotation2d(), getModulePositions(), pose2d);
    }

    @Override
    public CommandBase driveToPointNoFlip(Pose2d start, Pose2d end, boolean reverse) {
        // TODO implement
        return new InstantCommand();
    }

    @Override
    public void resetStickyFaultsChassis() {
        // TODO implement
    }

    @Override
    public CommandBase syncOdometryWithPoseEstimator() {
        return runOnce(() ->  m_odometry.resetPosition(m_gyro.getRotation2d(), getModulePositions(), m_poseEstimator.getEstimatedPosition()))
        .withName("Sync Odometry /w Pose");
    }

    @Override
    public CommandBase selfTestMotors() {
        // TODO implement
        return new SequentialCommandGroup();
    }

    @Override
    protected BaseAutoBuilder createPathPlannerAutoBuilder(Map<String, Command> eventMap, Consumer<Pose2d> poseSetter) {
        return new SwerveAutoBuilder(
            this::getPose,
            poseSetter,
            new PIDConstants(0, 0, 0),
            new PIDConstants(0, 0, 0),
            this::setSpeeds,
            eventMap,
            this
        );
    }

    public CommandBase commandSetModuleState(int moduleId, double degrees, double velocity) {
        return this.run(() -> setModuleState(moduleId, degrees, velocity)).withName("Set Module State" + moduleId);
    }

    public CommandBase commandSetChassisSpeed(ChassisSpeeds chassisSp) {
        return this.run(() -> setSpeeds(chassisSp)).withName("Set Chassis Speeds" + chassisSp);
    }

}

