package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

import org.usfirst.frc.team3504.robot.OI.DriveStyle;
import org.usfirst.frc.team3504.robot.commands.ExampleCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private Joystick operatingGamePad;
	private Joystick joystickA;
	private Joystick joystickB;
	private DigitalInput dio;
	
	public enum DriveStyle {
		gamePadArcade, gamePadTank, joystickArcade, joystickTank
	}; 
	
	public static DriveStyle driveStyle;
	
	public OI() {
		
		dio = new DigitalInput(0);
		operatingGamePad = new Joystick(0);
		joystickA = new Joystick(1);
		joystickB = new Joystick(2);
		if (joystickB.getName() == "")
		{
			joystickB = null;
		}
		
		if (joystickA.getIsXbox())
		{
			if(dio.get())
			{
				driveStyle = DriveStyle.gamePadTank;
			}
			else
			{
				driveStyle = DriveStyle.gamePadArcade;
			}
		}
		else
		{
			if(dio.get())
			{
				driveStyle = DriveStyle.joystickTank;
			}
			else
			{
				driveStyle = DriveStyle.joystickArcade;
			}
		}
		
		System.out.println("Selected drive style: " + driveStyle);
	}
}
