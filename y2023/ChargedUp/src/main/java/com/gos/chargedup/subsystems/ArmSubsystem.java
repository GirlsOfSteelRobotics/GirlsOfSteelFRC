package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import com.gos.chargedup.commands.PneumaticsMoveTest;
import com.gos.chargedup.commands.RobotMotorsMove;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.SingleJointedArmSimWrapper;

public class ArmSubsystem extends SubsystemBase {
    private static final GosDoubleProperty ALLOWABLE_ERROR = new GosDoubleProperty(false, "Pivot Arm Allowable Error", 0);

    private static final double GEAR_RATIO = 5.0 * 2.0 * 4.0;
    private static final double ARM_MOTOR_SPEED = 0.2;

    private final SimableCANSparkMax m_pivotMotor;
    private final RelativeEncoder m_pivotMotorEncoder;
    private final SparkMaxPIDController m_pivotPIDController;
    private final PidProperty m_pivotPID;

    private final Solenoid m_outerPiston;
    private final Solenoid m_innerPiston;
    private final DigitalInput m_lowerLimitSwitch;
    private final DigitalInput m_upperLimitSwitch;

    private final NetworkTableEntry m_lowerLimitSwitchEntry;
    private final NetworkTableEntry m_upperLimitSwitchEntry;
    private final NetworkTableEntry m_encoderDegEntry;
    private static final double GEARING =  252.0;
    private static final double J_KG_METERS_SQUARED = 1;
    private static final double ARM_LENGTH_METERS = Units.inchesToMeters(15);
    private static final double MIN_ANGLE_RADS = 0;
    private static final double MAX_ANGLE_RADS = Math.PI / 2;
    private static final double ARM_MASS_KG = Units.lbsToKilograms(5);
    private static final boolean SIMULATE_GRAVITY = true;
    private SingleJointedArmSimWrapper m_pivotSimulator;
    public static final GosDoubleProperty GRAVITY_OFFSET = new GosDoubleProperty(false, "Gravity Offset", 0);

    private double m_armAngleGoal = Double.MIN_VALUE;

    public ArmSubsystem() {
        m_pivotMotor = new SimableCANSparkMax(Constants.PIVOT_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_outerPiston = new Solenoid(PneumaticsModuleType.REVPH, Constants.ARM_OUTER_PISTON);
        m_innerPiston = new Solenoid(PneumaticsModuleType.REVPH, Constants.ARM_INNER_PISTON);

        m_pivotMotor.restoreFactoryDefaults();
        m_pivotMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        m_pivotMotorEncoder = m_pivotMotor.getEncoder();
        m_pivotPIDController = m_pivotMotor.getPIDController();

        m_pivotMotorEncoder.setPositionConversionFactor(360.0 / GEAR_RATIO);
        m_pivotMotorEncoder.setVelocityConversionFactor(360.0 / GEAR_RATIO / 60.0);

        m_lowerLimitSwitch = new DigitalInput(Constants.INTAKE_LOWER_LIMIT_SWITCH);
        m_upperLimitSwitch = new DigitalInput(Constants.INTAKE_UPPER_LIMIT_SWITCH);

        m_pivotPID = setupPidValues(m_pivotPIDController);

        m_pivotMotor.burnFlash();

        NetworkTable loggingTable = NetworkTableInstance.getDefault().getTable("Arm Subsystem");
        m_lowerLimitSwitchEntry = loggingTable.getEntry("Arm Lower LS");
        m_upperLimitSwitchEntry = loggingTable.getEntry("Arm Upper LS");
        m_encoderDegEntry = loggingTable.getEntry("Arm Encoder (deg)");

        if (RobotBase.isSimulation()) {
            SingleJointedArmSim armSim = new SingleJointedArmSim(DCMotor.getNeo550(1), GEARING, J_KG_METERS_SQUARED,
                ARM_LENGTH_METERS, MIN_ANGLE_RADS, MAX_ANGLE_RADS, ARM_MASS_KG, SIMULATE_GRAVITY);
            m_pivotSimulator = new SingleJointedArmSimWrapper(armSim, new RevMotorControllerSimWrapper(m_pivotMotor),
                RevEncoderSimWrapper.create(m_pivotMotor), true);
        }
    }

    private PidProperty setupPidValues(SparkMaxPIDController pidController) {
        return new RevPidPropertyBuilder("Arm", false, pidController, 0)
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
        m_pivotPID.updateIfChanged();
        m_lowerLimitSwitchEntry.setBoolean(isLowerLimitSwitchedPressed());
        m_upperLimitSwitchEntry.setBoolean(isUpperLimitSwitchedPressed());
        m_encoderDegEntry.setNumber(getArmAngleDeg());
    }

    public void pivotArmUp() {
        m_pivotMotor.set(ARM_MOTOR_SPEED);
    }

    public void pivotArmDown() {
        m_pivotMotor.set(-ARM_MOTOR_SPEED);
    }

    public double getArmMotorSpeed() {
        return m_pivotMotor.getAppliedOutput();
    }

    public void pivotArmStop() {
        m_armAngleGoal = Double.MIN_VALUE;
        m_pivotMotor.set(0);
    }

    public double getArmAngleDeg() {
        return m_pivotMotorEncoder.getPosition();
    }

    public double getArmAngleGoal() {
        return m_armAngleGoal;
    }

    public void fullRetract() {
        m_outerPiston.set(true);
        m_innerPiston.set(false);
    }

    public void middleRetract() {
        m_outerPiston.set(false);
        m_innerPiston.set(false);
    }

    public void out() {
        m_outerPiston.set(false);
        m_innerPiston.set(true);
    }

    public CommandBase commandPivotArmUp() {
        return this.startEnd(this::pivotArmUp, this::pivotArmStop);
    }

    public boolean isInnerPistonIn() {
        return m_innerPiston.get();
    }

    public CommandBase commandPivotArmDown() {
        return this.startEnd(this::pivotArmDown, this::pivotArmStop);
    }

    public CommandBase commandPivotArmToAngle(double angle) {
        return this.runEnd(() -> pivotArmToAngle(angle), this::pivotArmStop).withName("Arm to Angle" + angle);
    }

    public boolean isOuterPistonIn() {
        return m_outerPiston.get();
    }

    public boolean isLowerLimitSwitchedPressed() {
        return !m_lowerLimitSwitch.get();
    }

    public boolean isUpperLimitSwitchedPressed() {
        return !m_upperLimitSwitch.get();
    }

    public boolean pivotArmToAngle(double pivotAngleGoal) {
        m_armAngleGoal = pivotAngleGoal;

        double error = getArmAngleDeg() - pivotAngleGoal;
        double gravityOffset = Math.cos(getArmAngleDeg()) * GRAVITY_OFFSET.getValue();
        if (!isLowerLimitSwitchedPressed() || !isUpperLimitSwitchedPressed()) {
            m_pivotPIDController.setReference(pivotAngleGoal, CANSparkMax.ControlType.kSmartMotion, 0, gravityOffset);
        } else {
            m_pivotMotor.set(0);
        }

        return error <= ALLOWABLE_ERROR.getValue();
    }

    ///////////////////////
    // Command Factories
    ///////////////////////
    public CommandBase commandFullRetract() {
        return runOnce(this::fullRetract).withName("ArmPistonsFullRetract");
    }

    public CommandBase commandMiddleRetract() {
        return runOnce(this::middleRetract).withName("ArmPistonsMiddleRetract");
    }

    public CommandBase commandOut() {
        return runOnce(this::out).withName("ArmPistonsOut");
    }

    public CommandBase createIsPivotMotorMoving() {
        return new RobotMotorsMove(m_pivotMotor, "Arm: Pivot motor", 1.0);
    }

    public CommandBase createIsArmInnerPneumaticMoving(PneumaticHub pneumaticHub) {
        return new PneumaticsMoveTest(pneumaticHub, m_innerPiston, Constants.ARM_INNER_PISTON, "Arm: Inner Piston");
    }

    public CommandBase tuneGravityOffsetPID() {
        return this.runEnd(this::tuneGravityOffset, this::pivotArmStop);
    }

    public void tuneGravityOffset() {
        m_pivotMotor.setVoltage(GRAVITY_OFFSET.getValue());
    }

    @Override
    public void simulationPeriodic() {
        m_pivotSimulator.update();
    }

    public CommandBase createIsArmOuterPneumaticMoving(PneumaticHub pneumaticHub) {
        return new PneumaticsMoveTest(pneumaticHub, m_outerPiston, Constants.LEFT_CLAW_PISTON, "Claw: Left Piston");
    }

}

