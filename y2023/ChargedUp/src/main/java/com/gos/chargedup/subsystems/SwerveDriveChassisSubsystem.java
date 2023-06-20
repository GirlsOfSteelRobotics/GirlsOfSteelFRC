package com.gos.chargedup.subsystems;


import com.ctre.phoenix.sensors.WPI_Pigeon2;
import com.gos.chargedup.Constants;
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
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.ctre.CtrePigeonImuWrapper;
import org.snobotv2.sim_wrappers.SwerveModuleSimWrapper;
import org.snobotv2.sim_wrappers.SwerveSimWrapper;

import java.util.List;

public class SwerveDriveChassisSubsystem extends SubsystemBase {

    private static final double WHEEL_BASE = 0.381;

    private static final double TRACK_WIDTH = 0.381;

    public static final double MAX_TRANSLATION_SPEED = 2.9;

    public static final double MAX_ROTATION_SPEED = Units.degreesToRadians(180);

    private static final Translation2d FRONT_LEFT_LOCATION = new Translation2d(WHEEL_BASE / 2, TRACK_WIDTH / 2);
    private static final Translation2d FRONT_RIGHT_LOCATION = new Translation2d(WHEEL_BASE / 2, -TRACK_WIDTH / 2);
    private static final Translation2d BACK_LEFT_LOCATION = new Translation2d(-WHEEL_BASE / 2, TRACK_WIDTH / 2);
    private static final Translation2d BACK_RIGHT_LOCATION = new Translation2d(-WHEEL_BASE / 2, -TRACK_WIDTH / 2);

    private final SwerveDriveModules m_frontLeft;
    private final SwerveDriveModules m_backLeft;
    private final SwerveDriveModules m_frontRight;
    private final SwerveDriveModules m_backRight;

    private final SwerveDriveModules[] m_modules;

    private final WPI_Pigeon2 m_gyro;

    private final SwerveDriveOdometry m_swerveDriveOdom;

    // Creating my kinematics object using the module locations
    private static final SwerveDriveKinematics SWERVE_KINEMATICS = new SwerveDriveKinematics(
        FRONT_LEFT_LOCATION, FRONT_RIGHT_LOCATION, BACK_LEFT_LOCATION, BACK_RIGHT_LOCATION
    );

    private final Field2d m_swerveField;

    private SwerveSimWrapper m_simulator;

    public SwerveDriveChassisSubsystem() {

        m_swerveField = new Field2d();
        SmartDashboard.putData("SwerveField", m_swerveField);

        m_frontLeft = new SwerveDriveModules(Constants.FRONT_LEFT_WHEEL, Constants.FRONT_LEFT_AZIMUTH, "FL");
        m_frontRight = new SwerveDriveModules(Constants.FRONT_RIGHT_WHEEL, Constants.FRONT_RIGHT_AZIMUTH, "FR");
        m_backLeft = new SwerveDriveModules(Constants.BACK_LEFT_WHEEL, Constants.BACK_LEFT_AZIMUTH, "BL");
        m_backRight = new SwerveDriveModules(Constants.BACK_RIGHT_WHEEL, Constants.BACK_RIGHT_AZIMUTH, "BR");
        m_modules = new SwerveDriveModules[]{m_frontLeft, m_frontRight, m_backLeft, m_backRight};
        m_gyro = new WPI_Pigeon2(Constants.PIGEON_PORT);
        m_swerveDriveOdom = new SwerveDriveOdometry(
            SWERVE_KINEMATICS, m_gyro.getRotation2d(),
            new SwerveModulePosition[] {
                m_frontLeft.getModulePosition(),
                m_frontRight.getModulePosition(),
                m_backLeft.getModulePosition(),
                m_backRight.getModulePosition()
            }, new Pose2d(0, 0, new Rotation2d()));

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
    public void periodic() {
        SwerveModulePosition[] modulePositions = new SwerveModulePosition[4];
        for (int i = 0; i < 4; i += 1) {
            modulePositions[i] = m_modules[i].getModulePosition();
        }
        m_swerveDriveOdom.update(m_gyro.getRotation2d(), modulePositions);
        m_swerveField.setRobotPose(m_swerveDriveOdom.getPoseMeters());

    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }

    public void setSpeeds(ChassisSpeeds speedsInp) {
        SwerveModuleState[] moduleStates = SWERVE_KINEMATICS.toSwerveModuleStates(speedsInp);
        for (int i = 0; i < 4; i++) {
            m_modules[i].setState(moduleStates[i]);
        }
    }

    public void setModuleState(int moduleId, double degrees, double velocity) {
        m_modules[moduleId].setState(new SwerveModuleState(velocity, Rotation2d.fromDegrees(degrees)));
    }

    public CommandBase commandSetModuleState(int moduleId, double degrees, double velocity) {
        return this.run(() -> setModuleState(moduleId, degrees, velocity)).withName("Set Module State" + moduleId);
    }

    public CommandBase commandSetChassisSpeed(ChassisSpeeds chassisSp) {
        return this.run(() -> setSpeeds(chassisSp)).withName("Set Chassis Speeds" + chassisSp);
    }

}

