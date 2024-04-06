package com.gos.crescendo2024.subsystems;

import com.gos.crescendo2024.Constants;
import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.rev.SparkMaxUtil;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.lib.rev.properties.pid.RevPidPropertyBuilder;
import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HangerSubsystem extends SubsystemBase {
    private static final GosDoubleProperty HANGER_DOWN_SPEED = new GosDoubleProperty(false, "Hanger_Down_Speed", -0.3);
    private static final GosDoubleProperty HANGER_UP_SPEED = new GosDoubleProperty(false, "Hanger_Up_Speed", 0.3);

    private static final GosDoubleProperty WALL_HANGER_DOWN_SPEED = new GosDoubleProperty(false, "Wall_Hanger_Down_Speed", -1);
    private static final GosDoubleProperty WALL_HANGER_UP_SPEED = new GosDoubleProperty(false, "Wall_Hanger_Up_Speed", 1);

    private static final GosDoubleProperty HANGER_UP_GOAL_POSITION = new GosDoubleProperty(false, "Hanger Up Goal Position", 118);

    private final SimableCANSparkMax m_leftHangerMotor;
    private final RelativeEncoder m_leftHangerEncoder;
    private final SparkMaxAlerts m_leftHangerAlert;
    private final SparkPIDController m_leftPidController;
    private final PidProperty m_leftPidProperties;

    private final SimableCANSparkMax m_rightHangerMotor;
    private final RelativeEncoder m_rightHangerEncoder;
    private final SparkMaxAlerts m_rightHangerAlert;
    private final SparkPIDController m_rightPidController;
    private final PidProperty m_rightPidProperties;

    private final LoggingUtil m_networkTableEntries;
    private final DigitalInput m_upperLimitSwitchLeft;
    private final DigitalInput m_lowerLimitSwitchLeft;

    private final DigitalInput m_upperLimitSwitchRight;
    private final DigitalInput m_lowerLimitSwitchRight;

    //TODO add limit switches

    public HangerSubsystem() {
        m_leftHangerMotor = new SimableCANSparkMax(Constants.HANGER_LEFT_MOTOR, CANSparkLowLevel.MotorType.kBrushless);
        //m_leftHangerMotor.restoreFactoryDefaults();
        m_leftHangerMotor.setInverted(true);
        m_leftHangerEncoder = m_leftHangerMotor.getEncoder();
        m_leftHangerMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_leftHangerMotor.setSmartCurrentLimit(60);
        m_leftPidController = m_leftHangerMotor.getPIDController();
        m_leftPidProperties = createPidProperties(m_leftPidController);
        m_leftHangerMotor.burnFlash();
        m_leftHangerAlert = new SparkMaxAlerts(m_leftHangerMotor, "hanger l");

        m_rightHangerMotor = new SimableCANSparkMax(Constants.HANGER_RIGHT_MOTOR, CANSparkLowLevel.MotorType.kBrushless);
        //m_rightHangerMotor.restoreFactoryDefaults();
        m_rightHangerMotor.setInverted(false);
        m_rightHangerEncoder = m_rightHangerMotor.getEncoder();
        m_rightHangerMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_rightHangerMotor.setSmartCurrentLimit(60);
        m_rightPidController = m_rightHangerMotor.getPIDController();
        m_rightPidProperties = createPidProperties(m_rightPidController);
        m_rightHangerMotor.burnFlash();
        m_rightHangerAlert = new SparkMaxAlerts(m_rightHangerMotor, "hanger r");


        m_upperLimitSwitchLeft = new DigitalInput(Constants.HANGER_UPPER_LIMIT_SWITCH_LEFT);
        m_lowerLimitSwitchLeft = new DigitalInput(Constants.HANGER_LOWER_LIMIT_SWITCH_LEFT);
        m_upperLimitSwitchRight = new DigitalInput(Constants.HANGER_UPPER_LIMIT_SWITCH_RIGHT);
        m_lowerLimitSwitchRight = new DigitalInput(Constants.HANGER_LOWER_LIMIT_SWITCH_RIGHT);

        m_networkTableEntries = new LoggingUtil("Hanger Subsystem");
        m_networkTableEntries.addDouble("Left Output: ", m_leftHangerMotor::getAppliedOutput);
        m_networkTableEntries.addDouble("Right Output", m_rightHangerMotor::getAppliedOutput);
        m_networkTableEntries.addDouble("Left Pos: ", m_leftHangerEncoder::getPosition);
        m_networkTableEntries.addDouble("Right Pos", m_rightHangerEncoder::getPosition);

        resetEncoders();

    }

    private PidProperty createPidProperties(SparkPIDController pidController) {
        return new RevPidPropertyBuilder("HangerPid", false, pidController, 0)
            .addP(0.1)
            .build();
    }

    public void moveHangerToPosition(double position) {
        m_leftPidController.setReference(position, CANSparkBase.ControlType.kPosition);
        m_rightPidController.setReference(position, CANSparkBase.ControlType.kPosition);
    }


    @Override
    public void periodic() {
        m_leftHangerAlert.checkAlerts();
        m_rightHangerAlert.checkAlerts();
        m_networkTableEntries.updateLogs();

        m_leftPidProperties.updateIfChanged();
        m_rightPidProperties.updateIfChanged();
    }

    public void clearStickyFaults() {
        m_leftHangerMotor.clearFaults();
        m_rightHangerMotor.clearFaults();
    }

    private void setLeftMotor(double speed) {
        m_leftHangerMotor.set(speed);
    }

    private void setRightMotor(double speed) {
        m_rightHangerMotor.set(speed);
    }

    public void runHangerUp() {
        runLeftHangerUp(HANGER_UP_SPEED.getValue());
        runRightHangerUp(HANGER_UP_SPEED.getValue());
    }

    public void runLeftHangerUp(double hangerUpSpeed) {
        if (isLeftUpperLimitSwitchedPressed()) {
            m_leftHangerMotor.set(0);
        } else {
            setLeftMotor(hangerUpSpeed);
        }
    }

    public void runRightHangerUp(double hangerUpSpeed) {
        if (isRightUpperLimitSwitchedPressed()) {
            m_rightHangerMotor.set(0);
        } else {
            setRightMotor(hangerUpSpeed);
        }
    }

    public void runHangerDown() {
        runLeftHangerDown(HANGER_DOWN_SPEED.getValue());
        runRightHangerDown(HANGER_DOWN_SPEED.getValue());
    }

    public void runLeftHangerDown(double hangerDownSpeed) {
        if (isLeftLowerLimitSwitchedPressed()) {
            m_leftHangerMotor.set(0);
        } else {
            setLeftMotor(hangerDownSpeed);
        }
    }

    public void runRightHangerDown(double hangerDownSpeed) {
        if (isRightLowerLimitSwitchedPressed()) {
            m_rightHangerMotor.set(0);
        } else {
            setRightMotor(hangerDownSpeed);
        }
    }

    public void stopHanger() {
        m_leftHangerMotor.set(0);
        m_rightHangerMotor.set(0);
    }

    private void resetEncoders() {
        SparkMaxUtil.autoRetry(() -> m_leftHangerEncoder.setPosition(0));
        SparkMaxUtil.autoRetry(() -> m_rightHangerEncoder.setPosition(0));
    }

    public boolean isRightUpperLimitSwitchedPressed() {
        return !m_upperLimitSwitchRight.get();
    }

    public boolean isRightLowerLimitSwitchedPressed() {
        return !m_lowerLimitSwitchRight.get();
    }

    public boolean isLeftLowerLimitSwitchedPressed() {
        return !m_lowerLimitSwitchLeft.get();
    }

    public boolean isLeftUpperLimitSwitchedPressed() {
        return !m_upperLimitSwitchLeft.get();
    }

    /////////////////////////////////////
    // Command Factories
    /////////////////////////////////////
    public Command createHangerUp() {
        return this.runEnd(this::runHangerUp, this::stopHanger).withName("Hanger Up");
    }

    public Command createHangerDown() {
        return this.runEnd(this::runHangerDown, this::stopHanger).withName("Hanger Down");
    }

    public Command createLeftHangerUp() {
        return runEnd(() -> runLeftHangerUp(HANGER_UP_SPEED.getValue()), this::stopHanger).withName("Left Hanger Up");
    }

    public Command createLeftHangerDown() {
        return runEnd(() -> runLeftHangerDown(HANGER_DOWN_SPEED.getValue()), this::stopHanger).withName("Left Hanger Down");
    }

    public Command createRightHangerUp() {
        return runEnd(() -> runRightHangerUp(HANGER_UP_SPEED.getValue()), this::stopHanger).withName("Right Hanger Up");
    }

    public Command createRightHangerDown() {
        return runEnd(() -> runRightHangerDown(HANGER_DOWN_SPEED.getValue()), this::stopHanger).withName("Right Hanger Down");
    }

    public Command createSetHangerToCoast() {
        return this.runEnd(
                () -> {
                    m_leftHangerMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
                    m_rightHangerMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
                },
                () -> {
                    m_leftHangerMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
                    m_leftHangerMotor.setIdleMode(CANSparkBase.IdleMode.kBrake);
                })
            .ignoringDisable(true).withName("Hangers to Coast");
    }

    public Command createAutoUpCommand() {
        return runEnd(() -> moveHangerToPosition(HANGER_UP_GOAL_POSITION.getValue()), this::stopHanger)
            .withName("AutoHangerUp");
    }

    public Command createAutoDownCommand() {
        return runEnd(() -> moveHangerToPosition(0), this::stopHanger)
            .withName("AutoHangerDown");
    }

    public Command createResetEncoders() {
        return run(this::resetEncoders)
            .ignoringDisable(true)
            .withName("Reset Encoders");
    }

    //Defense-wall deployment on hanger
    public Command createDefenseWallUp() {
        return runEnd(() -> runRightHangerUp(WALL_HANGER_UP_SPEED.getValue()), this::stopHanger).withName("Defense Wall - Right Hanger Up");
    }

    public Command createDefenseWallDown() {
        return runEnd(() -> runRightHangerDown(WALL_HANGER_DOWN_SPEED.getValue()), this::stopHanger).withName("Defense Wall - Right Hanger Down");
    }



}
