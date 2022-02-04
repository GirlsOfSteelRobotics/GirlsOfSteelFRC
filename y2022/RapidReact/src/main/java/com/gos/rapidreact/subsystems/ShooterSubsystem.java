package com.gos.rapidreact.subsystems;


import com.gos.rapidreact.Constants;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkMaxRelativeEncoder;
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
    private final SimableCANSparkMax m_leader;
    private final SimableCANSparkMax m_follower;
    private final RelativeEncoder m_encoder;

    private ISimWrapper m_simulator;

    public ShooterSubsystem() {
        m_leader = new SimableCANSparkMax(Constants.SHOOTER_LEADER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_follower = new SimableCANSparkMax(Constants.SHOOTER_FOLLOWER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);

        //true because the motors are facing each other and in order to do the same thing, they would have to spin in opposite directions
        m_follower.follow(m_leader, true);

        m_encoder  = m_leader.getEncoder();

        if (RobotBase.isSimulation()) {

            FlywheelSim flywheelSim = new FlywheelSim(DCMotor.getNeo550(2), 1.66, .008);
            m_simulator = new FlywheelSimWrapper(flywheelSim,
                new RevMotorControllerSimWrapper(m_leader),
                RevEncoderSimWrapper.create(m_leader));
        }
    }

    public void setShooterSpeed(double speed) {
        m_leader.set(speed);
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();

    }

    public void periodic() {
        double rpm = m_encoder.getVelocity();
        SmartDashboard.putNumber("RPM", rpm);
        SmartDashboard.putNumber("Encoder Position", m_encoder.getPosition());
    }
}

