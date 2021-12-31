package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.RobotMap;

/**
 *
 */
public class Loader extends Subsystem {

    private final WPI_TalonSRX m_loaderMotor;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public Loader() {
        m_loaderMotor = new WPI_TalonSRX(RobotMap.LOADER_MOTOR);

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
