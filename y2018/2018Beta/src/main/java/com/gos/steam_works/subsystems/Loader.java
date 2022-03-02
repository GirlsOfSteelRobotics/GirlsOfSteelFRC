package com.gos.steam_works.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.steam_works.RobotMap;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 *
 */
public class Loader extends SubsystemBase {

    private final WPI_TalonSRX m_loaderMotor;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public Loader() {
        m_loaderMotor = new WPI_TalonSRX(RobotMap.LOADER_MOTOR);

        //addChild("loaderMotor", loaderMotor);
    }

    public void loadBall(double speed) {
        m_loaderMotor.set(ControlMode.PercentOutput, speed);
    }

    public void stopLoader() {
        m_loaderMotor.set(0.0);
    }


}
