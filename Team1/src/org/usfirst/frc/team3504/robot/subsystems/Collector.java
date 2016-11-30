package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.RobotMap;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;


/**
 *
 */
public class Collector extends Subsystem {
    
	private CANTalon collectorMotor;
	
	public Collector() { 
	collectorMotor = new CANTalon(RobotMap.COLLECTOR_MOTOR);
	collectorMotor.changeControlMode(TalonControlMode.PercentVbus);
	//LiveWindow.addActuator("Collector", "Talon", collectorMotor1);
	//LiveWindow.addActuator("Collector", "Talon", collectorMotor2);
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

