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

public class Shooter extends SubsystemBase {

    private static final double SHOOTER_KP = 0.1;
    private static final double SHOOTER_KFF = 0.00139;

    private static final double ALLOWABLE_ERROR_PERCENT = 1;          

    private static final int SLOT_ID = 0;

    private final CANSparkMax m_master;
    private final CANSparkMax m_follower;
    private final CANEncoder m_encoder;
    private CANPIDController m_pidController;

    private double m_goalRPM; 
    
    private final NetworkTable m_customNetworkTable;

    public Shooter() {
        m_master = new CANSparkMax(Constants.SHOOTER_SPARK_A, MotorType.kBrushed);
        m_follower = new CANSparkMax(Constants.SHOOTER_SPARK_B, MotorType.kBrushed);
        m_encoder  = m_master.getEncoder(EncoderType.kQuadrature, 4096);
        m_pidController = m_master.getPIDController();
 
        m_master.restoreFactoryDefaults();

        m_master.setSmartCurrentLimit(Constants.SPARK_MAX_CURRENT_LIMIT);

        m_follower.follow(m_master, true);

        m_master.setInverted(false);

        m_pidController.setP(SHOOTER_KP);
        m_pidController.setFF(SHOOTER_KFF);
        
        m_customNetworkTable = NetworkTableInstance.getDefault().getTable("SuperStructure/Shooter");
        NetworkTableInstance.getDefault().getTable("SuperStructure").getEntry(".type").setString("SuperStructure");
    } 

    
    public void setRPM(final double rpm) {
        m_goalRPM = rpm; 
        //m_pidController.setReference(rpm, ControlType.kVelocity);
        double targetVelocityUnitsPer100ms = rpm * 4096 / 600;
        m_master.set(1.00 /*targetVelocityUnitsPer100ms*/);
    }

    @Override
    public void periodic() {
        double rpm = m_encoder.getVelocity() * 600.0 / 4096;
        SmartDashboard.putNumber("RPM", rpm);
        m_customNetworkTable.getEntry("Speed").setDouble(m_master.get());
        m_customNetworkTable.getEntry("Current RPM").setDouble(rpm);
        m_customNetworkTable.getEntry("Goal RPM").setDouble(m_goalRPM);

    }

    public boolean isAtFullSpeed() {
        double currentRPM = m_encoder.getVelocity() * 600.0 / 4096;
        double percentError = (m_goalRPM - currentRPM) / m_goalRPM * 100;
        return Math.abs(percentError) <= ALLOWABLE_ERROR_PERCENT;
    }

    public void stop() {
        m_master.set(0);
        //m_pidController.setReference(0, ControlType.kVelocity);
    }
}
