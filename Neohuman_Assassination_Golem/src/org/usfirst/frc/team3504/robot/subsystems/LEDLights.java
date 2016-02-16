package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LEDLights extends Subsystem {
    

	SerialPort serialPort = new SerialPort(9600, SerialPort.Port.kMXP);
	//SerialPort serialPort;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public LEDLights() {
		//serialPort = new SerialPort(9600, SerialPort.Port.kUSB); 
	}
	/*
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
	
	public void rainbowLights(){
		serialPort.writeString("a"); 
	}
	
	public void dotLights(){
		serialPort.writeString("p"); 
	}
	*/
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
}


