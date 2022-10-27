package com.gos.preseason2023.subsystems;

import com.gos.preseason2023.Constants;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ChassisSubsystem extends SubsystemBase {

    private final SwerveModule m_swerveFrontLeft;
    private final SwerveModule m_swerveFrontRight;
    private final SwerveModule m_swerveBackLeft;
    private final SwerveModule m_swerveBackRight;

    // Locations for the swerve drive modules relative to the robot center.
    //TODO: fake numbers lol get our actual swerve numbers
    Translation2d m_frontLeftLocation = new Translation2d(0.381, 0.381);
    Translation2d m_frontRightLocation = new Translation2d(0.381, -0.381);
    Translation2d m_backLeftLocation = new Translation2d(-0.381, 0.381);
    Translation2d m_backRightLocation = new Translation2d(-0.381, -0.381);

    // Creating my kinematics object using the module locations
    SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(
        m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation
    );



    public ChassisSubsystem() {
        m_swerveFrontLeft = new SwerveModule(Constants.SWERVE_FRONT_LEFT_SPIN_ID, Constants.SWERVE_FRONT_LEFT_POWER_ID);
        m_swerveFrontRight = new SwerveModule(Constants.SWERVE_FRONT_RIGHT_SPIN_ID, Constants.SWERVE_FRONT_RIGHT_POWER_ID);
        m_swerveBackLeft = new SwerveModule(Constants.SWERVE_BACK_LEFT_SPIN_ID, Constants.SWERVE_BACK_LEFT_POWER_ID);
        m_swerveBackRight = new SwerveModule(Constants.SWERVE_BACK_RIGHT_SPIN_ID, Constants.SWERVE_BACK_RIGHT_POWER_ID);
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

}
