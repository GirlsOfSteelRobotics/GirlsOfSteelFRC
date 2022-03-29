package com.gos.rapidreact.subsystems;


import com.gos.lib.properties.PidProperty;
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
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.ElevatorSimWrapper;
import org.snobotv2.sim_wrappers.ISimWrapper;


public class HangerSubsystem extends SubsystemBase {
    //these constants are all not correct
    public static final int ENGAGED_RATCHET_ANGLE = 90;
    public static final int DISENGAGED_RATCHET_ANGLE = 0;
    public static final double HANGER_UP_SPEED = 1.0;
    public static final double HANGER_DOWN_SPEED = -HANGER_UP_SPEED;
    private static final double GEAR = 80;
    public static final double ALLOWABLE_ERROR = Units.inchesToMeters(5);

    private final Servo m_servo;
    private final SimableCANSparkMax m_leftHanger;
    private final SimableCANSparkMax m_rightHanger;

    private final RelativeEncoder m_leftEncoder;
    private final RelativeEncoder m_rightEncoder;

    private final PidProperty m_pid;
    private final SparkMaxPIDController m_pidController;

    private ISimWrapper m_simulator;

    private final SparkMaxLimitSwitch m_bottomLeftLimit;
    private final SparkMaxLimitSwitch m_bottomRightLimit;
    private final SparkMaxLimitSwitch m_topLeftLimit;
    private final SparkMaxLimitSwitch m_topRightLimit;

    public HangerSubsystem() {
        m_servo = new Servo(Constants.SERVO_CHANNEL);
        m_leftHanger = new SimableCANSparkMax(Constants.HANGER_LEFT_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_leftHanger.restoreFactoryDefaults();
        m_rightHanger = new SimableCANSparkMax(Constants.HANGER_RIGHT_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_rightHanger.restoreFactoryDefaults();
        m_leftEncoder = m_leftHanger.getEncoder();
        m_rightEncoder = m_rightHanger.getEncoder();

        m_leftHanger.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_rightHanger.setIdleMode(CANSparkMax.IdleMode.kBrake);

        m_leftEncoder.setPositionConversionFactor(GEAR);
        m_rightEncoder.setPositionConversionFactor(GEAR);

        m_pidController = m_leftHanger.getPIDController();

        m_pid = new RevPidPropertyBuilder("Hanger PID", false, m_pidController, 0)
            .addP(0)
            .addD(0)
            .build();

        m_leftHanger.burnFlash();
        m_rightHanger.burnFlash();

        m_bottomLeftLimit = m_leftHanger.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
        m_bottomRightLimit = m_rightHanger.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
        m_topLeftLimit = m_leftHanger.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
        m_topRightLimit = m_rightHanger.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);

        m_topRightLimit.enableLimitSwitch(false); // TODO turn back on
        m_topLeftLimit.enableLimitSwitch(false);
        m_bottomLeftLimit.enableLimitSwitch(false);
        m_bottomRightLimit.enableLimitSwitch(false);

        if (RobotBase.isSimulation()) {
            ElevatorSim elevatorSim = new ElevatorSim(DCMotor.getNeo550(2), GEAR, Units.lbsToKilograms(10), Units.inchesToMeters(2), Units.feetToMeters(0), Units.feetToMeters(4));
            m_simulator = new ElevatorSimWrapper(elevatorSim,
                new RevMotorControllerSimWrapper(m_leftHanger),
                RevEncoderSimWrapper.create(m_leftHanger));
        }
    }


    @Override
    public void periodic() {
        // SmartDashboard.putNumber("Hanger Height Encoder", getLeftHangerHeight());
        // SmartDashboard.putBoolean("Hanger bottom right LS", m_bottomRightLimit.isPressed());
        // SmartDashboard.putBoolean("Hanger bottom left LS", m_bottomLeftLimit.isPressed());
        // SmartDashboard.putBoolean("Hanger top right LS", m_topRightLimit.isPressed());
        // SmartDashboard.putBoolean("Hanger top left LS", m_topLeftLimit.isPressed());

        m_pid.updateIfChanged();
    }

    public double getLeftHangerSpeed() {
        return m_leftHanger.getAppliedOutput();
    }

    public double getLeftHangerHeight() {
        return m_leftEncoder.getPosition();
    }

    public void setLeftHangerSpeed(double speed) {
        m_leftHanger.set(speed);
    }

    public double getRightHangerSpeed() {
        return m_rightHanger.getAppliedOutput();
    }

    public double getRightHangerHeight() {
        return m_rightEncoder.getPosition();
    }

    public void setRightHangerSpeed(double speed) {
        m_rightHanger.set(speed);
    }

    public void engageRatchet() {
        m_servo.setAngle(ENGAGED_RATCHET_ANGLE);
    }

    public void disengageRatchet() {
        m_servo.setAngle(DISENGAGED_RATCHET_ANGLE);
    }

    public void setHangerUpMiddleRungPID() {
        hangerToHeight(Units.feetToMeters(4));
    }

    public void setHangerUpLowRungPID() {
        hangerToHeight(Units.feetToMeters(4));
    }

    public void setHangerDownPID() {
        hangerToHeight(Units.feetToMeters(0));
    }

    public void hangerToHeight(double hangerHeight) {
        m_pidController.setReference(hangerHeight, CANSparkMax.ControlType.kPosition, 0);
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }

    public void stop() {
        m_leftHanger.set(0);
        m_rightHanger.set(0);
    }
}



