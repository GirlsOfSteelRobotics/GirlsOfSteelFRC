package com.gos.chargedup.subsystems;

import com.gos.chargedup.AutoAimNodePositions;
import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.Constants;
import com.gos.chargedup.GamePieceType;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.properties.WpiProfiledPidPropertyBuilder;
import com.gos.lib.properties.feedforward.ArmFeedForwardProperty;
import com.gos.lib.rev.SparkMaxAlerts;
import com.gos.lib.rev.SparkMaxUtil;
import com.gos.lib.rev.checklists.SparkMaxMotorsMoveChecklist;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.SingleJointedArmSimWrapper;

import java.util.function.Supplier;

import static com.revrobotics.SparkMaxAbsoluteEncoder.Type.kDutyCycle;


@SuppressWarnings({"PMD.GodClass", "PMD.ExcessivePublicCount"})
public class ArmPivotSubsystem extends SubsystemBase {

    private static final GosDoubleProperty ALLOWABLE_ERROR = new GosDoubleProperty(false, "Pivot Arm Allowable Error", 3);
    private static final GosDoubleProperty ALLOWABLE_VELOCITY_ERROR = new GosDoubleProperty(false, "Pivot Arm Allowable Velocity Error", 20);
    private static final GosDoubleProperty GRAVITY_OFFSET;

    private static final double ARM_MOTOR_SPEED = 0.20;
    private static final double ARM_LENGTH_METERS = Units.inchesToMeters(15);

    private static final double GEAR_RATIO = 45.0 * 4.0;

    // Arm Setpoints
    public static final double HUMAN_PLAYER_ANGLE;
    public static final double ARM_CUBE_MIDDLE_DEG;
    public static final double ARM_CUBE_HIGH_DEG;
    public static final double ARM_CONE_MIDDLE_DEG;
    public static final double ARM_CONE_HIGH_DEG;
    public static final double ARM_SCORE_LOW_DEG;
    public static final double GROUND_PICKUP_ANGLE;
    public static final double HOME_ANGLE;
    public static final double MIN_ANGLE_DEG;
    public static final double MAX_ANGLE_DEG;

    static {
        //toDo: make these values work as expected
        if (Constants.IS_ROBOT_BLOSSOM) {
            GRAVITY_OFFSET = new GosDoubleProperty(false, "Pivot Arm Gravity Offset", 0.2);

            HUMAN_PLAYER_ANGLE = 14;
            ARM_CUBE_MIDDLE_DEG = 0;
            ARM_CUBE_HIGH_DEG = 23;
            ARM_CONE_MIDDLE_DEG = 15;
            ARM_CONE_HIGH_DEG = 25;
            MIN_ANGLE_DEG = -60;
            MAX_ANGLE_DEG = 50;
            HOME_ANGLE = MIN_ANGLE_DEG + 5;
            GROUND_PICKUP_ANGLE = -43;
            ARM_SCORE_LOW_DEG = -35;

        } else {
            GRAVITY_OFFSET = new GosDoubleProperty(false, "Gravity Offset", 0.3);

            HUMAN_PLAYER_ANGLE = 10;
            ARM_CUBE_MIDDLE_DEG = 0;
            ARM_CUBE_HIGH_DEG = 12;
            ARM_CONE_MIDDLE_DEG = 15;
            ARM_CONE_HIGH_DEG = 30;
            MIN_ANGLE_DEG = -60;
            MAX_ANGLE_DEG = 50;
            HOME_ANGLE = -45;
            GROUND_PICKUP_ANGLE = -20;
            ARM_SCORE_LOW_DEG = -25;
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

    private final Supplier<ArmExtensionSubsystem.ArmExtension> m_armExtensionSupplier;

    private final ProfiledPIDController m_midPivotPidController;
    private final PidProperty m_midPidProperty;
    private final ArmFeedForwardProperty m_midPidFeedForward;

    private final ProfiledPIDController m_retractedPivotPidController;
    private final PidProperty m_retractedPidProperty;
    private final ArmFeedForwardProperty m_retractedPidFeedForward;

    private final SparkMaxAbsoluteEncoder m_absoluteEncoder;

    private final DigitalInput m_lowerLimitSwitch;
    private final DigitalInput m_upperLimitSwitch;

    private double m_armAngleGoal = Double.MIN_VALUE;

    private final NetworkTableEntry m_lowerLimitSwitchEntry;
    private final NetworkTableEntry m_upperLimitSwitchEntry;
    private final NetworkTableEntry m_encoderDegEntry;
    private final NetworkTableEntry m_goalAngleDegEntry;
    private final NetworkTableEntry m_velocityEntry;
    private final NetworkTableEntry m_velocityGoalEntry;
    private final NetworkTableEntry m_absoluteEncoderEntry;
    private final NetworkTableEntry m_absoluteEncoderToBuiltInDriftEntry;

    private final SparkMaxAlerts m_pivotErrorAlert;

    private SingleJointedArmSimWrapper m_pivotSimulator;

    public ArmPivotSubsystem(Supplier<ArmExtensionSubsystem.ArmExtension> armExtensionSupplier) {
        m_armExtensionSupplier = armExtensionSupplier;

        m_pivotMotor = new SimableCANSparkMax(Constants.PIVOT_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_pivotMotor.restoreFactoryDefaults();
        m_pivotMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_pivotMotor.setInverted(true);
        m_pivotMotor.setSmartCurrentLimit(60);
        m_pivotMotorEncoder = m_pivotMotor.getEncoder();

        m_pivotMotorEncoder.setPositionConversionFactor(360.0 / GEAR_RATIO);
        m_pivotMotorEncoder.setVelocityConversionFactor(360.0 / GEAR_RATIO / 60.0);

        m_pivotMotor.enableVoltageCompensation(8);

        m_lowerLimitSwitch = new DigitalInput(Constants.INTAKE_LOWER_LIMIT_SWITCH);
        m_upperLimitSwitch = new DigitalInput(Constants.INTAKE_UPPER_LIMIT_SWITCH);

        m_midPivotPidController = new ProfiledPIDController(0, 0, 0, new TrapezoidProfile.Constraints(0, 0));
        m_midPidProperty = setupPidValuesMid(m_midPivotPidController);

        m_retractedPivotPidController = new ProfiledPIDController(0, 0, 0, new TrapezoidProfile.Constraints(0, 0));
        m_retractedPidProperty = setupPidValuesRetract(m_retractedPivotPidController);

        m_midPidFeedForward = new ArmFeedForwardProperty("WPI Pivot Arm MID", false)
            .addKa(0.02)
            .addKg(0.7)
            .addKff(5.5)
            .addKs(0.10072);

        m_retractedPidFeedForward = new ArmFeedForwardProperty("WPI Pivot Arm RETRACT", false)
            .addKa(0.02)
            .addKg(0.22)
            .addKff(5.5)
            .addKs(0.10072);


        NetworkTable loggingTable = NetworkTableInstance.getDefault().getTable("Arm Subsystem");
        m_lowerLimitSwitchEntry = loggingTable.getEntry("Arm Lower LS");
        m_upperLimitSwitchEntry = loggingTable.getEntry("Arm Upper LS");
        m_encoderDegEntry = loggingTable.getEntry("Arm Encoder (deg)");
        m_goalAngleDegEntry = loggingTable.getEntry("Arm Goal (deg)");
        m_velocityEntry = loggingTable.getEntry("Arm Velocity");
        m_velocityGoalEntry = loggingTable.getEntry("Velocity Goal");
        m_absoluteEncoderEntry = loggingTable.getEntry("Absolute Encoder Entry");
        m_absoluteEncoderToBuiltInDriftEntry = loggingTable.getEntry("Encoder Drift");
        m_pivotErrorAlert = new SparkMaxAlerts(m_pivotMotor, "arm pivot motor ");

        if (RobotBase.isSimulation()) {
            SingleJointedArmSim armSim = new SingleJointedArmSim(DCMotor.getNeo550(1), GEARING, J_KG_METERS_SQUARED,
                ARM_LENGTH_METERS, MIN_ANGLE_RADS, MAX_ANGLE_RADS, SIMULATE_GRAVITY);
            m_pivotSimulator = new SingleJointedArmSimWrapper(armSim, new RevMotorControllerSimWrapper(m_pivotMotor),
                RevEncoderSimWrapper.create(m_pivotMotor), true);
        }
        m_absoluteEncoder = m_pivotMotor.getAbsoluteEncoder(kDutyCycle);
        m_absoluteEncoder.setPositionConversionFactor(360.0);
        m_absoluteEncoder.setVelocityConversionFactor(360.0 / 60);
        m_absoluteEncoder.setInverted(true);
        m_absoluteEncoder.setZeroOffset(21.5);

        if (Constants.IS_ROBOT_BLOSSOM) {
            syncMotorEncoderToAbsoluteEncoder();
        }
        else {
            resetPivotEncoder(MIN_ANGLE_DEG);
        }

        m_pivotMotor.burnFlash();
    }

    public final void syncMotorEncoderToAbsoluteEncoder() {
        resetPivotEncoder(getAbsoluteEncoderAngle());
    }

    public double getAbsoluteEncoderVelocity() {
        return m_absoluteEncoder.getVelocity();
    }

    public final double getAbsoluteEncoderAngle() {
        if (RobotBase.isSimulation()) {
            return getArmAngleDeg();
        }

        double val = m_absoluteEncoder.getPosition();
        if (val > 180) {
            val -= 360;
        }
        if (val < -180) {
            val += 360;
        }

        return val;
    }

    private PidProperty setupPidValuesMid(ProfiledPIDController pidController) {
        WpiProfiledPidPropertyBuilder pidPropertyBuilder = new WpiProfiledPidPropertyBuilder("WPI Pivot Arm MID", false, pidController);

        ///
        // Full retract:
        // kp=0.000400
        // kf=0.005000
        // kd=0.005000
        if (Constants.IS_ROBOT_BLOSSOM) {
            return pidPropertyBuilder
                .addP(0.0195)
                .addI(0)
                .addD(0)
                // .addFF(0)
                .addMaxAcceleration(180)
                .addMaxVelocity(80)
                .build();
        } else {
            return pidPropertyBuilder
                .addP(0)
                .addI(0)
                .addD(0)
                // .addFF(0)
                .addMaxAcceleration(0)
                .addMaxVelocity(0)
                .build();
        }
    }

    private PidProperty setupPidValuesRetract(ProfiledPIDController pidController) {
        WpiProfiledPidPropertyBuilder pidPropertyBuilder = new WpiProfiledPidPropertyBuilder("WPI Pivot Arm RETRACT", false, pidController);

        ///
        // Full retract:
        // kp=0.000400
        // kf=0.005000
        // kd=0.005000
        if (Constants.IS_ROBOT_BLOSSOM) {
            return pidPropertyBuilder
                .addP(0)
                .addI(0)
                .addD(0)
                // .addFF(0)
                .addMaxAcceleration(180)
                .addMaxVelocity(100)
                .build();
        } else {
            return pidPropertyBuilder
                .addP(0)
                .addI(0)
                .addD(0)
                // .addFF(0)
                .addMaxAcceleration(0)
                .addMaxVelocity(0)
                .build();
        }
    }

    @Override
    public void periodic() {
        m_midPidProperty.updateIfChanged();
        m_midPidFeedForward.updateIfChanged();

        m_retractedPidProperty.updateIfChanged();
        m_retractedPidFeedForward.updateIfChanged();

        m_lowerLimitSwitchEntry.setBoolean(isLowerLimitSwitchedPressed());
        m_upperLimitSwitchEntry.setBoolean(isUpperLimitSwitchedPressed());
        m_encoderDegEntry.setNumber(getArmAngleDeg());
        m_absoluteEncoderEntry.setNumber(getAbsoluteEncoderAngle());
        m_absoluteEncoderToBuiltInDriftEntry.setNumber(getAbsoluteEncoderAngle() - getArmAngleDeg());
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

    public final double getArmAngleDeg() {
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

        ProfiledPIDController controller;
        ArmFeedForwardProperty feedForwardProperty;

        ArmExtensionSubsystem.ArmExtension currentExtension = m_armExtensionSupplier.get();
        switch (currentExtension) {
        case FULL_RETRACT:
            controller = m_retractedPivotPidController;
            feedForwardProperty = m_retractedPidFeedForward;
            break;
        case MIDDLE_RETRACT:
        default:
            controller = m_midPivotPidController;
            feedForwardProperty = m_midPidFeedForward;
            break;
        }

        if (controller.getSetpoint().equals(controller.getGoal())) {
            System.out.println("GOAL IS AT SETPOINT");
            // resetWpiPidController();
        }

        double currentAngleDeg = getAbsoluteEncoderAngle();

        double error = m_armAngleGoal - currentAngleDeg;

        System.out.println("Running");
        SmartDashboard.putNumber("arm angle error", error);

        // if (Math.abs(error) > ALLOWABLE_ERROR.getValue() && isArmPIDTrapezoidFinished()) {
        //     resetWpiPidController();
        // }

        double volts = controller.calculate(currentAngleDeg, pivotAngleGoal);

        TrapezoidProfile.State setpointDegrees = controller.getSetpoint();
        m_velocityGoalEntry.setNumber(setpointDegrees.velocity);

        double feedForward = feedForwardProperty.calculate(
            Units.degreesToRadians(setpointDegrees.position),
            Units.degreesToRadians(setpointDegrees.velocity),
            Units.degreesToRadians(setpointDegrees.velocity - getAbsoluteEncoderVelocity())
        );
        m_pivotMotor.setVoltage(volts + feedForward);

    }

    public boolean isArmAtAngle() {
        return isArmAtAngle(m_armAngleGoal);
    }

    public boolean isArmAtAngle(double pivotAngleGoal) {
        return isArmAtAngle(pivotAngleGoal, ALLOWABLE_ERROR.getValue(), ALLOWABLE_VELOCITY_ERROR.getValue());
    }

    public boolean isArmAtAngle(double pivotAngleGoal, double allowableError, double allowableVelocityError) {
        double error = getAbsoluteEncoderAngle() - pivotAngleGoal;
        double velocity = getArmVelocityDegPerSec();

        return Math.abs(error) <= allowableError && Math.abs(velocity) < allowableVelocityError;
    }

    public boolean isArmPIDTrapezoidFinished() {
        ProfiledPIDController controller;

        ArmExtensionSubsystem.ArmExtension currentExtension = m_armExtensionSupplier.get();
        switch (currentExtension) {
        case FULL_RETRACT:
            controller = m_retractedPivotPidController;
            break;
        case MIDDLE_RETRACT:
        default:
            controller = m_midPivotPidController;
            break;
        }

        return controller.getSetpoint().equals(controller.getGoal());

    }

    public void clearStickyFaultsArmPivot() {
        m_pivotMotor.clearFaults();
    }


    public final void resetPivotEncoder(double angle) {
        SparkMaxUtil.autoRetry(() -> m_pivotMotorEncoder.setPosition(angle));
    }

    public static double getArmAngleForScoring(AutoPivotHeight pivotHeightType, GamePieceType gamePieceType) {
        double angle;

        switch (pivotHeightType) {
        case HIGH:
            if (gamePieceType == GamePieceType.CONE) {
                angle = ARM_CONE_HIGH_DEG;
            } else {
                angle = ARM_CUBE_HIGH_DEG;
            }
            break;
        case MEDIUM:
            if (gamePieceType == GamePieceType.CONE) {
                angle = ARM_CONE_MIDDLE_DEG;
            } else {
                angle = ARM_CUBE_MIDDLE_DEG;
            }
            break;
        case LOW:
        default:
            angle = ARM_SCORE_LOW_DEG;
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

    public CommandBase createSyncEncoderToAbsoluteEncoder() {
        return this.run(this::syncMotorEncoderToAbsoluteEncoder)
            .ignoringDisable(true)
            .withName("Reset Pivot Encoder (Abs Encoder Val)");
    }

    public CommandBase tuneGravityOffsetPID() {
        return this.runEnd(this::tuneGravityOffset, this::pivotArmStop).withName("Tune Gravity Offset");
    }

    public CommandBase commandPivotArmUp() {
        return this.runEnd(this::pivotArmUp, this::pivotArmStop).withName("Arm: Pivot Down");
    }

    public CommandBase commandPivotArmDown() {
        return this.runEnd(this::pivotArmDown, this::pivotArmStop).withName("Arm: Pivot Up");
    }

    private void resetWpiPidController() {
        m_midPivotPidController.reset(getAbsoluteEncoderAngle(), getAbsoluteEncoderVelocity());
        m_retractedPivotPidController.reset(getAbsoluteEncoderAngle(), getAbsoluteEncoderVelocity());
    }

    private CommandBase createResetWpiPidController() {
        return runOnce(this::resetWpiPidController);
    }

    public CommandBase commandPivotArmToAngleHold(double angle) {
        //Command secondRun = new PrintCommand("Doing second run").andThen(createResetPivotEncoder()).andThen(this.run(() -> pivotArmToAngle(angle)).until(() -> isArmAtAngle(angle)));
        return createResetWpiPidController()
            .andThen(this.run(() -> pivotArmToAngle(angle)).until(this::isArmPIDTrapezoidFinished)
            //.andThen(secondRun.unless(this::isArmAtAngle))
            .withName("Arm to Angle And Hold" + angle));
    }

    public CommandBase commandPivotArmToAngleHold(double angle, double allowableError, double velocityAllowableError) {
        return createResetWpiPidController().andThen(this.run(() -> pivotArmToAngle(angle))
            .until(() -> isArmAtAngle(angle, allowableError, velocityAllowableError))
            .withName("Arm to Angle And Hold" + angle));
    }

    public CommandBase commandPivotArmToAngleNonHold(double angle) {
        return runOnce(this::resetWpiPidController)
            .andThen(this.runEnd(() -> pivotArmToAngle(angle), this::pivotArmStop)
            .until(() -> isArmAtAngle(angle))
            .withName("Arm to Angle" + angle));
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

    public CommandBase commandGoToGroundPickup() {
        return commandPivotArmToAngleNonHold(GROUND_PICKUP_ANGLE);
    }

    public CommandBase commandGoToGroundPickupAndHold() {
        return commandPivotArmToAngleHold(GROUND_PICKUP_ANGLE);
    }

    public CommandBase commandGoToGroundPickupAndHold(double allowableError, double velocityAllowableError) {
        return commandPivotArmToAngleHold(GROUND_PICKUP_ANGLE, allowableError, velocityAllowableError);
    }

    public CommandBase commandHpPickupNoHold() {
        return commandPivotArmToAngleNonHold(HUMAN_PLAYER_ANGLE);
    }

    public CommandBase commandHpPickupHold() {
        return commandPivotArmToAngleHold(HUMAN_PLAYER_ANGLE);
    }

    public Command commandGoToAutoNodePosition(Supplier<AutoAimNodePositions> nodeSupplier) {
        return runEnd(() -> {
            AutoAimNodePositions position = nodeSupplier.get();
            if (position == null || position == AutoAimNodePositions.NONE) {
                return;
            }
            pivotArmToAngle(position.getTargetPitch());
        }, this::pivotArmStop);
    }

    ////////////////
    // Checklists
    ////////////////

    public CommandBase createIsPivotMotorMoving() {
        return new SparkMaxMotorsMoveChecklist(this, m_pivotMotor, "Arm: Pivot motor", 1.0);
    }
}

