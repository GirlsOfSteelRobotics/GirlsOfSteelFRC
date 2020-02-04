package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

    private static final double SHOOTER_KP = 0.1;
    private static final double SHOOTER_KFF = 0.00139;

    private static final int SLOT_ID = 0;

    private final WPI_TalonSRX m_master;
    private final WPI_TalonSRX m_follower;

    public Shooter() {
        m_master = new WPI_TalonSRX(Constants.SHOOTER_TALON_A);
        m_follower = new WPI_TalonSRX(Constants.SHOOTER_TALON_B);

        m_follower.follow(m_master);
        

        m_master.configFactoryDefault();
        m_master.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, SLOT_ID, Constants.CTRE_TIMEOUT);

        m_master.setSensorPhase(true);
        m_master.setInverted(true);

        m_master.config_kF(SLOT_ID, SHOOTER_KFF, Constants.CTRE_TIMEOUT);
        m_master.config_kP(SLOT_ID, SHOOTER_KP, Constants.CTRE_TIMEOUT);
    } 
    
    public void setRPM(final double rpm) {
        //m_pidController.setReference(rpm, ControlType.kVelocity);
        double targetVelocityUnitsPer100ms = rpm * 4096 / 600;
        m_master.set(ControlMode.Velocity, targetVelocityUnitsPer100ms);
    }

    @Override
    public void periodic() {
        double rpm = m_master.getSelectedSensorVelocity() * 600.0 / 4096;
        SmartDashboard.putNumber("RPM", rpm);
    }

    public void stop() {
        m_master.set(ControlMode.PercentOutput, 0);
        //m_pidController.setReference(0, ControlType.kVelocity);
    }
}
