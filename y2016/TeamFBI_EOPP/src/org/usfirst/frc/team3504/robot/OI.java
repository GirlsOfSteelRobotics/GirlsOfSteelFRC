package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.ConveyorDown;
import org.usfirst.frc.team3504.robot.commands.ConveyorUp;
import org.usfirst.frc.team3504.robot.commands.ShiftDown;
import org.usfirst.frc.team3504.robot.commands.ShiftUp;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */


public class OI {

	// Joysticks
	private Joystick operatorJoystick;
	private Joystick chassisJoystick;
	
	//Driving
	private JoystickButton shiftUp;
	private JoystickButton shiftDown;
	
	//Collector
	private JoystickButton conveyorUp;
	private JoystickButton conveyorDown;

	public OI() {
		// Joysticks
		operatorJoystick = new Joystick(RobotMap.OPERATOR_JOYSTICK);
		chassisJoystick = new Joystick(RobotMap.CHASSIS_JOYSTICK);
		
		conveyorUp = new JoystickButton(operatorJoystick, 5); //TODO: change 
		conveyorUp.whileHeld(new ConveyorUp());
		conveyorDown = new JoystickButton(operatorJoystick, 4); //TODO: change 
		conveyorDown.whileHeld(new ConveyorDown());
		
		shiftUp = new JoystickButton(chassisJoystick, 5); //TODO: change 
		shiftUp.whenPressed(new ShiftUp());
		shiftDown = new JoystickButton(chassisJoystick, 3); //TODO: change
		shiftDown.whenPressed(new ShiftDown());
	}

	public Joystick getChassisJoystick() {
		return chassisJoystick;
	}

	public Joystick getOperatorJoystick() {
		return operatorJoystick;
	}
}
