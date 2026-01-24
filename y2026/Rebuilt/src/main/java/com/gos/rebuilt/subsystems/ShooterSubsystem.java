package com.gos.rebuilt.subsystems;

import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.rebuilt.Constants;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.FlywheelSimWrapper;
import org.snobotv2.sim_wrappers.ISimWrapper;

import java.util.function.DoubleSupplier;

public class ShooterSubsystem extends SubsystemBase {

    private final SparkFlex m_shooterMotor;
    private final RelativeEncoder m_motorEncoder;
    private final LoggingUtil m_networkTableEntries;
    private final GosDoubleProperty m_shooterSpeed = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "shooterSpeed", 1);
    private final GosDoubleProperty m_feedForward = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "ShooterKf", 1);
    private final GosDoubleProperty m_kp = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "ShooterKp", 1);
    private final GosDoubleProperty m_tuneRpm = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "tuneRPM", 1);

    private ISimWrapper m_shooterSimulator;
    private final InterpolatingDoubleTreeMap m_table = new InterpolatingDoubleTreeMap();


    public ShooterSubsystem() {
        m_shooterMotor = new SparkFlex(Constants.SHOOTER_MOTOR, MotorType.kBrushless);
        m_motorEncoder = m_shooterMotor.getEncoder();
        m_networkTableEntries = new LoggingUtil("Shooter Subsystem");

        m_table.put(6.14, 1550.0);
        m_table.put(2.85, 165.0);
        m_table.put(3.55, 1750.0);



        m_networkTableEntries.addDouble("Shooter rpm", this::getRPM);

        if (RobotBase.isSimulation()) {
            DCMotor gearbox = DCMotor.getNeo550(2);
            LinearSystem<N1, N1, N1> plant =
                LinearSystemId.createFlywheelSystem(gearbox, 0.01, 1.0);
            FlywheelSim shooterFlywheelSim = new FlywheelSim(plant, gearbox);
            this.m_shooterSimulator = new FlywheelSimWrapper(
                shooterFlywheelSim,
                new RevMotorControllerSimWrapper(this.m_shooterMotor, gearbox),
                RevEncoderSimWrapper.create(this.m_shooterMotor));
        }

    }

    public void spinMotorForward() {
        m_shooterMotor.set(m_shooterSpeed.getValue());
    }


    public void spinMotorForward(double pow) {
        m_shooterMotor.set(pow);
    }

    public double getRPM() {
        return m_motorEncoder.getVelocity();
    }

    public double getLaunchSpeed() {
        return m_motorEncoder.getVelocity() * 2 * Math.PI * Units.inchesToMeters(2) / 60 * .75;
    }

    public void setRPM(double goal) {

        double error = goal - getRPM();
        m_shooterMotor.set(m_feedForward.getValue() * goal + m_kp.getValue() * error);

    }

    public void shootFromDistance(double distance){
        double rpm = m_table.get(distance);
        setRPM(rpm);
    }


    public void spinMotorBackward() {
        m_shooterMotor.set(-m_shooterSpeed.getValue());
    }

    public void stop() {
        m_shooterMotor.stopMotor();
    }

    @Override
    public void periodic() {
        m_networkTableEntries.updateLogs();
    }

    public void addShooterDebugCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("Shooter");
        tab.add(createShooterSpinMotorForwardCommand());
        tab.add(createShooterSpinMotorBackwardCommand());
        tab.add(createShooterSpin2000());
        tab.add(createShooterSpin1500());
        tab.add(createtuneRPM());

    }

    public Command createShooterSpinMotorForwardCommand() {
        return runEnd(this::spinMotorForward, this::stop).withName("Shooter Forward! :)");
    }

    public Command createShooterSpinMotorBackwardCommand() {
        return runEnd(this::spinMotorBackward, this::stop).withName("Shooter Backward! :(");
    }

    public Command createShooterSpin1500() {
        return runEnd(() -> setRPM(1500), this::stop).withName("Shooter spins to 1500!!");
    }

    public Command createShooterSpin2000() {
        return runEnd(() -> setRPM(2000), this::stop).withName("Shooter spins to 2000!!");
    }
    public Command createtuneRPM() {
        return runEnd(() -> setRPM(m_tuneRpm.getValue()), this::stop).withName("Shooter spins to tuneRPM!!");
    }
    public Command createShootFromDistanceCommand(DoubleSupplier distanceGetter) {
        return runEnd(() -> shootFromDistance(distanceGetter.getAsDouble()), this::stop).withName("Shoot from distance");
    }


    @Override
    public void simulationPeriodic() {
        m_shooterSimulator.update();
    }


}

