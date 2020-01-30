package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterConveyor extends SubsystemBase {

    private final CANSparkMax m_motor;

    public ShooterConveyor() {
        m_motor = new CANSparkMax(Constants.SHOOTER_CONVEYOR_SPARK, MotorType.kBrushless);
        m_motor.restoreFactoryDefaults();
        m_motor.setSmartCurrentLimit(Constants.SPARK_MAX_CURRENT_LIMIT);
        m_motor.setInverted(false);

    } 

    public void inConveyor() {
        m_motor.set(1);
    }

    public void outConveyor() {
        m_motor.set(-1);
    }

    public void stop() {
        m_motor.set(0);
    }
}
