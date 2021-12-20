package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.RobotMap;

/**
 *
 */
public class Loader extends Subsystem {

    private final CANTalon m_loaderMotor;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public Loader() {
        m_loaderMotor = new CANTalon(RobotMap.LOADER_MOTOR);

        addChild("loaderMotor", m_loaderMotor);
    }

    public void loadBall(double speed) {
        m_loaderMotor.set(ControlMode.PercentOutput, speed);
    }

    public void stopLoader() {
        m_loaderMotor.set(ControlMode.PercentOutput, 0.0);
    }

    @Override
    public void initDefaultCommand() {
    }
}
