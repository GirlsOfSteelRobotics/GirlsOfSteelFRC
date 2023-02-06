package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import com.gos.lib.rev.SparkMaxAlerts;
import com.gos.chargedup.commands.RobotMotorsMove;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TurretSubsystem extends SubsystemBase {

    private static final double TURRET_SPEED = 0.3;
    public static final GosDoubleProperty ALLOWABLE_ERROR_DEG = new GosDoubleProperty(false, "Turret Angle Allowable Error", 1);
    private final SimableCANSparkMax m_turretMotor;
    private final RelativeEncoder m_turretEncoder;
    private final PidProperty m_turretPID;
    private final SparkMaxPIDController m_turretPidController;

    private double m_turretGoalAngle = Double.MIN_VALUE;

    private final DigitalInput m_leftLimitSwitch = new DigitalInput(Constants.LEFT_TURRET_LIMIT_SWITCH); //left ls relative to intake
    private final DigitalInput m_intakeLimitSwitch = new DigitalInput(Constants.INTAKE_TURRET_LIMIT_SWITCH);
    private final DigitalInput m_rightLimitSwitch = new DigitalInput(Constants.RIGHT_TURRET_LIMIT_SWITCH); //right ls relative to intake

    private final NetworkTableEntry m_leftLimitSwitchEntry;
    private final NetworkTableEntry m_rightLimitSwitchEntry;
    private final NetworkTableEntry m_intakeLimitSwitchEntry;
    private final NetworkTableEntry m_encoderDegEntry;

    private final SparkMaxAlerts m_turretMotorErrorAlert;


    public TurretSubsystem() {
        m_turretMotor = new SimableCANSparkMax(Constants.TURRET_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_turretMotor.restoreFactoryDefaults();
        m_turretMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        m_turretEncoder = m_turretMotor.getEncoder();

        m_turretPidController = m_turretMotor.getPIDController();
        m_turretPID = setupPidValues(m_turretPidController);

        m_turretMotor.burnFlash();

        NetworkTable loggingTable = NetworkTableInstance.getDefault().getTable("Turret Subsystem");
        m_leftLimitSwitchEntry = loggingTable.getEntry("Turret Left LS");
        m_intakeLimitSwitchEntry = loggingTable.getEntry("Turret Intake LS");
        m_rightLimitSwitchEntry = loggingTable.getEntry("Turret Right LS");
        m_encoderDegEntry = loggingTable.getEntry("Turret Encoder (deg)");

        m_turretMotorErrorAlert = new SparkMaxAlerts(m_turretMotor, "turret motor");

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

        m_leftLimitSwitchEntry.setBoolean(leftLimitSwitchPressed());
        m_intakeLimitSwitchEntry.setBoolean(intakeLimitSwitchPressed());
        m_rightLimitSwitchEntry.setBoolean(rightLimitSwitchPressed());
        m_encoderDegEntry.setNumber(getTurretAngleDegreesNeoEncoder());

        m_turretMotorErrorAlert.checkAlerts();
    }

    public boolean turretMotorError() {
        return m_turretMotor.getFaults() != 0;
    }

    public void moveTurretClockwise() {
        m_turretMotor.set(TURRET_SPEED);
    }

    public void moveTurretCounterClockwise() {
        m_turretMotor.set(-TURRET_SPEED);
    }

    public void stopTurret() {
        m_turretGoalAngle = Double.MIN_VALUE;
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

    public double getTurretAngleDegreesNeoEncoder() {
        return m_turretEncoder.getPosition();
    }

    public boolean turretPID(double goalAngle) {
        m_turretGoalAngle = goalAngle;

        double error = goalAngle - getTurretAngleDegreesNeoEncoder();

        m_turretPidController.setReference(goalAngle, CANSparkMax.ControlType.kSmartMotion, 0);
        return Math.abs(error) < ALLOWABLE_ERROR_DEG.getValue();
    }

    public double getTurretAngleDeg() {
        return m_turretEncoder.getPosition();
    }

    public double getTurretAngleGoalDeg() {
        return m_turretGoalAngle;
    }

    public double getTurretSpeed() {
        return m_turretMotor.getAppliedOutput();
    }

    // Command Factories
    public CommandBase commandMoveTurretClockwise() {
        return this.runEnd(this::moveTurretClockwise, this::stopTurret).withName("MoveTurretCW");
    }

    public CommandBase commandMoveTurretCounterClockwise() {
        return this.runEnd(this::moveTurretCounterClockwise, this::stopTurret).withName("MoveTurretCCW");
    }

    public CommandBase createIsTurretMotorMoving() {
        return new RobotMotorsMove(m_turretMotor, "Turret: Turret motor", 1.0);
    }

}

