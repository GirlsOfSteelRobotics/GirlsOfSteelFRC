package com.gos.steam_works.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.steam_works.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Loader extends Subsystem {

    private final WPI_TalonSRX loaderMotor;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public Loader() {
        loaderMotor = new WPI_TalonSRX(RobotMap.LOADER_MOTOR);

        //LiveWindow.addActuator("Loader", "loaderMotor", loaderMotor);
    }

    public void loadBall(double speed) {
        loaderMotor.set(ControlMode.PercentOutput, speed);
    }

    public void stopLoader() {
        loaderMotor.set(0.0);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
}
