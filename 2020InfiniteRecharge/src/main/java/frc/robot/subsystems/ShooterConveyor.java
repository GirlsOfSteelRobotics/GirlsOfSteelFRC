package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterConveyor extends SubsystemBase {

    private final CANSparkMax m_master;
    private final CANSparkMax m_follower;

    public ShooterConveyor() {
        m_master = new CANSparkMax(Constants.SHOOTER_CONVEYOR_SPARK_A, MotorType.kBrushless);
        m_follower = new CANSparkMax(Constants.SHOOTER_CONVEYOR_SPARK_B, MotorType.kBrushless);

        m_follower.follow(m_master);

        m_master.restoreFactoryDefaults();
        m_master.setSmartCurrentLimit(Constants.SPARK_MAX_CURRENT_LIMIT);
        m_master.setInverted(false);

    } 

    public void inConveyor() {
        m_master.set(1);
    }

    public void outConveyor() {
        m_master.set(-1);
    }

    public void stop() {
        m_master.set(0);
    }
}
