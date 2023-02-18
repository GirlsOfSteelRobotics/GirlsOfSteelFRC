package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import com.gos.lib.rev.checklists.SparkMaxMotorsMoveChecklist;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.gos.lib.rev.SparkMaxAlerts;
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
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.InstantaneousMotorSim;

public class TurretSubsystem extends SubsystemBase {

    public static final double TURRET_LEFT_OF_INTAKE = -10;
    public static final double TURRET_RIGHT_OF_INTAKE = 10;

    private static final double TURRET_SPEED = 0.4;
    public static final GosDoubleProperty ALLOWABLE_ERROR_DEG = new GosDoubleProperty(false, "Turret Angle Allowable Error", 1);
    public static final GosDoubleProperty TUNING_VELOCITY = new GosDoubleProperty(false, "Turret Goal Velocity", 0);
    private final SimableCANSparkMax m_turretMotor;
    private final RelativeEncoder m_turretEncoder;
    private final PidProperty m_turretPID;
    private final SparkMaxPIDController m_turretPidController;

    private static final double GEAR_RATIO = 20.0 * (160.0 / 14.0);
    private double m_turretGoalAngle = Double.MIN_VALUE;

    private final DigitalInput m_leftLimitSwitch = new DigitalInput(Constants.LEFT_TURRET_LIMIT_SWITCH); //left ls relative to intake
    private final DigitalInput m_intakeLimitSwitch = new DigitalInput(Constants.INTAKE_TURRET_LIMIT_SWITCH);
    private final DigitalInput m_rightLimitSwitch = new DigitalInput(Constants.RIGHT_TURRET_LIMIT_SWITCH); //right ls relative to intake

    private final NetworkTableEntry m_leftLimitSwitchEntry;
    private final NetworkTableEntry m_rightLimitSwitchEntry;
    private final NetworkTableEntry m_intakeLimitSwitchEntry;
    private final NetworkTableEntry m_encoderDegEntry;
    private final NetworkTableEntry m_encoderVelocityEntry;
    private final NetworkTableEntry m_angleGoalEntry;

    private final SparkMaxAlerts m_turretMotorErrorAlert;

    private InstantaneousMotorSim m_turretSimulator;


    public TurretSubsystem() {
        m_turretMotor = new SimableCANSparkMax(Constants.TURRET_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_turretMotor.restoreFactoryDefaults();
        m_turretMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        m_turretEncoder = m_turretMotor.getEncoder();
        m_turretEncoder.setPositionConversionFactor(360.0 / GEAR_RATIO);
        m_turretEncoder.setVelocityConversionFactor(360.0 / GEAR_RATIO / 60.0);

        m_turretPidController = m_turretMotor.getPIDController();
        m_turretPID = setupPidValues(m_turretPidController);

        m_turretMotor.burnFlash();

        NetworkTable loggingTable = NetworkTableInstance.getDefault().getTable("Turret Subsystem");
        m_leftLimitSwitchEntry = loggingTable.getEntry("Turret Left LS");
        m_intakeLimitSwitchEntry = loggingTable.getEntry("Turret Intake LS");
        m_rightLimitSwitchEntry = loggingTable.getEntry("Turret Right LS");
        m_encoderDegEntry = loggingTable.getEntry("Turret Encoder (deg)");
        m_encoderVelocityEntry = loggingTable.getEntry("Turret Velocity (deg per sec)");
        m_angleGoalEntry = loggingTable.getEntry("Goal Angle");

        m_turretMotorErrorAlert = new SparkMaxAlerts(m_turretMotor, "turret motor");

        if (RobotBase.isSimulation()) {
            m_turretSimulator = new InstantaneousMotorSim(new RevMotorControllerSimWrapper(m_turretMotor), RevEncoderSimWrapper.create(m_turretMotor), 180);
        }
    }

    private PidProperty setupPidValues(SparkMaxPIDController pidController) {
        return new RevPidPropertyBuilder("Turret", false, pidController, 0)
            .addP(0.001) //0.20201
            .addI(0)
            .addD(0.04)
            .addFF(0.00675)
            .addMaxVelocity(Units.inchesToMeters(80))
            .addMaxAcceleration(Units.inchesToMeters(160))
            .build();
    }

    @Override
    public void periodic() {
        m_turretPID.updateIfChanged();

        m_leftLimitSwitchEntry.setBoolean(leftLimitSwitchPressed());
        m_intakeLimitSwitchEntry.setBoolean(intakeLimitSwitchPressed());
        m_rightLimitSwitchEntry.setBoolean(rightLimitSwitchPressed());
        m_encoderDegEntry.setNumber(getTurretAngleDegreesNeoEncoder());
        m_encoderVelocityEntry.setNumber(m_turretEncoder.getVelocity());
        m_angleGoalEntry.setNumber(m_turretGoalAngle);

        m_turretMotorErrorAlert.checkAlerts();
    }

    @Override
    public void simulationPeriodic() {
        m_turretSimulator.update();
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

    public boolean moveTurretToAngleWithPID(double goalAngle) {
        m_turretGoalAngle = goalAngle;

        double error = goalAngle - getTurretAngleDegreesNeoEncoder();

        m_turretPidController.setReference(goalAngle, CANSparkMax.ControlType.kSmartMotion, 0);
        return Math.abs(error) < ALLOWABLE_ERROR_DEG.getValue();
    }

    public void tuneVelocity(double goalVelocity) {
        m_turretPidController.setReference(goalVelocity, CANSparkMax.ControlType.kVelocity, 0);
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

    ///////////////////////
    // Command Factories
    ///////////////////////
    public CommandBase commandMoveTurretClockwise() {
        return this.runEnd(this::moveTurretClockwise, this::stopTurret).withName("Turret: Move CW");
    }

    public CommandBase commandMoveTurretCounterClockwise() {
        return this.runEnd(this::moveTurretCounterClockwise, this::stopTurret).withName("Turret: Move CCW");
    }

    public CommandBase createIsTurretMotorMoving() {
        return new SparkMaxMotorsMoveChecklist(this, m_turretMotor, "Turret: Turret motor", 1.0);
    }

    public CommandBase commandTurretPID(double angle) {
        return this.runEnd(() -> moveTurretToAngleWithPID(angle), this::stopTurret)
            .until(() -> moveTurretToAngleWithPID(angle))
            .withName("Turret PID" + angle);
    }

    public CommandBase createTuneVelocity() {
        return this.runEnd(() -> tuneVelocity(TUNING_VELOCITY.getValue()), this::stopTurret);
    }

    public CommandBase createTurretToCoastMode() {
        return this.runEnd(
            () -> m_turretMotor.setIdleMode(CANSparkMax.IdleMode.kCoast),
            () -> m_turretMotor.setIdleMode(CANSparkMax.IdleMode.kBrake))
            .ignoringDisable(true).withName("Turret to Coast");
    }

    public CommandBase createResetEncoder() {
        return this.runOnce(() -> m_turretEncoder.setPosition(0.0)).ignoringDisable(true).withName("Reset Turret Encoder");
    }
}

