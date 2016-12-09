package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.ArmDown;
import org.usfirst.frc.team3504.robot.commands.ArmUp;
import org.usfirst.frc.team3504.robot.commands.Collect;
import org.usfirst.frc.team3504.robot.commands.JawIn;
import org.usfirst.frc.team3504.robot.commands.JawOut;
import org.usfirst.frc.team3504.robot.commands.Shoot;
import org.usfirst.frc.team3504.robot.commands.ShootPrep;
import org.usfirst.frc.team3504.robot.commands.ShooterIn;
import org.usfirst.frc.team3504.robot.commands.ShooterOut;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
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
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	public Joystick driveStick;
	
	//shooting
	private JoystickButton shootPrep;
	private JoystickButton shoot;
	
	//collect
	private JoystickButton collect;
	
	//arm
	private JoystickButton armUp;
	private JoystickButton armDown;
	
	//jaw
	private JoystickButton jawIn;
	private JoystickButton jawOut;
	
	//shooter piston
	private JoystickButton shooterIn;
	private JoystickButton shooterOut;
	
	public OI() {
		
		driveStick = new Joystick(0);
		
		shootPrep = new JoystickButton(driveStick, 9); //TODO: fix
		shootPrep.whenPressed(new ShootPrep());
		shoot = new JoystickButton(driveStick, 10); //TODO: fix
		shoot.whenPressed(new Shoot());
		
		collect = new JoystickButton(driveStick, 11); //TODO: fix
		collect.whileHeld(new Collect());
		
		armUp = new JoystickButton(driveStick, 8); //TODO: fix
		armUp.whileHeld(new ArmUp()); 
		armDown = new JoystickButton(driveStick, 7); //TODO: fix
		armDown.whileHeld(new ArmDown());
		
		jawIn = new JoystickButton(driveStick, 4); //TODO: fix
		jawIn.whenPressed(new JawIn());
		jawOut = new JoystickButton(driveStick, 5); //TODO: fix
		jawOut.whenPressed(new JawOut());
		
		shooterIn = new JoystickButton(driveStick, 2); //TODO: fix
		shooterIn.whenPressed(new ShooterIn());
		shooterOut = new JoystickButton(driveStick, 3); //TODO: fix
		shooterOut.whenPressed(new ShooterOut());
	}
	
	public Joystick getDriveStick() {
		return driveStick;
	}
}


