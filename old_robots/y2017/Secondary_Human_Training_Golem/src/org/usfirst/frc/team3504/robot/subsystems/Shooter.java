package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.RobotMap;

public class Shooter extends Subsystem {

    /*
     * private static final double shooterMinSpeed = -0.5; private static final
     * double shooterMaxSpeed = -1.0; private static final double
     * shooterDefaultSpeed = shooterMaxSpeed; private static final double
     * shooterSpeedStep = -0.05; //percentage up/down per press pridvate  static
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

    private final WPI_TalonSRX m_lowShooterMotor;
    private final WPI_TalonSRX m_highShooterMotor;
    private int m_shooterSpeed = SHOOTER_DEFAULT_SPEED;
    private boolean m_lowMotorRunning;

    public Shooter() {
        m_lowShooterMotor = new WPI_TalonSRX(RobotMap.LOW_SHOOTER_MOTOR);
        m_highShooterMotor = new WPI_TalonSRX(RobotMap.HIGH_SHOOTER_MOTOR);

        m_lowShooterMotor.setNeutralMode(NeutralMode.Coast);
        m_highShooterMotor.setNeutralMode(NeutralMode.Coast);

        setupEncoder(m_lowShooterMotor);
        setupEncoder(m_highShooterMotor);

        addChild("low", m_lowShooterMotor);
        addChild("high", m_highShooterMotor);

        // PID Values
        m_lowShooterMotor.config_kF(0, 0.04407); // see p 17 of motion profile manual
                                        // 0.04407
        // lowShooterMotor.setF(0); //see p 17 of motion profile manual
        m_lowShooterMotor.config_kP(0, 0.01);
        m_lowShooterMotor.config_kI(0, 0.0);
        m_lowShooterMotor.config_kD(0, 0.0);

        // PID Values
        m_highShooterMotor.config_kF(0, 0.02997); // see p 17 of motion profile manual
                                        // 0.02997
        // highShooterMotor.setF(0);
        m_highShooterMotor.config_kP(0, 0.01);
        m_highShooterMotor.config_kI(0, 0.0);
        m_highShooterMotor.config_kD(0, 0.0);

        addChild("lowShooterMotor", m_lowShooterMotor);
        addChild("highShooterMotor", m_highShooterMotor);
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
        return (m_highShooterMotor.getClosedLoopError() / (double) m_shooterSpeed) < MAX_SHOOTER_ERROR;
    }

    public void stopShooterMotors() {
        m_lowShooterMotor.set(ControlMode.PercentOutput, 0);
        m_highShooterMotor.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
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

    public final void setupEncoder(WPI_TalonSRX talon) { // call on both talons
        // Set Encoder Types
        talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        talon.setSensorPhase(true);
    }

    public double getHighShooterSpeed() {
        return m_highShooterMotor.getSelectedSensorVelocity();
    }

    public double getLowShooterSpeed() {
        return m_lowShooterMotor.getSelectedSensorVelocity();
    }

    public boolean isLowShooterMotorRunning() {
        return m_lowMotorRunning;
    }

}
