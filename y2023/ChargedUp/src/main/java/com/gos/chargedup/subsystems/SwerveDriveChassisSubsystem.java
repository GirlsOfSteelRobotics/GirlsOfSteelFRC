package com.gos.chargedup.subsystems;


import com.ctre.phoenix.sensors.PigeonIMU;
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
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.swerve.SwerveModuleSim;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.ctre.CtrePigeonImuWrapper;
import org.snobotv2.module_wrappers.wpi.ADXRS450GyroWrapper;
import org.snobotv2.sim_wrappers.SwerveModuleSimWrapper;
import org.snobotv2.sim_wrappers.SwerveSimWrapper;

import java.util.List;

public class SwerveDriveChassisSubsystem extends SubsystemBase {


    private static final double WHEEL_BASE = 0.381;

    private static final double TRACK_WIDTH = 0.381;

    public static final double MAX_TRANSLATION_SPEED = 2.9;

    public static final double MAX_ROTATION_SPEED = Units.degreesToRadians(180);


    private SwerveSimWrapper m_Simulator;

    private static final Translation2d m_frontLeftLocation = new Translation2d(0.381, 0.381);
    private static final Translation2d m_frontRightLocation = new Translation2d(0.381, -0.381);
    private static final Translation2d m_backLeftLocation = new Translation2d(-0.381, 0.381);
    private static final Translation2d m_backRightLocation = new Translation2d(-0.381, -0.381);

    private final SwerveDriveModules m_frontLeft;
    private final SwerveDriveModules m_backLeft;
    private final SwerveDriveModules m_frontRight;
    private final SwerveDriveModules m_backRight;

    private final SwerveDriveModules[] m_modules;

    private final WPI_Pigeon2 m_gyro;

    // Creating my kinematics object using the module locations
    private static final SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(
        m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation
    );

    public SwerveDriveChassisSubsystem() {

        m_frontLeft = new SwerveDriveModules(Constants.FRONT_LEFT_WHEEL, Constants.FRONT_LEFT_AZIMUTH, "FL");
        m_frontRight = new SwerveDriveModules(Constants.FRONT_RIGHT_WHEEL, Constants.FRONT_RIGHT_AZIMUTH, "FR");
        m_backLeft = new SwerveDriveModules(Constants.BACK_LEFT_WHEEL, Constants.BACK_LEFT_AZIMUTH, "BL");
        m_backRight = new SwerveDriveModules(Constants.BACK_RIGHT_WHEEL, Constants.BACK_RIGHT_AZIMUTH, "BR");
        m_modules = new SwerveDriveModules[]{m_frontLeft, m_frontRight, m_backLeft, m_backRight};
        m_gyro = new WPI_Pigeon2(Constants.PIGEON_PORT);
        SwerveDriveOdometry m_odometry = new SwerveDriveOdometry(
            m_kinematics, m_gyro.getRotation2d(),
            new SwerveModulePosition[] {
                m_frontLeft.getModulePosition(),
                m_frontRight.getModulePosition(),
                m_backLeft.getModulePosition(),
                m_backRight.getModulePosition()
            }, new Pose2d(5.0, 13.5, new Rotation2d()));

        if (RobotBase.isSimulation())
        {
            List<SwerveModuleSimWrapper> moduleSims = List.of(
                m_frontLeft.getSimWrapper(),
                m_frontRight.getSimWrapper(),
                m_backLeft.getSimWrapper(),
                m_backRight.getSimWrapper());
            m_Simulator = new SwerveSimWrapper(WHEEL_BASE, TRACK_WIDTH, 64.0, 1.0, moduleSims, new CtrePigeonImuWrapper(m_gyro));
        }
    }

    public void simulationPeriodic() {
        m_Simulator.update();
    }

    public void setSpeeds(ChassisSpeeds speedsInp) {
        SwerveModuleState[] moduleStates = m_kinematics.toSwerveModuleStates(speedsInp);
        for (int i = 0; i < 4; i++) {
            m_modules[i].setState(moduleStates[i]);
        }
    }

    public void setModuleState(int moduleId, double degrees, double velocity){
        m_modules[moduleId].setState(new SwerveModuleState(velocity, Rotation2d.fromDegrees(degrees)));
    }

    public CommandBase commandSetModuleState(int moduleId, double degrees, double velocity) {
        return this.run(() -> setModuleState(moduleId, degrees, velocity)).withName("Set Module State" + moduleId);
    }

    public CommandBase commandSetChassisSpeed(ChassisSpeeds chassisSp) {
        return this.run(() -> setSpeeds(chassisSp)).withName("Set Chassis Speeds");
    }

    }

