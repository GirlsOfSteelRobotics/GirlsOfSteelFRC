package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

    private final CANSparkMax m_motor;

    private final CANEncoder m_encoder;

    private CANPIDController m_pidController;

	public Shooter () {
        m_motor = new CANSparkMax(Constants.SHOOTER_SPARK, MotorType.kBrushless);
        m_encoder  = m_motor.getEncoder();
        m_pidController = m_motor.getPIDController();

        m_pidController.setP(0.1);
        m_pidController.setFF(0.00139);
    } 
    
    public void setRPM(final double rpm) {
        m_pidController.setReference(rpm, ControlType.kVelocity);
    }

    public void periodic(){
        SmartDashboard.putNumber("RPM", m_encoder.getVelocity());
    }

    public void stop(){
        m_motor.set(0);
        m_pidController.setReference(0, ControlType.kVelocity);
    }
}