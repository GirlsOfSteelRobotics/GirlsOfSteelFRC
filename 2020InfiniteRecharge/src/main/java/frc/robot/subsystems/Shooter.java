package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.lib.PropertyManager;

public class Shooter extends SubsystemBase {

    private static final double SHOOTER_KP = 0.000203;
    private static final double SHOOTER_KFF = 0.000091; //1 / 10600.0;

    private static final double ALLOWABLE_ERROR_PERCENT = 1;          


    private final CANSparkMax m_master;
    private final CANSparkMax m_follower;
    private final CANEncoder m_encoder;
    private CANPIDController m_pidController;

    private double m_goalRPM; 
    
    private final NetworkTable m_customNetworkTable;

    private final PropertyManager.IProperty<Double> m_dashboardKp;
    private final PropertyManager.IProperty<Double> m_dashboardKff;

    public Shooter() {
        m_master = new CANSparkMax(Constants.SHOOTER_SPARK_A, MotorType.kBrushed);
        m_follower = new CANSparkMax(Constants.SHOOTER_SPARK_B, MotorType.kBrushed);
        m_encoder  = m_master.getEncoder(EncoderType.kQuadrature, 4096);
        m_pidController = m_master.getPIDController();
        
        m_dashboardKp = new PropertyManager.DoubleProperty("shooter_kp", SHOOTER_KP);
        m_dashboardKff = new PropertyManager.DoubleProperty("shooter_kff", SHOOTER_KFF);
        
        m_master.restoreFactoryDefaults();

        m_encoder.setInverted(true);

        m_master.setSmartCurrentLimit(Constants.SPARK_MAX_CURRENT_LIMIT);
        m_master.setInverted(false);
        m_follower.follow(m_master, true);

        m_pidController.setP(SHOOTER_KP);
        m_pidController.setFF(SHOOTER_KFF);
        
        m_customNetworkTable = NetworkTableInstance.getDefault().getTable("SuperStructure/Shooter");
        NetworkTableInstance.getDefault().getTable("SuperStructure").getEntry(".type").setString("SuperStructure");
    } 

    
    public void setRPM(final double rpm) {
        m_goalRPM = rpm; 
        m_pidController.setReference(rpm, ControlType.kVelocity);
        // double targetVelocityUnitsPer100ms = rpm * 4096 / 600;
        // m_master.set(1.00 /*targetVelocityUnitsPer100ms*/);
    }

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
//        System.out.println("kp: " + m_dashboardKp.getValue() + ", " + m_dashboardKff.getValue() + " goal: " + m_goalRPM + "== " + rpm);

    }

    public boolean isAtFullSpeed() {
        double currentRPM = m_encoder.getVelocity();
        double percentError = (m_goalRPM - currentRPM) / m_goalRPM * 100;
        return Math.abs(percentError) <= ALLOWABLE_ERROR_PERCENT;
    }

    public void stop() {
        m_master.set(0);
        //m_pidController.setReference(0, ControlType.kVelocity);
    }
}
