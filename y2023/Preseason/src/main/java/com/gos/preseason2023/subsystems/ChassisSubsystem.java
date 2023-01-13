package com.gos.preseason2023.subsystems;

import com.gos.preseason2023.Constants;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.wpi.ADXRS450GyroWrapper;
import org.snobotv2.sim_wrappers.SwerveModuleSimWrapper;
import org.snobotv2.sim_wrappers.SwerveSimWrapper;

import java.util.List;

public class ChassisSubsystem extends SubsystemBase {


    private final SwerveModule m_swerveFrontLeft;
    private final SwerveModule m_swerveFrontRight;
    private final SwerveModule m_swerveBackLeft;
    private final SwerveModule m_swerveBackRight;

    private static final double kWheelBase = Units.inchesToMeters(20.733);
    private static final double kTrackWidth = Units.inchesToMeters(20.733);

    private SwerveSimWrapper m_simWrapper;

    // Locations for the swerve drive modules relative to the robot center.
    //TODO: fake numbers lol get our actual swerve numbers
    private final Translation2d m_frontLeftLocation = new Translation2d(0.381, 0.381);
    private final Translation2d m_frontRightLocation = new Translation2d(0.381, -0.381);
    private final Translation2d m_backLeftLocation = new Translation2d(-0.381, 0.381);
    private final Translation2d m_backRightLocation = new Translation2d(-0.381, -0.381);

    private final ADXRS450_Gyro m_gyro = new ADXRS450_Gyro();

    private final Field2d m_field;

    // Creating my kinematics object using the module locations
    public ChassisSubsystem() {
        m_field = new Field2d();
        SmartDashboard.putData(m_field);
        m_swerveFrontLeft = new SwerveModule(Constants.SWERVE_FRONT_LEFT_SPIN_ID, Constants.SWERVE_FRONT_LEFT_POWER_ID);
        m_swerveFrontRight = new SwerveModule(Constants.SWERVE_FRONT_RIGHT_SPIN_ID, Constants.SWERVE_FRONT_RIGHT_POWER_ID);
        m_swerveBackLeft = new SwerveModule(Constants.SWERVE_BACK_LEFT_SPIN_ID, Constants.SWERVE_BACK_LEFT_POWER_ID);
        m_swerveBackRight = new SwerveModule(Constants.SWERVE_BACK_RIGHT_SPIN_ID, Constants.SWERVE_BACK_RIGHT_POWER_ID);

        if (RobotBase.isSimulation()) {
            List<SwerveModuleSimWrapper> moduleSims = List.of(
                m_swerveFrontLeft.getSimWrapper(),
                m_swerveFrontRight.getSimWrapper(),
                m_swerveBackLeft.getSimWrapper(),
                m_swerveBackRight.getSimWrapper());
            m_simWrapper = new SwerveSimWrapper(kWheelBase, kTrackWidth, 64.0, 1.0, moduleSims, new ADXRS450GyroWrapper(m_gyro));
        }
    }


    private final SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(
        m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation
    );

    SwerveDriveOdometry m_odometry = new SwerveDriveOdometry(m_kinematics,
        getGyroHeading(), new Pose2d(5, 6, new Rotation2d()));

    private Rotation2d getGyroHeading() {
        return m_gyro.getRotation2d();
    }

    @Override
    public void periodic() {
        // Get my gyro angle. We are negating the value because gyros return positive
        // values as the robot turns clockwise. This is not standard convention that is
        // used by the WPILib classes.
        var gyroAngle = Rotation2d.fromDegrees(-m_gyro.getAngle());
        m_field.setRobotPose(getPose());

        // Update the pose
        m_odometry.update(gyroAngle, m_swerveFrontLeft.getState(), m_swerveFrontRight.getState(),
            m_swerveBackLeft.getState(), m_swerveBackRight.getState());
    }

    @Override
    public void simulationPeriodic() {
        m_simWrapper.update();
    }

    public void setJoystickDrive(double xAxis, double yAxis, double steer) {
        ChassisSpeeds speeds = new ChassisSpeeds(xAxis, yAxis, steer);
        // Convert to module states
        SwerveModuleState[] moduleStates = m_kinematics.toSwerveModuleStates(speeds);

        // Front left module state
        SwerveModuleState frontLeft = moduleStates[0];

        // Front right module state
        SwerveModuleState frontRight = moduleStates[1];

        // Back left module state
        SwerveModuleState backLeft = moduleStates[2];

        // Back right module state
        SwerveModuleState backRight = moduleStates[3];

        m_swerveFrontLeft.goToState(frontLeft);
        m_swerveFrontRight.goToState(frontRight);
        m_swerveBackLeft.goToState(backLeft);
        m_swerveBackRight.goToState(backRight);
    }

    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }


}
