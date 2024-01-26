package com.gos.crescendo2024.subsystems;

import com.gos.crescendo2024.Constants;
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
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.FlywheelSimWrapper;
import org.snobotv2.sim_wrappers.ISimWrapper;

public class ShooterSubsystem extends SubsystemBase {

    public static final GosDoubleProperty DEFAULT_SHOOTER_RPM = new GosDoubleProperty(false, "ShooterDefaultRpm", 500);
    public static final GosDoubleProperty SHOOTER_SPEED = new GosDoubleProperty(false, "ShooterSpeed", 0.5);
    private static final double ERROR = 50;
    private final SimableCANSparkMax m_shooterMotor;
    private final SparkMaxAlerts m_shooterMotorErrorAlerts;
    private final RelativeEncoder m_shooterEncoder;
    private final SparkPIDController m_pidController;
    private final PidProperty m_pidProperties;
    private final LoggingUtil m_networkTableEntries;
    private ISimWrapper m_shooterSimulator;
    private double m_shooterGoalRPM;
    public ShooterSubsystem() {
        m_shooterMotor = new SimableCANSparkMax(Constants.SHOOTER_MOTOR, CANSparkLowLevel.MotorType.kBrushless);
        m_shooterMotor.restoreFactoryDefaults();
        m_shooterMotor.setInverted(true);
        m_shooterEncoder = m_shooterMotor.getEncoder();
        m_pidController = m_shooterMotor.getPIDController();
        m_pidProperties = new RevPidPropertyBuilder("Shooter", false, m_pidController, 0)
            .addP(0.0)
            .addI(0.0)
            .addD(0.0)
            .addFF(4.0E-4)
            .build();

        m_shooterMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_shooterMotor.setSmartCurrentLimit(60);
        m_shooterMotor.burnFlash();
        m_shooterMotorErrorAlerts = new SparkMaxAlerts(m_shooterMotor, "shooter motor");

        m_networkTableEntries = new LoggingUtil("Shooter Subsystem");
        m_networkTableEntries.addDouble("Current Amps", m_shooterMotor::getOutputCurrent);
        m_networkTableEntries.addDouble("Output", m_shooterMotor::getAppliedOutput);
        m_networkTableEntries.addDouble("Velocity (RPM)", m_shooterEncoder::getVelocity);

        if (RobotBase.isSimulation()) {
            FlywheelSim shooterFlywheelSim = new FlywheelSim(DCMotor.getNeo550(2), 1.0, 0.01);
            this.m_shooterSimulator = new FlywheelSimWrapper(shooterFlywheelSim, new RevMotorControllerSimWrapper(this.m_shooterMotor), RevEncoderSimWrapper.create(this.m_shooterMotor));
        }
    }

    @Override
    public void periodic() {
        m_shooterMotorErrorAlerts.checkAlerts();
        m_networkTableEntries.updateLogs();
        m_pidProperties.updateIfChanged();
        SmartDashboard.putNumber("shooterRpm", this.getRPM());

    }

    @Override
    public void simulationPeriodic() {
        this.m_shooterSimulator.update();
    }

    public void tuneShootPercentage() {
        m_shooterMotor.set(SHOOTER_SPEED.getValue());
    }

    public void stopShooter() {
        m_shooterMotor.set(0);
    }

    public void setPidRpm(double rpm) {
        this.m_pidController.setReference(rpm, CANSparkBase.ControlType.kVelocity);
        m_shooterGoalRPM = rpm;
    }

    public double getRPM() {
        return m_shooterEncoder.getVelocity();
    }

    public double getShooterMotorPercentage() {
        return m_shooterMotor.getAppliedOutput();
    }

    // Command Factories

    public Command createTunePercentShootCommand() {
        return this.runEnd(this::tuneShootPercentage, this::stopShooter).withName("TuneShooterPercentage");
    }

    public Command createSetRPMCommand(double rpm) {
        return this.runEnd(() -> this.setPidRpm(rpm), this::stopShooter).withName("set shooter rpm " + rpm);
    }

    public Command createStopShooterCommand() {
        return this.run(this::stopShooter).withName("stop shooter");
    }

    public boolean isShooterAtGoal() {
        double error = m_shooterGoalRPM-getRPM();
        return Math.abs(error)<ERROR;
    }
}
