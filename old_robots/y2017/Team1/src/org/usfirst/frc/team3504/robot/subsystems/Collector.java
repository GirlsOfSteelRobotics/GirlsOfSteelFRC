package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.RobotMap;


/**
 *
 */
public class Collector extends Subsystem {

    private final CANTalon m_collectorMotor;

    public Collector() {
    m_collectorMotor = new CANTalon(RobotMap.COLLECTOR_MOTOR);
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
