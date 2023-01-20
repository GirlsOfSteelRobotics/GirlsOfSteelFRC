package com.gos.chargedup.subsystems;

import com.ctre.phoenix.sensors.WPI_PigeonIMU;
import com.gos.chargedup.Constants;
import com.gos.lib.properties.GosDoubleProperty;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ChassisSubsystem extends SubsystemBase {
    public static final double PITCH_LOWER_LIMIT = -5.0;
    public static final double PITCH_UPPER_LIMIT = 5.0;
    public static final GosDoubleProperty AUTO_ENGAGE_SPEED = new GosDoubleProperty(false, "Chassis speed for auto engage", 0.1);
    private final SimableCANSparkMax m_leaderLeft;
    private final SimableCANSparkMax m_followerLeft;
    private final SimableCANSparkMax m_leaderRight;
    private final SimableCANSparkMax m_followerRight;

    private final DifferentialDrive m_drive;

    private final DifferentialDriveOdometry m_odometry; // NOPMD
    private final WPI_PigeonIMU m_gyro; //NOPMD



    public ChassisSubsystem() {
        m_leaderLeft = new SimableCANSparkMax(Constants.DRIVE_LEFT_LEADER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_followerLeft = new SimableCANSparkMax(Constants.DRIVE_LEFT_FOLLOWER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_leaderRight = new SimableCANSparkMax(Constants.DRIVE_RIGHT_LEADER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_followerRight = new SimableCANSparkMax(Constants.DRIVE_RIGHT_FOLLOWER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);

        m_leaderLeft.restoreFactoryDefaults();
        m_followerLeft.restoreFactoryDefaults();
        m_leaderRight.restoreFactoryDefaults();
        m_followerRight.restoreFactoryDefaults();

        m_leaderLeft.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_followerLeft.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_leaderRight.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_followerRight.setIdleMode(CANSparkMax.IdleMode.kCoast);

        m_leaderLeft.setInverted(false);
        m_leaderRight.setInverted(true);

        m_followerLeft.follow(m_leaderLeft, false);
        m_followerRight.follow(m_leaderRight, false);

        m_drive = new DifferentialDrive(m_leaderLeft, m_leaderRight);

        m_gyro = new WPI_PigeonIMU(Constants.PIGEON_PORT);
        m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(0), 0, 0);
    }

    public void setArcadeDrive(double speed, double steer) {
        m_drive.arcadeDrive(speed, steer);
    }

    public void setCurvatureDrive(double speed, double steer) {
        m_drive.curvatureDrive(speed, steer, speed < 0.05);

    }

    public double getPitch () {
        System.out.println(m_gyro.getPitch());
        return m_gyro.getPitch();
    }
    public void autoEngage () {
        if (getPitch() > PITCH_LOWER_LIMIT && getPitch() < PITCH_UPPER_LIMIT) {
            setArcadeDrive(0, 0);
            System.out.println("pj is amazing");

        } else if (getPitch() > 0) {
            setArcadeDrive(-AUTO_ENGAGE_SPEED.getValue(), 0);
            System.out.println("grace is amazing");
        } else if (getPitch() < 0) {
            setArcadeDrive(AUTO_ENGAGE_SPEED.getValue(), 0);
            System.out.println(("sienna is amazing"));
        }
    }


    public Command createAutoEngageCommand() {
        return this.run(this::autoEngage);
    }
}
