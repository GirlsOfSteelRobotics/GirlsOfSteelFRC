package com.gos.crescendo2024.subsystems;

import com.gos.crescendo2024.Constants;
import com.gos.crescendo2024.subsystems.sysid.ShooterSysId;
import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.lib.rev.properties.pid.RevPidPropertyBuilder;
import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.FlywheelSimWrapper;
import org.snobotv2.sim_wrappers.ISimWrapper;

public class ShooterSubsystem extends SubsystemBase {

    public static final GosDoubleProperty DEFAULT_SHOOTER_RPM = new GosDoubleProperty(false, "ShooterDefaultRpm", 4000);
    private static final GosDoubleProperty SHOOTER_SPEED = new GosDoubleProperty(false, "ShooterSpeed", 0.5);
    private static final double ALLOWABLE_ERROR = 70;

    private final SimableCANSparkMax m_shooterMotorLeader;
    private final SimableCANSparkMax m_shooterMotorFollower;
    private final SparkMaxAlerts m_shooterMotorErrorAlerts;
    private final RelativeEncoder m_shooterEncoder;
    private final SparkPIDController m_pidController;
    private final PidProperty m_pidProperties;
    private final LoggingUtil m_networkTableEntries;
    private ISimWrapper m_shooterSimulator;
    private double m_shooterGoalRPM;
    private final ShooterSysId m_shooterSysId;

    public ShooterSubsystem() {
        m_shooterMotorLeader = new SimableCANSparkMax(Constants.SHOOTER_MOTOR_LEADER, CANSparkLowLevel.MotorType.kBrushless);
        m_shooterMotorLeader.restoreFactoryDefaults();
        m_shooterMotorLeader.setInverted(false);
        m_shooterEncoder = m_shooterMotorLeader.getEncoder();
        m_pidController = m_shooterMotorLeader.getPIDController();
        m_pidProperties = new RevPidPropertyBuilder("Shooter", false, m_pidController, 0)
            .addP(0.0002)
            .addI(0.0)
            .addD(0.0)
            .addFF(0.000174)
            .build();

        m_shooterMotorLeader.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_shooterMotorLeader.setSmartCurrentLimit(60);
        m_shooterMotorLeader.burnFlash();

        m_shooterMotorFollower = new SimableCANSparkMax(Constants.SHOOTER_MOTOR_FOLLOWER, CANSparkLowLevel.MotorType.kBrushless);
        m_shooterMotorFollower.restoreFactoryDefaults();
        m_shooterMotorFollower.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_shooterMotorFollower.setSmartCurrentLimit(60);
        m_shooterMotorFollower.follow(m_shooterMotorLeader, true);
        m_shooterMotorFollower.burnFlash();

        m_shooterMotorErrorAlerts = new SparkMaxAlerts(m_shooterMotorLeader, "shooter motor");

        m_networkTableEntries = new LoggingUtil("Shooter Subsystem");
        m_networkTableEntries.addDouble("Current Amps", m_shooterMotorLeader::getOutputCurrent);
        m_networkTableEntries.addDouble("Output", m_shooterMotorLeader::getAppliedOutput);
        m_networkTableEntries.addDouble("Velocity (RPM)", m_shooterEncoder::getVelocity);
        m_networkTableEntries.addBoolean("Shooter At Goal", this::isShooterAtGoal);

        if (RobotBase.isSimulation()) {
            FlywheelSim shooterFlywheelSim = new FlywheelSim(DCMotor.getNeo550(2), 1.0, 0.01);
            this.m_shooterSimulator = new FlywheelSimWrapper(shooterFlywheelSim, new RevMotorControllerSimWrapper(this.m_shooterMotorLeader), RevEncoderSimWrapper.create(this.m_shooterMotorLeader));
        }

        m_shooterSysId = new ShooterSysId(this);

    }

    @Override
    public void periodic() {
        m_shooterMotorErrorAlerts.checkAlerts();
        m_networkTableEntries.updateLogs();
        m_pidProperties.updateIfChanged();

    }

    @Override
    public void simulationPeriodic() {
        this.m_shooterSimulator.update();
    }

    public void tuneShootPercentage() {
        m_shooterMotorLeader.set(SHOOTER_SPEED.getValue());
    }

    public void stopShooter() {
        m_shooterMotorLeader.set(0);
    }

    public void setPidRpm(double rpm) {
        this.m_pidController.setReference(rpm, CANSparkBase.ControlType.kVelocity);
        m_shooterGoalRPM = rpm;
    }

    public void setVoltageLeadAndFollow(double outputVolts) {
        m_shooterMotorLeader.setVoltage(outputVolts);
        m_shooterMotorFollower.setVoltage(outputVolts);
    }

    public double getRPM() {
        return m_shooterEncoder.getVelocity();
    }

    public double getShooterMotorPercentage() {
        return m_shooterMotorLeader.getAppliedOutput();
    }

    public double replaceVoltage() {
        return m_shooterMotorLeader.get() * RobotController.getBatteryVoltage();
    }

    public double getEncoderPos() {
        return m_shooterEncoder.getPosition();
    }

    public double getEncoderVel() {
        return m_shooterEncoder.getVelocity();
    }

    public boolean isShooterAtGoal() {
        double error = m_shooterGoalRPM - getRPM();
        return Math.abs(error) < ALLOWABLE_ERROR;
    }


    /////////////////////////////////////
    // Command Factories
    /////////////////////////////////////
    public Command createTunePercentShootCommand() {
        return this.runEnd(this::tuneShootPercentage, this::stopShooter).withName("TuneShooterPercentage");
    }

    public Command createSetRPMCommand(double rpm) {
        return this.runEnd(() -> this.setPidRpm(rpm), this::stopShooter).withName("set shooter rpm " + rpm);
    }

    public Command createRunDefaultRpmCommand() {
        return this.runEnd(() -> this.setPidRpm(DEFAULT_SHOOTER_RPM.getValue()), this::stopShooter).withName("set shooter default rpm");
    }

    public Command createStopShooterCommand() {
        return this.run(this::stopShooter).withName("stop shooter");
    }


}
