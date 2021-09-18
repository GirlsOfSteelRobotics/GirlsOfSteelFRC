package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Claw extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private CANTalon clawMotor;
	
	
	public Claw() {
		clawMotor = new CANTalon(RobotMap.CLAW_MOTOR);
		LiveWindow.addActuator("Claw", "Talon", clawMotor);
	}

	public void collectRelease(double speed) {
		clawMotor.set(speed);
		
	}
	
	public void stopCollecting(){
		clawMotor.set(0.0);
		SmartDashboard.putBoolean("Claw Off", false);
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

