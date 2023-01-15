package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TurretSubsystem extends SubsystemBase {

    private final SimableCANSparkMax m_turretMotor;

    private final PidProperty m_turretPID;
    private final SparkMaxPIDController m_turretPidController;

    //left relative to intake limit switch
    private final DigitalInput m_leftLimitSwitch = new DigitalInput(Constants.LEFT_LIMIT_SWITCH);
    private final DigitalInput m_intakeLimitSwitch = new DigitalInput(Constants.INTAKE_LIMIT_SWITCH);
    //right relative to intake limit switch
    private final DigitalInput m_rightLimitSwitch = new DigitalInput(Constants.RIGHT_LIMIT_SWITCH);

    public TurretSubsystem() {

        m_turretMotor = new SimableCANSparkMax(Constants.TURRET_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_turretMotor.restoreFactoryDefaults();
        m_turretMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        m_turretPidController = m_turretMotor.getPIDController();
        m_turretPID = setupPidValues(m_turretPidController);
    }

    private PidProperty setupPidValues(SparkMaxPIDController pidController) {
        return new RevPidPropertyBuilder("Collector", false, pidController, 0)
            .addP(0) //0.20201
            .addI(0)
            .addD(0)
            .addFF(0)
            .addMaxVelocity(Units.inchesToMeters(0))
            .addMaxAcceleration(Units.inchesToMeters(0))
            .build();
    }

    @Override
    public void periodic() {
        m_turretPID.updateIfChanged();

    }


    public void moveTurretClockwise(double speed) {
        m_turretMotor.set(speed);
    }

    public void moveTurretCounterClockwise(double speed) {
        m_turretMotor.set(-speed);
    }

    public boolean leftLimitSwitchPressed() {
        return !m_leftLimitSwitch.get();
    }

    public boolean intakeLimitSwitchPressed() {
        return !m_intakeLimitSwitch.get();
    }

    public boolean rightLimitSwitchPressed() {
        return !m_rightLimitSwitch.get();
    }
}

