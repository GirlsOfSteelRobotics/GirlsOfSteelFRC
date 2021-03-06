package com.gos.stronghold.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.stronghold.robot.RobotMap;

/**
 *
 */
public class Claw extends SubsystemBase {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private final WPI_TalonSRX m_clawMotor;


    public Claw() {
        m_clawMotor = new WPI_TalonSRX(RobotMap.CLAW_MOTOR);
        addChild("Talon", m_clawMotor);
    }

    public void collectRelease(double speed) {
        m_clawMotor.set(ControlMode.PercentOutput, speed);

    }

    public void stopCollecting() {
        m_clawMotor.set(ControlMode.PercentOutput, 0.0);
        SmartDashboard.putBoolean("Claw Off", false);
    }


}
