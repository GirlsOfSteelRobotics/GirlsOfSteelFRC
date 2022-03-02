package com.gos.steam_works.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.steam_works.RobotMap;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public final class Shooter extends SubsystemBase {
    private final WPI_TalonSRX m_lowShooterMotor;
    private final WPI_TalonSRX m_highShooterMotor;

    /*
     * private static final double shooterMinSpeed = -0.5; private static final
     * double shooterMaxSpeed = -1.0; private static final double
     * shooterDefaultSpeed = shooterMaxSpeed; private static final double
     * shooterSpeedStep = -0.05; //percentage up/down per press private  static
     * final double shooterDefaultSpeedGear = -1.0 - (5.5 * -0.05); private
     * static final double shooterDefaultSpeedKey = -1.0 - (8.0 * -0.05);
     * private double shooterSpeed = shooterDefaultSpeed; //starting speed
     */
    // remember to add when released to reset current shooter increment

    // Speed mode constants //TODO: test and change
    private static final int LOW_MAX_RPM = 3400;
    private static final int HIGH_MAX_RPM = 5000;
    private static final int SHOOTER_MIN_SPEED = 1000;
    private static final int SHOOTER_MAX_SPEED = HIGH_MAX_RPM;
    private static final int SHOOTER_SPEED_STEP = 100;
    public static final int SHOOTER_DEFAULT_SPEED = SHOOTER_MAX_SPEED;
    public static final int SHOOTER_SPEED_GEAR = 3700;
    public static final int SHOOTER_SPEED_KEY = 3250;
    public static final int AUTO_SHOOTER_SPEED_KEY = 3333;
    private static final double MAX_SHOOTER_ERROR = 0.05;

    private int m_shooterSpeed = SHOOTER_DEFAULT_SPEED;
    private boolean m_lowMotorRunning;

    public Shooter() {
        m_lowShooterMotor = new WPI_TalonSRX(RobotMap.LOW_SHOOTER_MOTOR);
        m_highShooterMotor = new WPI_TalonSRX(RobotMap.HIGH_SHOOTER_MOTOR);

        m_lowShooterMotor.setNeutralMode(NeutralMode.Brake);
        m_highShooterMotor.setNeutralMode(NeutralMode.Brake);

        setupEncoder(m_lowShooterMotor);
        setupEncoder(m_highShooterMotor);

        // addChild("low", lowShooterMotor);
        // addChild("high", highShooterMotor);

        // PID Values
        m_lowShooterMotor.config_kF(0, 0.04407, 0); //see p 17 of motion profile manual
        m_lowShooterMotor.config_kP(0, 0.01, 0);
        m_lowShooterMotor.config_kI(0, 0, 0);
        m_lowShooterMotor.config_kD(0, 0, 0);

        // PID Values
        m_highShooterMotor.config_kF(0, 0.02997, 0); //see p 17 of motion profile manual
        m_highShooterMotor.config_kP(0, 0.01, 0);
        m_highShooterMotor.config_kI(0, 0, 0);
        m_highShooterMotor.config_kD(0, 0, 0);


        // addChild("lowShooterMotor", lowShooterMotor);
        // addChild("highShooterMotor", highShooterMotor);
    }

    public void runHighShooterMotor() {
        m_highShooterMotor.set(ControlMode.Velocity, m_shooterSpeed);
    }

    public void runLowShooterMotor() {
        if (m_lowMotorRunning) {
            m_lowShooterMotor.set(ControlMode.Velocity, m_shooterSpeed * ((double) LOW_MAX_RPM / (double) HIGH_MAX_RPM));
        }
    }

    public void startLowShooterMotor() {
        m_lowMotorRunning = true;
    }

    public void stopLowShooterMotor() {
        m_lowMotorRunning = false;
    }

    public boolean isHighShooterAtSpeed() { // TODO: This is broken, always
        // returning true
        return (m_highShooterMotor.getClosedLoopError(0) / (double) m_shooterSpeed) < MAX_SHOOTER_ERROR;
    }

    public void stopShooterMotors() {
        m_lowShooterMotor.set(ControlMode.Velocity, 0);
        m_highShooterMotor.set(ControlMode.Velocity, 0);
    }



    public void incrementHighShooterSpeed() {
        if ((m_shooterSpeed + SHOOTER_SPEED_STEP) <= SHOOTER_MAX_SPEED) {
            m_shooterSpeed += SHOOTER_SPEED_STEP;
        }
        System.out.println("currentShooterSpeed: " + m_shooterSpeed);
    }

    public void decrementHighShooterSpeed() {
        if ((m_shooterSpeed - SHOOTER_SPEED_STEP) >= SHOOTER_MIN_SPEED) {
            m_shooterSpeed -= SHOOTER_SPEED_STEP;
        }
        System.out.println("currentShooterSpeed: " + m_shooterSpeed);
    }

    public void setShooterSpeed(int speed) {
        m_shooterSpeed = speed;
        System.out.println("currentShooterSpeed has reset to: " + m_shooterSpeed);
    }

    public void setupEncoder(WPI_TalonSRX talon) { // call on both talons
        // Set Encoder Types
        talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        talon.setSensorPhase(false);
    }

    public double getHighShooterSpeed() {
        return m_highShooterMotor.getSelectedSensorVelocity(0);
    }

    public double getLowShooterSpeed() {
        return m_lowShooterMotor.getSelectedSensorVelocity(0);
    }

    public boolean isLowShooterMotorRunning() {
        return m_lowMotorRunning;
    }

}
