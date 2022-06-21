package com.gos.steam_works.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.steam_works.RobotMap;

/**
 *
 */
public class Loader extends SubsystemBase {

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


}
