package com.gos.codelabs.basic_simulator.subsystems;

import com.gos.codelabs.basic_simulator.Constants;
import com.gos.codelabs.basic_simulator.commands.ShooterRpmCommand;
import com.gos.lib.logging.LoggingUtil;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.FlywheelSimWrapper;

public class ShooterSubsystem extends SubsystemBase implements AutoCloseable {
    public static final double SHOOTING_RPM = 3000;
    public static final double ALLOWABLE_RPM_ERROR = 100;

    private final SparkMax m_wheelMotor;
    private final RelativeEncoder m_wheelEncoder;
    private final LoggingUtil m_loggingUtil;

    private FlywheelSimWrapper m_simulator;

    private static final class FlywheelSimConstants {
        public static final DCMotor K_GEARBOX = DCMotor.getNEO(1);
        public static final double K_GEARING = 1;
        public static final double K_MOMENT_OF_INERTIA = 0.004; // kg * m^2
    }

    public ShooterSubsystem() {
        m_wheelMotor = new SparkMax(Constants.CAN_SHOOTER_MOTOR, MotorType.kBrushless);
        m_wheelEncoder = m_wheelMotor.getEncoder();

        m_loggingUtil = new LoggingUtil("Shooter");
        m_loggingUtil.addDouble("RPM", this::getRpm);
        m_loggingUtil.addDouble("Throttle", m_wheelMotor::getAppliedOutput);

        if (RobotBase.isSimulation()) {
            FlywheelSim sim = new FlywheelSim(
                    LinearSystemId.createFlywheelSystem(FlywheelSimConstants.K_GEARBOX, FlywheelSimConstants.K_MOMENT_OF_INERTIA, FlywheelSimConstants.K_GEARING),
                    FlywheelSimConstants.K_GEARBOX);
            m_simulator = new FlywheelSimWrapper(sim,
                    new RevMotorControllerSimWrapper(m_wheelMotor, FlywheelSimConstants.K_GEARBOX),
                    RevEncoderSimWrapper.create(m_wheelMotor));
        }
    }

    @Override
    public void close() {
        m_wheelMotor.close();
    }

    @Override
    public void periodic() {
        m_loggingUtil.updateLogs();
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }

    /**
     * Runs the wheel at a raw throttle.
     *
     * @param throttle The motor throttle [-1, 1]
     */
    public void setThrottle(double throttle) {
        // TODO implement
    }

    /**
     * Speeds the wheel up or slows it down to reach the goal RPM. This should be called every loop.
     *
     * @param rpm The goal speed, in rotations per minute
     */
    public void spinAtRpm(double rpm) {
        // TODO implement
    }

    /**
     * @param rpm The goal speed, in rotations per minute
     * @return True if the wheel is within ALLOWABLE_RPM_ERROR of the goal
     */
    public boolean isAtRpm(double rpm) {
        // TODO implement
        return false;
    }

    /**
     * @return How fast the wheel is spinning, in rotations per minute
     */
    public double getRpm() {
        // TODO implement
        return 0;
    }

    public void stop() {
        // TODO implement
    }

    public void addShooterDebugCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("Shooter");
        tab.add(new ShooterRpmCommand(this, SHOOTING_RPM).withName("Spin At Shooting RPM"));
        tab.add(new ShooterRpmCommand(this, 1000).withName("Spin At 1000 RPM"));
    }
}
