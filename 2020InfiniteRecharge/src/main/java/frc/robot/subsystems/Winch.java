package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Winch extends SubsystemBase {

    private final CANSparkMax m_motorA;
    private final CANSparkMax m_motorB;

    public Winch() {
        m_motorA = new CANSparkMax(Constants.WINCH_A_SPARK, MotorType.kBrushless);
        m_motorA.setIdleMode(IdleMode.kBrake);
        m_motorA.setInverted(false);
        m_motorB = new CANSparkMax(Constants.WINCH_B_SPARK, MotorType.kBrushless);
        m_motorB.setIdleMode(IdleMode.kBrake);
        m_motorB.setInverted(false);
    } 

    public void wind() {
        m_motorA.set(0.8);
        m_motorB.set(0.8);
    }

    public void unwind() {
        m_motorA.set(-0.8);
        m_motorB.set(-0.8);
    }

    public void stop() {
        m_motorA.set(0);
        m_motorB.set(0);
    }

}
