 package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3504.robot.commands.*;

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

	public JoystickButton joystickButton3;
	public JoystickButton joystickButton2;
	
	public JoystickButton closeArm;
	public JoystickButton openArm;
	public JoystickButton collectBall;
	public JoystickButton releaseBall;
	public JoystickButton shootBall;
	public JoystickButton shooterIn;
	public JoystickButton pivotUp;
	public JoystickButton pivotDown;
	
	public Joystick driveStick;
	public Joystick operatorGamePad;

	public OI() {
		driveStick = new Joystick(0);
		operatorGamePad = new Joystick(1);
//for attack3 joystick

		joystickButton2 = new JoystickButton(driveStick, 2);
		joystickButton2.whenPressed(new ShiftDown());
		joystickButton3 = new JoystickButton(driveStick, 3);
		joystickButton3.whenPressed(new ShiftUp());
		
		closeArm = new JoystickButton(operatorGamePad, 6); //good
		closeArm.whenPressed(new CloseArm());
		openArm = new JoystickButton(operatorGamePad, 5); //good
		openArm.whenPressed(new OpenArm());
		collectBall = new JoystickButton(operatorGamePad, 3); //good
		collectBall.whileHeld(new CollectBall());
		releaseBall = new JoystickButton(operatorGamePad, 4); //good
		releaseBall.whileHeld(new ReleaseBall());
		shootBall = new JoystickButton(operatorGamePad, 2);
		shootBall.whenPressed(new ShootBall());
		shooterIn = new JoystickButton(operatorGamePad, 1);
		shooterIn.whenPressed(new ShooterIn());
		pivotUp = new JoystickButton(operatorGamePad, 7); //good
		pivotUp.whileHeld(new PivotUp());
		pivotDown = new JoystickButton(operatorGamePad, 8); //good
		pivotDown.whileHeld(new PivotDown());
		
		// SmartDashboard Buttons
		SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
		SmartDashboard.putData("DriveByJoystick", new DriveByJoystick());
		SmartDashboard.putData("Shift Up", new ShiftUp());
		SmartDashboard.putData("Shift Down", new ShiftDown());
		SmartDashboard.putData("Accessory Left Fwd", new AccessoryLeftFwd());
		SmartDashboard.putData("Accessory Left Rev", new AccessoryLeftRev());
		SmartDashboard.putData("Accessory Right Fwd", new AccessoryRightFwd());
		SmartDashboard.putData("Accessory Right Rev", new AccessoryRightRev());
	}

	public Joystick getDriveStick() {
		return driveStick;
	}
}

