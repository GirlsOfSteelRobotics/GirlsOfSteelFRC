// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.scra.mepi.rapid_react.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.scra.mepi.rapid_react.Constants;
import com.scra.mepi.rapid_react.ShooterLookupTable;
import com.scra.mepi.rapid_react.TunableNumber;

public class ShooterSubsytem extends SubsystemBase {
    /** Creates a new Shooter. */
    private final CANSparkMax m_shooterMotor;

    private final CANSparkMax m_hoodMotor;

    private final ShooterLookupTable m_shooterLookupTable;
    private final RelativeEncoder m_encoder;
    private final SparkMaxPIDController m_PIDController;
    private final TunableNumber m_tunableNumberkP = new TunableNumber("Shooter(kP)", 0);
    private final TunableNumber m_tunableNumberkD = new TunableNumber("Shooter(kD)", 0);
    private final TunableNumber m_tunableNumberkFF = new TunableNumber("Shooter(kFF)", 0.0004);
    private final TunableNumber m_tunableNumberkI = new TunableNumber("Shooter(kI)", 0);
    private final TunableNumber m_tunableAllowableError =
            new TunableNumber("Shooter(AllowableError))", 50);
    private final double m_afterEncoderReduction = 0.5;
    public static final double FENDER_RPM = 1000;

    public ShooterSubsytem() {
        m_shooterMotor = new CANSparkMax(Constants.SHOOTER_SPARK, MotorType.kBrushless);
        m_hoodMotor = new CANSparkMax(Constants.SHOOTER_HOOD_SPARK, MotorType.kBrushless);
        m_shooterLookupTable = new ShooterLookupTable();
        m_encoder = m_shooterMotor.getEncoder();
        m_PIDController = m_shooterMotor.getPIDController();
        m_shooterMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_shooterMotor.restoreFactoryDefaults();
        m_shooterMotor.setInverted(true);
        m_shooterMotor.burnFlash();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        if (m_tunableNumberkP.hasChanged()
                || m_tunableNumberkI.hasChanged()
                || m_tunableNumberkD.hasChanged()
                || m_tunableNumberkFF.hasChanged()
                || m_tunableAllowableError.hasChanged()) {
            configurePID();
        }
        SmartDashboard.putNumber("shooterRpm", getRPM());
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    public void setPidRpm(double rpm) {
        m_PIDController.setReference(rpm, CANSparkMax.ControlType.kVelocity);
    }

    public void configurePID() {
        m_PIDController.setP(m_tunableNumberkP.get());
        m_PIDController.setD(m_tunableNumberkD.get());
        m_PIDController.setFF(m_tunableNumberkFF.get());
        m_PIDController.setI(m_tunableNumberkI.get());
    }

    public boolean checkAtSpeed(double goal) {
        double error = Math.abs(goal - getRPM());
        return m_tunableAllowableError.get() > error;
    }

    public double getRPM() {
        return m_encoder.getVelocity() * m_afterEncoderReduction;
    }

    public void shootFromDistance(double distance) {
        setPidRpm(m_shooterLookupTable.getRpmTable(distance));
    }

    public void setShooterSpeed(double speed) {
        m_shooterMotor.set(speed);
    }
}
