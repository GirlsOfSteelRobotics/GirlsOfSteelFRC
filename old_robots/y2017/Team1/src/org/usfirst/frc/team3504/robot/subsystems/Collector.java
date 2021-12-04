package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class Collector extends Subsystem {

    private CANTalon collectorMotor;

    public Collector() {
    collectorMotor = new CANTalon(RobotMap.COLLECTOR_MOTOR);
    collectorMotor.changeControlMode(TalonControlMode.PercentVbus);
    //addChild("Talon", collectorMotor1);
    //addChild("Talon", collectorMotor2);
    }

    public void spinWheels(double speed) {
        collectorMotor.set(speed);
    }

    public void stop() {
        collectorMotor.set(0.0);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
