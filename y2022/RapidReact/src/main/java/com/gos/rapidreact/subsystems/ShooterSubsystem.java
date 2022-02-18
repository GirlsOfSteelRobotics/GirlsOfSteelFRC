package com.gos.rapidreact.subsystems;


import com.gos.lib.properties.PidProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.gos.rapidreact.Constants;
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
    public static final double ALLOWABLE_ERROR = 100.0;
    private final SimableCANSparkMax m_leader;
    private final RelativeEncoder m_encoder;
    private final PidProperty m_pid;
    private final SparkMaxPIDController m_pidController;

    private ISimWrapper m_simulator;

    public ShooterSubsystem() {
        m_leader = new SimableCANSparkMax(Constants.SHOOTER_LEADER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_leader.restoreFactoryDefaults();
        m_leader.setIdleMode(CANSparkMax.IdleMode.kCoast);

        //true because the motors are facing each other and in order to do the same thing, they would have to spin in opposite directions
        m_encoder  = m_leader.getEncoder();

        m_pidController = m_leader.getPIDController();
        m_pid = new RevPidPropertyBuilder("Shooter PID", false, m_pidController, 0)
            .addP(0)
            .addD(0)
            .addFF(0)
            .build();

        if (RobotBase.isSimulation()) {
            FlywheelSim flywheelSim = new FlywheelSim(DCMotor.getNeo550(2), 1.66, 1.0);
            m_simulator = new FlywheelSimWrapper(flywheelSim,
                new RevMotorControllerSimWrapper(m_leader),
                RevEncoderSimWrapper.create(m_leader));
        }
    }

    @Override
    public void periodic() {
        double rpm = m_encoder.getVelocity();
        SmartDashboard.putNumber("RPM", rpm);
        SmartDashboard.putNumber("Shooter Encoder", m_encoder.getPosition());
        m_pid.updateIfChanged();
    }

    public void setShooterRpmPIDSpeed(double rpm) {
        m_pidController.setReference(rpm, CANSparkMax.ControlType.kVelocity);
    }

    public void setShooterSpeed(double speed) {
        m_leader.set(speed);
    }

    public double getEncoderVelocity() {
        return m_encoder.getVelocity();
    }

    public double getShooterSpeed() {
        return m_leader.getAppliedOutput();
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();

    }

}

