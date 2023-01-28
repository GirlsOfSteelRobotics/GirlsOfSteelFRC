package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;

public class TurretSubsystem extends SubsystemBase {

    private static final double TURRET_SPEED = 0.2;

    public static final GosDoubleProperty ALLOWABLE_ERROR_DEG = new GosDoubleProperty(false, "Gravity Offset", 1);

    private final SimableCANSparkMax m_turretMotor;

    private final RelativeEncoder m_turretEncoder;

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

        m_turretEncoder = m_turretMotor.getEncoder();

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


    public void moveTurretClockwise() {
        m_turretMotor.set(TURRET_SPEED);
    }

    public void moveTurretCounterClockwise() {
        m_turretMotor.set(-TURRET_SPEED);
    }

    public void stopTurret() {
        m_turretMotor.set(0);
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

    public Command commandMoveTurretClockwise() {
        return this.startEnd(this::moveTurretClockwise, this::stopTurret);
    }

    public Command commandMoveTurretCounterClockwise() {
        return this.startEnd(this::commandMoveTurretCounterClockwise, this::stopTurret);
    }

    public double getTurretAngleDegreesNeoEncoder() {

        return m_turretEncoder.getPosition();
    }


    public boolean turretPID(double goalAngle) {

        double error = goalAngle - getTurretAngleDegreesNeoEncoder();

        m_turretPidController.setReference(goalAngle, CANSparkMax.ControlType.kSmartMotion, 0);
        return Math.abs(error) < ALLOWABLE_ERROR_DEG.getValue();
    }


}

