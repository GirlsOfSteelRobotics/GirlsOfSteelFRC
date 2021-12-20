package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.RobotMap;


/**
 *
 */
public class Collector extends Subsystem {

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

    @Override
    public void initDefaultCommand() {
    }
}
