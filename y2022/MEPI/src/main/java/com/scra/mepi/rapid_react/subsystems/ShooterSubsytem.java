// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.scra.mepi.rapid_react.subsystems;

import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.rev.properties.pid.RevPidPropertyBuilder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import com.scra.mepi.rapid_react.Constants;
import com.scra.mepi.rapid_react.ShooterLookupTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.FlywheelSimWrapper;
import org.snobotv2.sim_wrappers.ISimWrapper;
import org.snobotv2.sim_wrappers.InstantaneousMotorSim;

public class ShooterSubsytem extends SubsystemBase {
    private static final double AFTER_ENCODER_REDUCTION = 0.5;

    public static final double FENDER_RPM = 1500;

    /**
     * Creates a new Shooter.
     */
    private final SimableCANSparkMax m_shooterMotor;

    private final SimableCANSparkMax m_hoodMotor;

    private final ShooterLookupTable m_shooterLookupTable;
    private final RelativeEncoder m_encoder;
    private final SparkMaxPIDController m_pidController;
    private final PidProperty m_pidProperties;
    private final GosDoubleProperty m_tunableAllowableError = new GosDoubleProperty(false, "Shooter(AllowableError))", 50);

    // Simulation
    private ISimWrapper m_shooterSimulator;
    private ISimWrapper m_hoodSimulator;

    public ShooterSubsytem() {
        m_shooterMotor = new SimableCANSparkMax(Constants.SHOOTER_SPARK, MotorType.kBrushless);
        m_hoodMotor = new SimableCANSparkMax(Constants.SHOOTER_HOOD_SPARK, MotorType.kBrushless);
        m_shooterMotor.setSmartCurrentLimit(50);
        m_hoodMotor.setSmartCurrentLimit(30);
        m_shooterLookupTable = new ShooterLookupTable();
        m_encoder = m_shooterMotor.getEncoder();
        m_pidController = m_shooterMotor.getPIDController();
        m_pidProperties = new RevPidPropertyBuilder("Shooter", false, m_pidController, 0)
            .addP(0)
            .addI(0)
            .addD(0)
            .addFF(0.00045)
            .build();
        m_shooterMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_shooterMotor.restoreFactoryDefaults();
        m_shooterMotor.setInverted(true);
        m_shooterMotor.burnFlash();

        if (RobotBase.isSimulation()) {
            FlywheelSim shooterFlywheelSim = new FlywheelSim(DCMotor.getNeo550(2), 1, 0.01);
            m_shooterSimulator = new FlywheelSimWrapper(shooterFlywheelSim,
                new RevMotorControllerSimWrapper(m_shooterMotor),
                RevEncoderSimWrapper.create(m_shooterMotor));

            m_hoodSimulator = new InstantaneousMotorSim(
                new RevMotorControllerSimWrapper(m_hoodMotor),
                RevEncoderSimWrapper.create(m_hoodMotor),
                1
            );
        }
    }

    @Override
    public void periodic() {
        m_pidProperties.updateIfChanged();
        SmartDashboard.putNumber("shooterRpm", getRPM());
    }

    @Override
    public void simulationPeriodic() {
        m_hoodSimulator.update();
        m_shooterSimulator.update();
    }

    public void setPidRpm(double rpm) {
        m_pidController.setReference(rpm, CANSparkMax.ControlType.kVelocity);
    }

    public boolean checkAtSpeed(double goal) {
        double error = Math.abs(goal - getRPM());
        return m_tunableAllowableError.getValue() > error;
    }

    public double getRPM() {
        return m_encoder.getVelocity() * AFTER_ENCODER_REDUCTION;
    }

    public void shootFromDistance(double distance) {
        setPidRpm(m_shooterLookupTable.getRpmTable(distance));
    }

    public void setShooterSpeed(double speed) {
        m_shooterMotor.set(speed);
    }
}
