package com.gos.preseason2017.team1.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.preseason2017.team1.robot.RobotMap;


/**
 *
 */
public class Collector extends SubsystemBase {

    private final WPI_TalonSRX m_collectorMotor;

    public Collector() {
        m_collectorMotor = new WPI_TalonSRX(RobotMap.COLLECTOR_MOTOR);
    }

    public void spinWheels(double speed) {
        m_collectorMotor.set(ControlMode.PercentOutput, speed);
    }

    public void stop() {
        m_collectorMotor.set(ControlMode.PercentOutput, 0.0);
    }


}
