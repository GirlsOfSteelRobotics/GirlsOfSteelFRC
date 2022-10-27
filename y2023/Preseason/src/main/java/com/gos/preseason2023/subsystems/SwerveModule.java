package com.gos.preseason2023.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class SwerveModule {

    private final CANSparkMax m_spinMotor;
    private final CANSparkMax m_powerMotor;

    private final RelativeEncoder m_spinEncoder;
    private final RelativeEncoder m_powerEncoder;

    public SwerveModule(int spinID, int powerID) {
        m_spinMotor = new CANSparkMax(spinID, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_powerMotor = new CANSparkMax(powerID, CANSparkMaxLowLevel.MotorType.kBrushless);

        m_spinEncoder = m_spinMotor.getEncoder();
        m_powerEncoder = m_powerMotor.getEncoder();
    }

    public void goToState(SwerveModuleState state) {
        double error = state.angle.getDegrees() - m_spinEncoder.getPosition();
        if(error > 0) {
            m_spinMotor.set(0.5);
        }
        else {
            m_spinMotor.set(-.5);
        }

        m_powerMotor.set(state.speedMetersPerSecond);
    }
}
