package com.gos.chargedup.subsystems;

import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.Constants;
import com.gos.chargedup.GamePieceType;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.gos.lib.rev.SparkMaxAlerts;
import com.gos.lib.rev.SparkMaxUtil;
import com.gos.lib.rev.checklists.SparkMaxMotorsMoveChecklist;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.SingleJointedArmSimWrapper;

import static com.revrobotics.SparkMaxAbsoluteEncoder.Type.kDutyCycle;


@SuppressWarnings({"PMD.GodClass", "PMD.ExcessivePublicCount"})
public class ArmPivotSubsystem extends SubsystemBase {

    private static final GosDoubleProperty ALLOWABLE_ERROR = new GosDoubleProperty(false, "Pivot Arm Allowable Error", 0.5);
    private static final GosDoubleProperty PID_STOP_ERROR = new GosDoubleProperty(false, "Pivot Arm PID Stop Error", 0.5);
    private static final GosDoubleProperty ALLOWABLE_VELOCITY_ERROR = new GosDoubleProperty(false, "Pivot Arm Allowable Velocity Error", 2);
    private static final GosDoubleProperty GRAVITY_OFFSET;

    private static final double ARM_MOTOR_SPEED = 0.20;
    private static final double ARM_LENGTH_METERS = Units.inchesToMeters(15);

    // From SysID
    private static final double KS;

    private static final double GEAR_RATIO = 45.0 * 4.0;

    // Arm Setpoints
    private static final double HUMAN_PLAYER_ANGLE;
    private static final double ARM_CUBE_MIDDLE_DEG;
    private static final double ARM_CUBE_HIGH_DEG;
    private static final double ARM_CONE_MIDDLE_DEG;
    private static final double ARM_CONE_HIGH_DEG;
    private static final double HOME_ANGLE;
    private static final double MIN_ANGLE_DEG;
    private static final double MAX_ANGLE_DEG;

    static {
        //toDo: make these values work as expected
        if (Constants.IS_ROBOT_BLOSSOM) {
            KS = 0.10072;
            GRAVITY_OFFSET = new GosDoubleProperty(false, "Gravity Offset", 0.2);

            HUMAN_PLAYER_ANGLE = 20;
            ARM_CUBE_MIDDLE_DEG = 0;
            ARM_CUBE_HIGH_DEG = 15;
            ARM_CONE_MIDDLE_DEG = 15;
            ARM_CONE_HIGH_DEG = 30;
            MIN_ANGLE_DEG = -60;
            MAX_ANGLE_DEG = 50;
            HOME_ANGLE = -20;

        } else {
            KS = 0.1375;
            GRAVITY_OFFSET = new GosDoubleProperty(false, "Gravity Offset", 0.3);


            HUMAN_PLAYER_ANGLE = 10;
            ARM_CUBE_MIDDLE_DEG = 0;
            ARM_CUBE_HIGH_DEG = 12;
            ARM_CONE_MIDDLE_DEG = 15;
            ARM_CONE_HIGH_DEG = 30;
            MIN_ANGLE_DEG = -60;
            MAX_ANGLE_DEG = 50;
            HOME_ANGLE = -45;
        }
    }

    public static final double ARM_HIT_INTAKE_ANGLE = 15;

    // Sim Stuff
    private static final double GEARING =  252.0;
    private static final double J_KG_METERS_SQUARED = 1;
    private static final double MIN_ANGLE_RADS = Math.toRadians(MIN_ANGLE_DEG);
    private static final double MAX_ANGLE_RADS = Math.toRadians(MAX_ANGLE_DEG);
    private static final boolean SIMULATE_GRAVITY = true;

    private final SimableCANSparkMax m_pivotMotor;
    private final RelativeEncoder m_pivotMotorEncoder;
    private final SparkMaxPIDController m_pivotPIDController;
    private final PidProperty m_pivotPID;

    private final SparkMaxAbsoluteEncoder m_absoluteEncoder;

    private final DigitalInput m_lowerLimitSwitch;
    private final DigitalInput m_upperLimitSwitch;

    private double m_armAngleGoal = Double.MIN_VALUE;

    private final NetworkTableEntry m_lowerLimitSwitchEntry;
    private final NetworkTableEntry m_upperLimitSwitchEntry;
    private final NetworkTableEntry m_encoderDegEntry;
    private final NetworkTableEntry m_goalAngleDegEntry;
    private final NetworkTableEntry m_velocityEntry;
    private final NetworkTableEntry m_effectiveGravityOffsetEntry;
    private final NetworkTableEntry m_pidArbitraryFeedForwardEntry;
    private final NetworkTableEntry m_absoluteEncoderEntry;

    private final SparkMaxAlerts m_pivotErrorAlert;

    private SingleJointedArmSimWrapper m_pivotSimulator;

    public ArmPivotSubsystem() {
        m_pivotMotor = new SimableCANSparkMax(Constants.PIVOT_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_pivotMotor.restoreFactoryDefaults();
        m_pivotMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_pivotMotor.setInverted(true);
        m_pivotMotor.setSmartCurrentLimit(60);
        m_pivotMotorEncoder = m_pivotMotor.getEncoder();
        m_pivotPIDController = m_pivotMotor.getPIDController();

        m_pivotMotorEncoder.setPositionConversionFactor(360.0 / GEAR_RATIO);
        m_pivotMotorEncoder.setVelocityConversionFactor(360.0 / GEAR_RATIO / 60.0);

        m_lowerLimitSwitch = new DigitalInput(Constants.INTAKE_LOWER_LIMIT_SWITCH);
        m_upperLimitSwitch = new DigitalInput(Constants.INTAKE_UPPER_LIMIT_SWITCH);
        m_pivotPID = setupPidValues(m_pivotPIDController);

        NetworkTable loggingTable = NetworkTableInstance.getDefault().getTable("Arm Subsystem");
        m_lowerLimitSwitchEntry = loggingTable.getEntry("Arm Lower LS");
        m_upperLimitSwitchEntry = loggingTable.getEntry("Arm Upper LS");
        m_encoderDegEntry = loggingTable.getEntry("Arm Encoder (deg)");
        m_goalAngleDegEntry = loggingTable.getEntry("Arm Goal (deg)");
        m_velocityEntry = loggingTable.getEntry("Arm Velocity");
        m_effectiveGravityOffsetEntry = loggingTable.getEntry("Effective Gravity Offset");
        m_pidArbitraryFeedForwardEntry = loggingTable.getEntry("Arbitrary FF");
        m_absoluteEncoderEntry = loggingTable.getEntry("Absolute Encoder Entry");
        m_pivotErrorAlert = new SparkMaxAlerts(m_pivotMotor, "arm pivot motor ");

        if (RobotBase.isSimulation()) {
            SingleJointedArmSim armSim = new SingleJointedArmSim(DCMotor.getNeo550(1), GEARING, J_KG_METERS_SQUARED,
                ARM_LENGTH_METERS, MIN_ANGLE_RADS, MAX_ANGLE_RADS, SIMULATE_GRAVITY);
            m_pivotSimulator = new SingleJointedArmSimWrapper(armSim, new RevMotorControllerSimWrapper(m_pivotMotor),
                RevEncoderSimWrapper.create(m_pivotMotor), true);
        }
        m_absoluteEncoder = m_pivotMotor.getAbsoluteEncoder(kDutyCycle);
        m_absoluteEncoder.setPositionConversionFactor(360.0 / GEAR_RATIO);
        m_absoluteEncoder.setVelocityConversionFactor(360.0 / GEAR_RATIO / 60.0);

        if (Constants.IS_ROBOT_BLOSSOM) {
            resetPivotEncoder(m_absoluteEncoder.getPosition());
        }
        else {
            resetPivotEncoder(MIN_ANGLE_DEG);
        }

        m_pivotMotor.burnFlash();
    }

    private PidProperty setupPidValues(SparkMaxPIDController pidController) {
        ///
        // Full retract:
        // kp=0.000400
        // kf=0.005000
        // kd=0.005000
        if (Constants.IS_ROBOT_BLOSSOM) {
            return new RevPidPropertyBuilder("Arm", false, pidController, 0)
                .addP(0.004)
                .addI(0)
                .addD(0)
                .addFF(0.005)
                .addMaxVelocity(120)
                .addMaxAcceleration(60)
                .build();
        } else {
            return new RevPidPropertyBuilder("Arm", false, pidController, 0)
                .addP(0.0045) // 0.0058
                .addI(0)
                .addD(0.045)
                .addFF(0.0053) // 0.0065
                .addMaxVelocity(60)
                .addMaxAcceleration(180)
                .build();
        }
    }

    @Override
    public void periodic() {
        m_pivotPID.updateIfChanged();
        m_lowerLimitSwitchEntry.setBoolean(isLowerLimitSwitchedPressed());
        m_upperLimitSwitchEntry.setBoolean(isUpperLimitSwitchedPressed());
        m_encoderDegEntry.setNumber(getArmAngleDeg());
        m_absoluteEncoderEntry.setNumber(m_absoluteEncoder.getPosition());
        m_goalAngleDegEntry.setNumber(m_armAngleGoal);
        m_velocityEntry.setNumber(getArmVelocityDegPerSec());

        m_pivotErrorAlert.checkAlerts();
    }

    @Override
    public void simulationPeriodic() {
        m_pivotSimulator.update();
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

    public double getArmVelocityDegPerSec() {
        return m_pivotMotorEncoder.getVelocity();
    }

    public boolean isUpperLimitSwitchedPressed() {
        return !m_upperLimitSwitch.get();
    }

    public boolean isLowerLimitSwitchedPressed() {
        return !m_lowerLimitSwitch.get();
    }

    public void pivotArmToAngle(double pivotAngleGoal) {
        m_armAngleGoal = pivotAngleGoal;

        double error = m_armAngleGoal - getArmAngleDeg();
        double gravityOffset = Math.cos(Math.toRadians(getArmAngleDeg())) * GRAVITY_OFFSET.getValue();
        double arbFf = gravityOffset + KS * Math.signum(error);

        m_effectiveGravityOffsetEntry.setNumber(gravityOffset);
        m_pidArbitraryFeedForwardEntry.setNumber(arbFf);

        if (!isLowerLimitSwitchedPressed() || !isUpperLimitSwitchedPressed()) {
            if (Math.abs(error) < PID_STOP_ERROR.getValue()) {
                m_pivotMotor.set(0);
            } else {
                m_pivotPIDController.setReference(pivotAngleGoal, CANSparkMax.ControlType.kSmartMotion, 0, arbFf);
            }
        } else {
            m_pivotMotor.set(0);
        }
    }

    public boolean isArmAtAngle() {
        return isArmAtAngle(m_armAngleGoal);
    }

    public boolean isArmAtAngle(double pivotAngleGoal) {
        double error = getArmAngleDeg() - pivotAngleGoal;
        double velocity = getArmVelocityDegPerSec();

        return Math.abs(error) <= ALLOWABLE_ERROR.getValue() && Math.abs(velocity) < ALLOWABLE_VELOCITY_ERROR.getValue();
    }

    public final void resetPivotEncoder(double angle) {
        SparkMaxUtil.autoRetry(() -> m_pivotMotorEncoder.setPosition(angle));
    }

    public double getArmAngleForScoring(AutoPivotHeight pivotHeightType, GamePieceType gamePieceType) {
        double angle = 0.0;

        switch (pivotHeightType) {
        case HIGH:
            if (gamePieceType == GamePieceType.CONE) {
                //pivotArmToAngle(ARM_CONE_HIGH_DEG);
                angle = ARM_CONE_HIGH_DEG;
            } else {
                //pivotArmToAngle();
                angle = ARM_CUBE_HIGH_DEG;
            }
            break;
        case MEDIUM:
            if (gamePieceType == GamePieceType.CONE) {
                //pivotArmToAngle();
                angle = ARM_CONE_MIDDLE_DEG;
            } else {
                //pivotArmToAngle();
                angle = ARM_CUBE_MIDDLE_DEG;
            }
            break;
        case LOW:
            //pivotArmToAngle();
            angle = HOME_ANGLE;
            break;
        default:
            angle = ARM_CONE_HIGH_DEG;
            break;
        }
        return angle;
    }

    public void tuneGravityOffset() {
        m_pivotMotor.setVoltage(GRAVITY_OFFSET.getValue());
    }


    ///////////////////////
    // Command Factories
    ///////////////////////
    public CommandBase createPivotToCoastMode() {
        return this.runEnd(
            () -> m_pivotMotor.setIdleMode(CANSparkMax.IdleMode.kCoast),
            () -> m_pivotMotor.setIdleMode(CANSparkMax.IdleMode.kBrake))
            .ignoringDisable(true).withName("Pivot to Coast");
    }

    public CommandBase createResetPivotEncoder() {
        return createResetPivotEncoder(MIN_ANGLE_DEG);
    }

    public CommandBase createResetPivotEncoder(double angle) {
        return this.run(() -> resetPivotEncoder(angle))
            .ignoringDisable(true)
            .withName("Reset Pivot Encoder (" + angle + ")");
    }

    public CommandBase tuneGravityOffsetPID() {
        return this.runEnd(this::tuneGravityOffset, this::pivotArmStop).withName("Tune Gravity Offset");
    }

    public CommandBase commandPivotArmUpPrevention(ChassisSubsystem cs, CommandXboxController x) {
        return new ConditionalCommand(
            this.runEnd(this::pivotArmUp, this::pivotArmStop).withName("MoveArmUp"),
            this.run(() ->  x.getHID().setRumble(GenericHID.RumbleType.kLeftRumble, 1)),
            cs::canExtendArm);
    }

    public CommandBase commandPivotArmUp() {
        return this.runEnd(this::pivotArmUp, this::pivotArmStop).withName("Arm: Pivot Down");
    }

    public CommandBase commandPivotArmDownPrevention(ChassisSubsystem cs, CommandXboxController x) {
        return new ConditionalCommand(
            this.runEnd(this::pivotArmDown, this::pivotArmStop).withName("MoveArmDown"),
            this.run(() ->  x.getHID().setRumble(GenericHID.RumbleType.kLeftRumble, 1)),
            cs::canExtendArm);
    }

    public CommandBase commandPivotArmDown() {
        return this.runEnd(this::pivotArmDown, this::pivotArmStop).withName("Arm: Pivot Up");
    }

    public CommandBase commandPivotArmToAngleHold(double angle) {
        return this.run(() -> pivotArmToAngle(angle))
            .until(() -> isArmAtAngle(angle))
            .withName("Arm to Angle And Hold" + angle);
    }

    public CommandBase commandPivotArmToAnglePrevention(double angle, ChassisSubsystem cs, CommandXboxController x) {
        return new ConditionalCommand(
            this.runEnd(() -> pivotArmToAngle(angle), this::pivotArmStop).withName("Arm to Angle" + angle),
            this.run(() -> {
                System.out.println("BAD");
                x.getHID().setRumble(GenericHID.RumbleType.kLeftRumble, 1);
            }),
            cs::canExtendArm);
    }

    public CommandBase commandPivotArmToAngleNonHold(double angle) {
        return this.runEnd(() -> pivotArmToAngle(angle), this::pivotArmStop)
            .until(() -> isArmAtAngle(angle))
            .withName("Arm to Angle" + angle);
    }

    public CommandBase commandMoveArmToPieceScorePositionAndHold(AutoPivotHeight height, GamePieceType gamePieceType) {
        double angle = getArmAngleForScoring(height, gamePieceType);
        return commandPivotArmToAngleHold(angle).withName("Score [" + height + "," + gamePieceType + "] and Hold");
    }

    public CommandBase commandMoveArmToPieceScorePositionDontHold(AutoPivotHeight height, GamePieceType gamePieceType) {
        double angle = getArmAngleForScoring(height, gamePieceType);
        return commandPivotArmToAngleNonHold(angle).withName("Score [" + height + "," + gamePieceType + "]");
    }

    public CommandBase commandGoHome() {
        return commandPivotArmToAngleNonHold(HOME_ANGLE);
    }

    public CommandBase commandHpPickupNoHold() {
        return commandPivotArmToAngleNonHold(HUMAN_PLAYER_ANGLE);
    }

    public CommandBase commandHpPickupHold() {
        return commandPivotArmToAngleHold(HUMAN_PLAYER_ANGLE);
    }

    ////////////////
    // Checklists
    ////////////////

    public CommandBase createIsPivotMotorMoving() {
        return new SparkMaxMotorsMoveChecklist(this, m_pivotMotor, "Arm: Pivot motor", 1.0);
    }
}

