package com.gos.infinite_recharge.subsystems;

import com.gos.infinite_recharge.Constants;
import com.gos.lib.properties.GosDoubleProperty;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.FlywheelSimWrapper;
import org.snobotv2.sim_wrappers.ISimWrapper;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {

    private static final double SHOOTER_KP = 0.0005;
    private static final double SHOOTER_KFF = 0.000087; //1 / 10600.0;
    private static final double SHOOTER_KD = 0.0001;

    private static final double ALLOWABLE_ERROR_PERCENT = 1;


    private final SimableCANSparkMax m_master;
    private final SimableCANSparkMax m_follower;
    private final RelativeEncoder m_encoder;
    private final SparkMaxPIDController m_pidController;

    private final Limelight m_limelight;

    private double m_goalRPM;

    private final NetworkTable m_customNetworkTable;

    private final GosDoubleProperty m_dashboardKp;
    private final GosDoubleProperty m_dashboardKff;

    private final GenericEntry m_isAtShooterSpeedEntry;

    private ISimWrapper m_simulator;

    public Shooter(ShuffleboardTab driveDisplayTab, Limelight limelight) {
        m_master = new SimableCANSparkMax(Constants.SHOOTER_SPARK_A, MotorType.kBrushed);
        m_follower = new SimableCANSparkMax(Constants.SHOOTER_SPARK_B, MotorType.kBrushed);
        m_encoder  = m_master.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature, 8192);
        m_pidController = m_master.getPIDController();

        m_master.restoreFactoryDefaults();
        m_follower.restoreFactoryDefaults();

        m_dashboardKp = new GosDoubleProperty(false, "shooter_kp", SHOOTER_KP);
        m_dashboardKff = new GosDoubleProperty(false, "shooter_kff", SHOOTER_KFF);

        m_limelight = limelight;


        m_encoder.setInverted(true);

        m_master.setSmartCurrentLimit(Constants.SPARK_MAX_CURRENT_LIMIT);
        m_master.setInverted(false);
        m_follower.follow(m_master, true);

        m_pidController.setP(SHOOTER_KP);
        m_pidController.setFF(SHOOTER_KFF);
        m_pidController.setD(SHOOTER_KD);

        m_master.burnFlash();
        m_follower.burnFlash();

        m_customNetworkTable = NetworkTableInstance.getDefault().getTable("SuperStructure/Shooter");
        NetworkTableInstance.getDefault().getTable("SuperStructure").getEntry(".type").setString("SuperStructure");

        m_isAtShooterSpeedEntry = driveDisplayTab.add("Shooter At Speed", isAtFullSpeed()) // NOPMD
            .withSize(4, 1)
            .withPosition(0, 0)
            .getEntry();

        if (RobotBase.isSimulation()) {

            FlywheelSim flywheelSim = new FlywheelSim(DCMotor.getVex775Pro(2), 1.66, .008);
            m_simulator = new FlywheelSimWrapper(flywheelSim,
                    new RevMotorControllerSimWrapper(m_master),
                    RevEncoderSimWrapper.create(m_master));
        }
    }


    public void setRPM(final double rpm) {
        m_goalRPM = rpm;
        m_pidController.setReference(rpm, CANSparkMax.ControlType.kVelocity);
        //double targetVelocityUnitsPer100ms = rpm * 4096 / 600;
        //m_master.set(1.00 /*targetVelocityUnitsPer100ms*/);
        m_limelight.turnLimelightOn();
    }

    // public void Limelight() {
    //     m_limelight - limelight

    @Override
    public void periodic() {
        double rpm = m_encoder.getVelocity();
        SmartDashboard.putNumber("RPM", rpm);
        SmartDashboard.putNumber("Encoder Position", m_encoder.getPosition());
        m_customNetworkTable.getEntry("Speed").setDouble(m_master.get());
        m_customNetworkTable.getEntry("Current RPM").setDouble(rpm);
        m_customNetworkTable.getEntry("Goal RPM").setDouble(m_goalRPM);

        m_pidController.setP(m_dashboardKp.getValue());
        m_pidController.setFF(m_dashboardKff.getValue());
        // System.out.println("kp: " + m_dashboardKp.getValue() + ", " + m_dashboardKff.getValue() + " goal: " + m_goalRPM + "== " + rpm);

        m_isAtShooterSpeedEntry.setBoolean(isAtFullSpeed());
    }

    public boolean isAtFullSpeed() {
        double currentRPM = m_encoder.getVelocity();
        double percentError = (m_goalRPM - currentRPM) / m_goalRPM * 100;
        return Math.abs(percentError) <= ALLOWABLE_ERROR_PERCENT;
    }

    public void stop() {
        m_master.set(0);
        m_limelight.turnLimelightOff();
        //m_pidController.setReference(0, CANSparkMax.ControlType.kVelocity);
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }
}
