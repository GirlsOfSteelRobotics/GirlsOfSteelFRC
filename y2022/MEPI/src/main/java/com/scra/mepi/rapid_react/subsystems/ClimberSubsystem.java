// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.scra.mepi.rapid_react.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.scra.mepi.rapid_react.Constants;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberSubsystem extends SubsystemBase {
    private final CANSparkMax m_leftClimber = new CANSparkMax(Constants.CLIMBER_LEFT, MotorType.kBrushless);
    private final CANSparkMax m_rightClimber =
        new CANSparkMax(Constants.CLIMBER_RIGHT, MotorType.kBrushless);
    private final DigitalInput m_leftLimitSwitch = new DigitalInput(Constants.LEFT_LIMIT_SWITCH);
    private final DigitalInput m_rightLimitSwitch = new DigitalInput(Constants.RIGHT_LIMIT_SWITCH);
    private final RelativeEncoder m_leftEncoder = m_leftClimber.getEncoder();
    private final RelativeEncoder m_rightEncoder = m_rightClimber.getEncoder();
    private final ProfiledPIDController m_climberPID = new ProfiledPIDController(
        3.5, 0.0, 0.0, new Constraints(20.0, 150.0), 0.02);

    /**
     * Creates a new ClimberSubsystem.
     */
    public ClimberSubsystem() {
        m_leftClimber.restoreFactoryDefaults();
        m_rightClimber.restoreFactoryDefaults();
        m_leftClimber.setInverted(true);
        m_rightClimber.setInverted(false);
        m_leftClimber.burnFlash();
        m_rightClimber.burnFlash();

        m_rightClimber.follow(m_leftClimber, true);
    }

    public void runClimberPID(double goal) {
        m_climberPID.setGoal(goal);
        set(m_climberPID.calculate(m_leftEncoder.getPosition()));
    }


    @SuppressWarnings("PMD.AvoidReassigningParameters")
    public void set(double speed) {
        if (speed > 0 && (leftLimitSwitchPress() || rightLimitSwitchPress())) {
            speed = 0;
        }
        m_leftClimber.set(speed);
    }

    public boolean leftLimitSwitchPress() {
        return !m_leftLimitSwitch.get();
    }

    public boolean rightLimitSwitchPress() {
        return !m_rightLimitSwitch.get();
    }

    public double getPIDPosition() {
        return m_leftEncoder.getPosition();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        if (leftLimitSwitchPress()) {
            m_leftEncoder.setPosition(0);
        }
        if (rightLimitSwitchPress()) {
            m_rightEncoder.setPosition(0);
        }

        SmartDashboard.putBoolean("climber limit switch pressed", leftLimitSwitchPress());
        SmartDashboard.putNumber("climb left encoder", m_leftEncoder.getPosition());
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
