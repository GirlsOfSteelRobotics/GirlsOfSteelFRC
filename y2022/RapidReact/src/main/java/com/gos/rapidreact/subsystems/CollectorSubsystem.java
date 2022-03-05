package com.gos.rapidreact.subsystems;

import com.gos.lib.properties.PidProperty;
import com.gos.lib.properties.PropertyManager;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.gos.rapidreact.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.SingleJointedArmSimWrapper;

@SuppressWarnings("PMD.TooManyMethods")
public class CollectorSubsystem extends SubsystemBase {
    private static final double ROLLER_SPEED = 0.5;
    private static final double PIVOT_SPEED = 1;
    public static final double ALLOWABLE_ERROR_DEG = 2;
    public static final PropertyManager.IProperty<Double> GRAVITY_OFFSET = new PropertyManager.DoubleProperty("Gravity Offset", 0);
    private static final double GEARING =  756.0;
    private static final double J_KG_METERS_SQUARED = 1;
    private static final double ARM_LENGTH_METERS = Units.inchesToMeters(16);
    private static final double MIN_ANGLE_RADS = 0;
    private static final double MAX_ANGLE_RADS = Math.PI / 2;
    private static final double ARM_MASS_KG = Units.lbsToKilograms(5);
    private static final boolean SIMULATE_GRAVITY = true;

    // From SysId
    private static final double PIVOT_KS = 0.1831;
    //private static final double PIVOT_KV = 0.12391;
    //private static final double PIVOT_KA = 0.0063637;

    private final SimableCANSparkMax m_roller;
    private final SimableCANSparkMax m_pivotLeader;
    private final SimableCANSparkMax m_pivotFollower;

    private final DigitalInput m_indexSensor;

    private final RelativeEncoder m_pivotEncoder;


    private final PidProperty m_pivotPID;
    private final SparkMaxPIDController m_pidController;

    private SingleJointedArmSimWrapper m_simulator;

    private final SparkMaxLimitSwitch m_limitSwitch;

    public CollectorSubsystem() {
        m_roller = new SimableCANSparkMax(Constants.COLLECTOR_ROLLER, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_roller.restoreFactoryDefaults();
        m_roller.setIdleMode(CANSparkMax.IdleMode.kCoast);

        m_pivotLeader = new SimableCANSparkMax(Constants.COLLECTOR_PIVOT_LEADER, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_pivotLeader.restoreFactoryDefaults();
        m_pivotLeader.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_pivotLeader.setInverted(true);

        m_pivotFollower = new SimableCANSparkMax(Constants.COLLECTOR_PIVOT_FOLLOWER, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_pivotFollower.restoreFactoryDefaults();
        m_pivotFollower.setIdleMode(CANSparkMax.IdleMode.kBrake);

        m_pivotFollower.follow(m_pivotLeader, true);

        m_pivotEncoder = m_pivotLeader.getEncoder();
        m_pivotEncoder.setPositionConversionFactor(360.0 / GEARING);
        m_pivotEncoder.setVelocityConversionFactor(360.0 / GEARING / 60.0);

        m_indexSensor = new DigitalInput(Constants.INTAKE_INDEX_SENSOR);

        m_pidController = m_pivotLeader.getPIDController();

        CANSparkMax.IdleMode idleModeBreak = CANSparkMax.IdleMode.kBrake;
        CANSparkMax.IdleMode idleModeCoast = CANSparkMax.IdleMode.kCoast;
        m_pivotLeader.setIdleMode(idleModeBreak);
        m_roller.setIdleMode(idleModeCoast);

        m_limitSwitch = m_pivotLeader.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyClosed);
        m_limitSwitch.enableLimitSwitch(false);

        m_pivotPID = new RevPidPropertyBuilder("Pivot PID", false, m_pidController, 0)
            .addP(0.6)
            .addD(0)
            .addFF(0.0115)
            .addMaxVelocity(75)
            .addMaxAcceleration(120)
            .build();

        m_roller.burnFlash();
        m_pivotLeader.burnFlash();
        m_pivotFollower.burnFlash();

        if (RobotBase.isSimulation()) {
            SingleJointedArmSim armSim = new SingleJointedArmSim(DCMotor.getNeo550(1), GEARING, J_KG_METERS_SQUARED,
                ARM_LENGTH_METERS, MIN_ANGLE_RADS, MAX_ANGLE_RADS, ARM_MASS_KG, SIMULATE_GRAVITY);
            m_simulator = new SingleJointedArmSimWrapper(armSim, new RevMotorControllerSimWrapper(m_pivotLeader),
                RevEncoderSimWrapper.create(m_pivotLeader));
        }
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Pivot Encoder (rad)", getIntakeAngleRadians());
        SmartDashboard.putNumber("Pivot Encoder (deg)", getIntakeAngleDegrees());
        SmartDashboard.putNumber("Pivot Encoder (deg/sec)", m_pivotEncoder.getVelocity());
        SmartDashboard.putBoolean("Intake LS", m_limitSwitch.isPressed());
        m_pivotPID.updateIfChanged();
    }

    public void collectorDown() {
        m_pivotLeader.set(-PIVOT_SPEED);
    }

    public void collectorUp() {
        m_pivotLeader.set(PIVOT_SPEED);
    }

    public void rollerIn() {
        m_roller.set(ROLLER_SPEED);
    }

    public void rollerOut() {
        m_roller.set(-ROLLER_SPEED);
    }

    public void rollerStop() {
        m_roller.set(0);
    }

    public void pivotStop() {
        m_pivotLeader.set(0);
    }

    public void collectorDownPID() {
        collectorToAngle(0);
    }

    public void collectorUpPID() {
        collectorToAngle(Math.toRadians(90));
    }

    /**
     * gets pivot point of collector to given angle using pid
     * @param pivotAngleDegrees *IN DEGREES*
     */
    public void collectorToAngle(double pivotAngleDegrees) {

        double error = pivotAngleDegrees - getIntakeAngleDegrees();
        double gravityOffset = Math.cos(getIntakeAngleRadians()) * GRAVITY_OFFSET.getValue();
        double staticFriction = PIVOT_KS * Math.signum(error);
        double arbFeedforward = gravityOffset + staticFriction;
        System.out.println("Arm pid goal: " + pivotAngleDegrees + " sf: " + staticFriction + " g: " + gravityOffset + " -> " + arbFeedforward);
        m_pidController.setReference(pivotAngleDegrees, CANSparkMax.ControlType.kSmartMotion, 0, arbFeedforward);
    }

    public double getIntakeAngleRadians() {
        return Math.toRadians(getIntakeAngleDegrees());
    }

    public double getIntakeAngleDegrees() {
        return m_pivotEncoder.getPosition();
    }

    public double getPivotSpeed() {
        return m_pivotLeader.getAppliedOutput();
    }

    public double getRollerSpeed() {
        return m_roller.getAppliedOutput();
    }

    public void tuneGravityOffset() {
        m_pivotLeader.setVoltage(GRAVITY_OFFSET.getValue());
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }

    public void resetPivotEncoder() {
        m_pivotEncoder.setPosition(90);
    }

    public boolean getIndexSensor() {
        return m_indexSensor.get();
    }
}

