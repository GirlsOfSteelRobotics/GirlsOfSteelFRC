package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LEDLights extends Subsystem {
    
	SerialPort serialPort = new SerialPort(9600, SerialPort.Port.kUSB);
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public LEDLights() {
	}
	
	public void redLight(){
		byte[] buffer = new byte[3];
		buffer[0] = (byte) 255; //red
		buffer[1] = 0; //green
		buffer[2] = 0; //blue
		serialPort.write(buffer, 3); 
	}
	
	public void greenLight(){
		byte[] buffer = new byte[3];
		buffer[0] = 0; //red
		buffer[1] = (byte) 255; //green
		buffer[2] = 0; //blue 
		serialPort.write(buffer, 3); 
	}
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

