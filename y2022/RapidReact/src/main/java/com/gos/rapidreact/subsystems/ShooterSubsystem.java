package com.gos.rapidreact.subsystems;


import com.gos.lib.properties.PidProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.gos.rapidreact.Constants;
import com.gos.rapidreact.ShooterLookupTable;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.FlywheelSimWrapper;
import org.snobotv2.sim_wrappers.ISimWrapper;

public class ShooterSubsystem extends SubsystemBase {

    //variables for the two NEO Brushless Motors
    public static final double SHOOTER_ALLOWABLE_ERROR = 100.0;
    public static final double FENDER_RPM_LOW = 1500;
    public static final double TARMAC_EDGE_RPM_LOW = 2000;
    public static final double TARMAC_EDGE_RPM_HIGH = 3000;
    public static final double DEFAULT_SHOOTER_RPM = FENDER_RPM_LOW;
    public static final double MAX_SHOOTER_RPM = 3200;

    private static final double ROLLER_SPEED = 0.5;
    private static final double ROLLER_ALLOWABLE_ERROR = 100.0;
    private static final double ROLLER_RPM = 4000;

    private final SimableCANSparkMax m_leader;
    private final RelativeEncoder m_shooterEncoder;
    private final PidProperty m_shooterPid;
    private final SparkMaxPIDController m_shooterPidController;
    private final ShooterLookupTable m_shooterTable;
    private double m_goalRpm = Double.MAX_VALUE;

    private final SimableCANSparkMax m_roller;
    private final RelativeEncoder m_rollerEncoder;
    private final PidProperty m_rollerPid;
    private final SparkMaxPIDController m_rollerPidController;

    private ISimWrapper m_simulator;

    public ShooterSubsystem() {
        m_leader = new SimableCANSparkMax(Constants.SHOOTER_LEADER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_leader.restoreFactoryDefaults();
        m_leader.setIdleMode(CANSparkMax.IdleMode.kCoast);

        m_roller = new SimableCANSparkMax(Constants.SHOOTER_ROLLER, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_roller.restoreFactoryDefaults();
        m_roller.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_roller.setInverted(true);

        //true because the motors are facing each other and in order to do the same thing, they would have to spin in opposite directions
        m_shooterEncoder = m_leader.getEncoder();
        m_rollerEncoder = m_roller.getEncoder();

        m_shooterPidController = m_leader.getPIDController();
        m_shooterPid = new RevPidPropertyBuilder("Shooter PID", false, m_shooterPidController, 0)
            .addP(0.003)
            .addD(0.000055)
            .addFF(0.000176)
            .build();

        m_rollerPidController = m_roller.getPIDController();
        m_rollerPid = new RevPidPropertyBuilder("Roller PID", false, m_shooterPidController, 0)
            .addP(0)
            .addD(0)
            .addFF(0)
            .build();

        m_shooterTable = new ShooterLookupTable();

        m_leader.burnFlash();


        if (RobotBase.isSimulation()) {
            FlywheelSim flywheelSim = new FlywheelSim(DCMotor.getNeo550(2), 1, 0.01);
            m_simulator = new FlywheelSimWrapper(flywheelSim,
                new RevMotorControllerSimWrapper(m_leader),
                RevEncoderSimWrapper.create(m_leader));
        }
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("RPM", getEncoderVelocity());
        //        SmartDashboard.putNumber("Shooter Encoder", m_encoder.getPosition());
        m_shooterPid.updateIfChanged();
        m_rollerPid.updateIfChanged();
    }

    public void setShooterRpmPIDSpeed(double rpm) {
        m_shooterPidController.setReference(rpm, CANSparkMax.ControlType.kVelocity);
        m_goalRpm = rpm;
    }

    public boolean isShooterAtSpeed() {
        double error = Math.abs(m_goalRpm - getEncoderVelocity());
        return error < SHOOTER_ALLOWABLE_ERROR;
    }

    public void forwardRoller() {
        m_roller.set(ROLLER_SPEED);
    }

    public void backwardRoller() {
        m_roller.set(-ROLLER_SPEED);
    }

    public void stopRoller() {
        m_roller.set(0);
    }

    public void rollerPID() {
        m_rollerPidController.setReference(ROLLER_RPM, CANSparkMax.ControlType.kVelocity);
    }

    public boolean isRollerAtSpeed() {
        double error = Math.abs(ROLLER_RPM - getRollerVelocity());
        return error < ROLLER_ALLOWABLE_ERROR;
    }

    public double getRollerVelocity() {
        return m_rollerEncoder.getVelocity();
    }

    public void setShooterSpeed(double speed) {
        m_leader.set(speed);
        m_goalRpm = Double.MAX_VALUE;
    }

    public double getEncoderVelocity() {
        return m_shooterEncoder.getVelocity();
    }

    public double getShooterSpeed() {
        return m_leader.getAppliedOutput();
    }

    public void rpmForDistance(double distance) {
        setShooterRpmPIDSpeed(m_shooterTable.getVelocityTable(distance));
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();

    }

}

