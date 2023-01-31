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
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {

    public static final GosDoubleProperty ALLOWABLE_ERROR = new GosDoubleProperty(false, "Pivot Arm Allowable Error", 0);
    private static final double ARM_MOTOR_SPEED = 0.2;
    private final SimableCANSparkMax m_pivotMotor;
    private static final double GEAR_RATIO = 5.0 * 2.0 * 4.0;

    private final RelativeEncoder m_pivotMotorEncoder;
    private final SparkMaxPIDController m_pivotPIDController;

    private final Solenoid m_outerPiston;

    private final Solenoid m_innerPiston;

    private final DigitalInput m_lowerLimitSwitch;

    private final DigitalInput m_upperLimitSwitch;

    private final PidProperty m_pivotPID;

    private static final double CUBE_LOW_ANGLE = 15;
    private static final double CONE_LOW_ANGLE = 15;
    private static final double CUBE_MIDDLE_ANGLE = 30;
    private static final double CONE_MIDDLE_ANGLE = 40;
    private static final double CUBE_HIGH_ANGLE = 50;
    private static final double CONE_HIGH_ANGLE = 60;

    public static final double[] armLevel = {CUBE_LOW_ANGLE, CONE_LOW_ANGLE, CUBE_MIDDLE_ANGLE, CONE_MIDDLE_ANGLE, CUBE_HIGH_ANGLE, CONE_HIGH_ANGLE};


    public ArmSubsystem() {
        m_pivotMotor = new SimableCANSparkMax(Constants.PIVOT_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
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

    public void pivotArmUp() {
        m_pivotMotor.set(ARM_MOTOR_SPEED);
    }

    public void pivotArmDown() {
        m_pivotMotor.set(-ARM_MOTOR_SPEED);
    }

    public double getArmMotorSpeed() {
        return m_pivotMotor.get();
    }

    public void pivotArmStop() {
        m_pivotMotor.set(0);
    }

    public void fullRetract() {
        m_outerPiston.set(true);
        m_innerPiston.set(false);
        System.out.println("retract");
    }

    public boolean isInnerPistonIn() {
        return m_innerPiston.get();
    }

    public boolean isOuterPistonIn() {
        return m_outerPiston.get();
    }


    public void middleRetract() {
        m_outerPiston.set(false);
        m_innerPiston.set(false);
        System.out.println("mid");
    }

    public void out() {
        m_outerPiston.set(false);
        m_innerPiston.set(true);
        System.out.println("out");
    }

    public Command commandPivotArmUp() {
        return this.startEnd(this::pivotArmUp, this::pivotArmStop);
    }

    public Command commandPivotArmDown() {
        return this.startEnd(this::pivotArmDown, this::pivotArmStop);
    }

    public double getPosition() {
        return m_pivotMotorEncoder.getPosition();
    }

    public boolean isLowerLimitSwitchedPressed() {
        return !m_lowerLimitSwitch.get();
    }

    public boolean isUpperLimitSwitchedPressed() {
        return !m_upperLimitSwitch.get();
    }

    public boolean pivotArmToAngle(double pivotAngleGoal) {
        double error = getPosition() - pivotAngleGoal;
        if (!isLowerLimitSwitchedPressed() || !isUpperLimitSwitchedPressed()) {
            m_pivotPIDController.setReference(pivotAngleGoal, CANSparkMax.ControlType.kSmartMotion, 0);
        } else {
            m_pivotMotor.set(0);
        }

        return error <= ALLOWABLE_ERROR.getValue();
    }

    @Override
    public void periodic() {
        m_pivotPID.updateIfChanged();
    }

    public Command commandFullRetract() {
        return this. runOnce(this::fullRetract);
    }

    public Command commandMiddleRetract() {
        return this. runOnce(this::middleRetract);
    }

    public Command commandOut() {
        return this. runOnce(this::out);
    }

    public int findNodeLevel() {
        double armAngle = getPosition();
        double error = -1;
        double currentMinError = 100;
        int currentBestArrayPos = -1;
        for (int i = 0; i < 6; i++) {
            error = Math.abs(armAngle - armLevel[i]);
            if (error <= ALLOWABLE_ERROR.getValue() && error < currentMinError) {
                currentMinError = error;
                currentBestArrayPos = i;
            }
        }

        return currentBestArrayPos;
    }


}

