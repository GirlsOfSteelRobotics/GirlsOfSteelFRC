package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.ConveyorDown;
import org.usfirst.frc.team3504.robot.commands.ConveyorUp;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */


public class OI {
	// Joysticks
	private Joystick operatorJoystick;
	private Joystick chassisJoystick;
	
	//Collector
	private JoystickButton conveyorUp;
	private JoystickButton conveyorDown;
	
	public OI() {
		// Joysticks
		operatorJoystick = new Joystick(RobotMap.OPERATOR_JOYSTICK);
		chassisJoystick = new Joystick(RobotMap.CHASSIS_JOYSTICK);
		
		conveyorUp = new JoystickButton(operatorJoystick, 1); //TODO: change these
		conveyorUp.whenPressed(new ConveyorUp());
		conveyorDown = new JoystickButton(operatorJoystick, 2); //TODO: change these
		conveyorDown.whenPressed(new ConveyorDown());
	}
	
	public Joystick getChassisJoystick() {
		return chassisJoystick;
	}
	
	public Joystick getOperatorJoystick() {
		return operatorJoystick;
	}
	
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
  
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
}

