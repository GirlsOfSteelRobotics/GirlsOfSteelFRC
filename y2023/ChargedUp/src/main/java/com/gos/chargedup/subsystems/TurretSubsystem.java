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

    // y layout of field for turret auto pivoting
    public static final int nodeRowCount = 9;
    public static final Translation2d[] lowTranslations = new Translation2d[nodeRowCount];
    public static final Translation2d[] midTranslations = new Translation2d[nodeRowCount];
    public static final Translation3d[] mid3dTranslations = new Translation3d[nodeRowCount];
    public static final double nodeFirstY = Units.inchesToMeters(20.19);
    public static final double nodeSeparationY = Units.inchesToMeters(22.0);

    //additional turret auto variables
    public static final double cubeEdgeHigh = Units.inchesToMeters(3.0);
    public static final double highCubeZ = Units.inchesToMeters(35.5) - cubeEdgeHigh;
    public static final double midCubeZ = Units.inchesToMeters(23.5) - cubeEdgeHigh;
    public static final double highConeZ = Units.inchesToMeters(46.0);
    public static final double midConeZ = Units.inchesToMeters(34.0);
    public static final Translation2d[] highTranslations = new Translation2d[nodeRowCount];
    public static final Translation3d[] high3dTranslations = new Translation3d[nodeRowCount];


    // x layout of the field for turret auto pivoting
    public static final double outerX = Units.inchesToMeters(193.25);
    public static final double lowX = outerX - (Units.inchesToMeters(14.25) / 2.0);
    public static final double midX = outerX - Units.inchesToMeters(22.75);
    public static final double highX = outerX - Units.inchesToMeters(39.75);

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

    static {
        for (int i = 0; i < nodeRowCount; i++) {
            boolean isCube = i == 1 || i == 4 || i == 7;
            lowTranslations[i] = new Translation2d(lowX, nodeFirstY + nodeSeparationY * i);
            midTranslations[i] = new Translation2d(midX, nodeFirstY + nodeSeparationY * i);
            mid3dTranslations[i] =
                new Translation3d(midX, nodeFirstY + nodeSeparationY * i, isCube ? midCubeZ : midConeZ);
            high3dTranslations[i] =
                new Translation3d(
                    highX, nodeFirstY + nodeSeparationY * i, isCube ? highCubeZ : highConeZ);
            highTranslations[i] = new Translation2d(highX, nodeFirstY + nodeSeparationY * i);
        }
    }

}

