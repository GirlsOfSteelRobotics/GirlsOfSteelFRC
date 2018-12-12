/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.ChangeToRed;
import org.usfirst.frc.team3504.robot.commands.ChangeToWhite;
import org.usfirst.frc.team3504.robot.commands.DriveByJoystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public Joystick joystick;
    
    //public Button shoot; 
	public Button changeToRed;
	public Button changeToWhite;

    public OI() {  
        joystick = new Joystick (0);
       
        // Example button
        //button1 = new JoystickButton(tankDrive, 1); //Open Pneumatic 
    	//button1.whenPressed(new OpenPneumatic());
		
        //shoot = new JoystickButton(joystick, 1);
        //shoot.whenHeld(new Shoot());
        
        changeToRed = new JoystickButton(joystick, 2);
        changeToRed.whenPressed(new ChangeToRed());
        
        changeToWhite = new JoystickButton(joystick, 3);
        changeToWhite.whenPressed(new ChangeToWhite());
        
        // SmartDashboard Buttons
        SmartDashboard.putData("DriveByJoystick", new DriveByJoystick());
        
    }

    public double getDrivingJoyStickY(){
    	return joystick.getY();
    }

    public double getDrivingJoyStickX(){
    	return joystick.getX();
    }
   
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
