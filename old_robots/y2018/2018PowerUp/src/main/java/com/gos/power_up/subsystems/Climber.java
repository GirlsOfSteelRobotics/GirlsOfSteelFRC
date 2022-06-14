package com.gos.power_up.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.RobotMap;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 *
 */
public class Climber extends SubsystemBase {

    private final WPI_TalonSRX m_climbMotor;

    public Climber() {
        m_climbMotor = new WPI_TalonSRX(RobotMap.CLIMB_MOTOR);

        m_climbMotor.setNeutralMode(NeutralMode.Brake);
        m_climbMotor.configContinuousCurrentLimit(200, 10);


        // Put methods for controlling this subsystem
        // here. Call these from Commands.

    }

    public void climb(double speed) {
        m_climbMotor.set(speed);
    }

    public void stopClimb() {
        m_climbMotor.set(0.0);
    }


}
