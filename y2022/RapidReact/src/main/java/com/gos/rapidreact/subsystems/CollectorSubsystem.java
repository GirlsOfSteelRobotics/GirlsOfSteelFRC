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
    private static final double PIVOT_SPEED = .75;
    public static final double ALLOWABLE_ERROR_DEG = 1;
    public static final PropertyManager.IProperty<Double> GRAVITY_OFFSET = PropertyManager.createDoubleProperty(false, "Gravity Offset", 0);
    private static final double GEARING =  756.0;
    private static final double J_KG_METERS_SQUARED = 1;
    private static final double ARM_LENGTH_METERS = Units.inchesToMeters(16);
    private static final double MIN_ANGLE_RADS = 0;
    private static final double MAX_ANGLE_RADS = Math.PI / 2;
    private static final double ARM_MASS_KG = Units.lbsToKilograms(5);
    private static final boolean SIMULATE_GRAVITY = true;

    // TODO play with these numbers for optimal ball pickup
    public static final double UP_ANGLE = 80;
    public static final double DOWN_ANGLE = RobotBase.isReal() ? 2 : 0;

    // From SysId
    private static final double PIVOT_KS = 0.1831;
    //private static final double PIVOT_KV = 0.12391;
    //private static final double PIVOT_KA = 0.0063637;

    private final SimableCANSparkMax m_roller;
    private final SimableCANSparkMax m_pivotLeft;
    private final SimableCANSparkMax m_pivotRight;

    private final DigitalInput m_indexSensor;

    private final RelativeEncoder m_pivotEncoderLeft;
    private final RelativeEncoder m_pivotEncoderRight;

    private final PidProperty m_pivotPIDLeft;
    private final SparkMaxPIDController m_pidControllerLeft;

    private final PidProperty m_pivotPIDRight;
    private final SparkMaxPIDController m_pidControllerRight;

    private SingleJointedArmSimWrapper m_simulator;

    private final SparkMaxLimitSwitch m_limitSwitch;

    private double m_counter; //TODO: take this out


    private double m_pivotChangingSetpoint = DOWN_ANGLE;

    public CollectorSubsystem() {
        m_roller = new SimableCANSparkMax(Constants.COLLECTOR_ROLLER, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_roller.restoreFactoryDefaults();
        m_roller.setIdleMode(CANSparkMax.IdleMode.kCoast);

        m_pivotLeft = new SimableCANSparkMax(Constants.COLLECTOR_PIVOT_LEADER, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_pivotLeft.restoreFactoryDefaults();
        m_pivotLeft.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_pivotLeft.setInverted(true);

        m_pivotRight = new SimableCANSparkMax(Constants.COLLECTOR_PIVOT_FOLLOWER, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_pivotRight.restoreFactoryDefaults();
        m_pivotRight.setIdleMode(CANSparkMax.IdleMode.kBrake);

        m_pivotRight.follow(m_pivotLeft, true);

        m_pivotEncoderLeft = m_pivotLeft.getEncoder();
        m_pivotEncoderLeft.setPositionConversionFactor(360.0 / GEARING);
        m_pivotEncoderLeft.setVelocityConversionFactor(360.0 / GEARING / 60.0);

        m_pivotEncoderRight = m_pivotRight.getEncoder();
        m_pivotEncoderRight.setPositionConversionFactor(360.0 / GEARING);
        m_pivotEncoderRight.setVelocityConversionFactor(360.0 / GEARING / 60.0);

        m_indexSensor = new DigitalInput(Constants.INTAKE_INDEX_SENSOR);

        m_pidControllerLeft = m_pivotLeft.getPIDController();
        m_pidControllerRight = m_pivotRight.getPIDController();

        CANSparkMax.IdleMode idleModeBreak = CANSparkMax.IdleMode.kBrake;
        CANSparkMax.IdleMode idleModeCoast = CANSparkMax.IdleMode.kCoast;
        m_pivotLeft.setIdleMode(idleModeBreak);
        m_roller.setIdleMode(idleModeCoast);

        m_limitSwitch = m_pivotLeft.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyClosed);
        m_limitSwitch.enableLimitSwitch(true);

        m_pivotPIDLeft = setupPidValues(m_pidControllerLeft);
        m_pivotPIDRight = setupPidValues(m_pidControllerRight);

        resetPivotEncoder();

        m_roller.burnFlash();
        m_pivotLeft.burnFlash();
        m_pivotRight.burnFlash();

        if (RobotBase.isSimulation()) {
            SingleJointedArmSim armSim = new SingleJointedArmSim(DCMotor.getNeo550(1), GEARING, J_KG_METERS_SQUARED,
                ARM_LENGTH_METERS, MIN_ANGLE_RADS, MAX_ANGLE_RADS, ARM_MASS_KG, SIMULATE_GRAVITY);
            m_simulator = new SingleJointedArmSimWrapper(armSim, new RevMotorControllerSimWrapper(m_pivotLeft),
                RevEncoderSimWrapper.create(m_pivotLeft), true);
        }
    }

    private PidProperty setupPidValues(SparkMaxPIDController pidController) {
        return new RevPidPropertyBuilder("Collector", false, pidController, 0)
            .addP(0)
            .addI(0)
            .addD(0)
            .addFF(0)
            .addMaxVelocity(Units.inchesToMeters(0))
            .addMaxAcceleration(Units.inchesToMeters(0))
            .build();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Pivot Lead Encoder (rad)", getIntakeLeftAngleRadians());
        SmartDashboard.putNumber("Pivot Lead Encoder (deg)", getIntakeLeftAngleDegrees());
        SmartDashboard.putNumber("Pivot Lead Encoder (deg/sec)", m_pivotEncoderLeft.getVelocity());
        SmartDashboard.putNumber("Pivot Follow Encoder (deg)", getIntakeRightAngleDegrees());
        SmartDashboard.putBoolean("Intake LS", m_limitSwitch.isPressed());
        m_pivotPIDLeft.updateIfChanged();
        m_pivotPIDRight.updateIfChanged();

        if (limitSwitchPressed()) {
            m_pivotEncoderLeft.setPosition(90);
            m_pivotEncoderRight.setPosition(90);
        }

        m_counter++;
        if (m_counter == 5) {
            m_counter = 0;
            //System.out.println("left:  " + getIntakeLeftAngleDegrees());
            //System.out.println("right:  " + getIntakeRightAngleDegrees());
            //System.out.println();
        }
    }

    public void collectorDown() {
        if (limitSwitchPressed()) {
            m_pivotLeft.set(0);
            m_pivotRight.set(0);
        }

        else {
            m_pivotLeft.set(-PIVOT_SPEED);
            m_pivotRight.set(-PIVOT_SPEED);
            m_pivotChangingSetpoint = getIntakeLeftAngleDegrees();
        }
    }

    public void collectorUp() {
        m_pivotLeft.set(PIVOT_SPEED);
        m_pivotRight.set(PIVOT_SPEED);

        m_pivotChangingSetpoint = getIntakeLeftAngleDegrees();
    }

    public void pivotToMagicAngle() {
        SmartDashboard.putNumber("AHHHH", m_pivotChangingSetpoint);
        collectorToAngle(m_pivotChangingSetpoint);
    }

    public boolean limitSwitchPressed() {
        return m_limitSwitch.isPressed();
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
        m_pivotLeft.set(0);
        m_pivotRight.set(0);
    }

    public void collectorDownPID() {
        collectorToAngle(DOWN_ANGLE);
    }

    public void collectorUpPID() {
        collectorToAngle(Math.toRadians(UP_ANGLE));
    }

    /**
     * gets pivot point of collector to given angle using pid
     * @param pivotAngleDegrees *IN DEGREES*
     */
    public void collectorToAngle(double pivotAngleDegrees) {

        if (limitSwitchPressed()) {
            pivotStop();
        }

        else {
            double errorLeft = pivotAngleDegrees - getIntakeLeftAngleDegrees();
            double errorRight = pivotAngleDegrees - getIntakeRightAngleDegrees();

            double gravityOffset = Math.cos(getIntakeLeftAngleRadians()) * GRAVITY_OFFSET.getValue();
            double staticFrictionLeft = PIVOT_KS * Math.signum(errorLeft);
            double staticFrictionRight = PIVOT_KS * Math.signum(errorRight);
            double arbFeedforwardLeft = gravityOffset + staticFrictionLeft;
            double arbFeedforwardRight = gravityOffset + staticFrictionRight;
            m_pidControllerLeft.setReference(pivotAngleDegrees, CANSparkMax.ControlType.kSmartMotion, 0, arbFeedforwardLeft);
            m_pidControllerRight.setReference(pivotAngleDegrees, CANSparkMax.ControlType.kSmartMotion, 0, arbFeedforwardRight);
        }

    }

    public double getIntakeLeftAngleRadians() {
        return Math.toRadians(getIntakeLeftAngleDegrees());
    } //for leader

    public double getIntakeLeftAngleDegrees() { //for leader
        return m_pivotEncoderLeft.getPosition();
    }

    public double getIntakeRightAngleDegrees() {
        return m_pivotEncoderRight.getPosition();
    }

    public double getPivotSpeed() {
        return m_pivotLeft.getAppliedOutput();
    }

    public double getRollerSpeed() {
        return m_roller.getAppliedOutput();
    }

    public void tuneGravityOffset() {
        m_pivotLeft.setVoltage(GRAVITY_OFFSET.getValue());
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }

    public final void resetPivotEncoder() {
        m_pivotEncoderLeft.setPosition(90);
        m_pivotEncoderRight.setPosition(90);
    }

    public boolean getIndexSensor() {
        return !m_indexSensor.get();
    }
}

