package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LEDLights extends Subsystem {
    

	//SerialPort serialPort = new SerialPort(9600, SerialPort.Port.kUSB);
	SerialPort serialPort;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public LEDLights() {
		serialPort = new SerialPort(9600, SerialPort.Port.kOnboard); 
	}
	
	public void blueLight(){
		serialPort.writeString("b"); 
	}
	public void greenLight(){
		serialPort.writeString("g");
	}
	
	public void redLight(){
		serialPort.writeString("r");
	}
	
	public void whiteLight(){
		serialPort.writeString("w"); 
	}
	
	public void autoLights(){
		serialPort.writeString("a"); 
	}
	
	public void dotLights(){
		serialPort.writeString("p"); 
	}
	
	public void initLights(){
		switch (DriverStation.getInstance().getAlliance()) {
		case Red:
			Robot.ledlights.redLight();
		break;
		case Blue:
			Robot.ledlights.blueLight();
		break;
		case Invalid:
			Robot.ledlights.autoLights();
		default:
		break;
		}
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
}


