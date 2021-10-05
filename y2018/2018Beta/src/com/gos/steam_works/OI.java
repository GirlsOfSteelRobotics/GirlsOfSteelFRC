package com.gos.steam_works;

import com.gos.steam_works.subsystems.Shifters;
import com.gos.steam_works.commands.DriveByDistance;

import com.gos.steam_works.commands.ShiftDown;
import com.gos.steam_works.commands.ShiftUp;
import com.gos.steam_works.commands.TurnToGear;
import com.gos.steam_works.commands.autonomous.AutoBoilerGearAndShoot;
import com.gos.steam_works.commands.autonomous.AutoCenterGear;
import com.gos.steam_works.commands.autonomous.AutoDoNothing;
import com.gos.steam_works.commands.autonomous.AutoGear;
import com.gos.steam_works.commands.autonomous.AutoShooter;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public enum DriveDirection {
		kFWD, kREV
	};
	
	public enum JoystickScaling {
		linear, deadband, quadratic
	};
	
	
	//Drive Styles
	//gamepad: tank, split arcade
	//joystick: tank, one stick arcade 
	public enum DriveStyle {
		joystickArcade, gamePadArcade, joystickTank, gamePadTank
	}; 
	
	private DriveStyle driveStyle;
	
	private Joystick operatorGamePad = new Joystick (0);
	private Joystick drivingGamePad = new Joystick (1);
	private Joystick drivingJoystickOne = new Joystick (1);
	private Joystick drivingJoystickTwo = new Joystick (2);
	private Joystick autonSelector = new Joystick(5); 
	
	private DigitalInput dio0 = new DigitalInput(0);
	private DigitalInput dio1 = new DigitalInput(1);
	private DigitalInput dio2 = new DigitalInput(2);
	private DigitalInput dio3 = new DigitalInput(3);
	private DigitalInput dio4 = new DigitalInput(4);

	private DriveDirection driveDirection = DriveDirection.kFWD;
	
	private JoystickScaling joystickScale = JoystickScaling.linear;
	private static double DEADBAND = 0.3; //TODO: find a good value

	private JoystickButton switchToForward;
	private JoystickButton switchToBackward;

	private JoystickButton shifterUp;
	private JoystickButton shifterDown;

	private JoystickButton driveByDistanceLow;
	private JoystickButton driveByDistanceHigh;
	
	private JoystickButton liftUp;
	private JoystickButton liftDown;
	
	private JoystickButton collect;
	private JoystickButton release;

	public OI() {
		//BUTTON ASSIGNMENTS
		shifterDown = new JoystickButton(drivingJoystickOne, 2);
		shifterUp = new JoystickButton(drivingJoystickOne, 3);
		driveByDistanceLow = new JoystickButton(drivingJoystickOne, 9);
		driveByDistanceHigh = new JoystickButton(drivingJoystickOne, 10);
		
		liftUp = new JoystickButton(operatorGamePad, 1); //TODO: random buttom assignment
		liftDown = new JoystickButton(operatorGamePad, 2);
		
		collect = new JoystickButton(operatorGamePad, 3);
		release = new JoystickButton(operatorGamePad, 4);
		
		shifterDown.whenPressed(new ShiftDown());
		shifterUp.whenPressed(new ShiftUp());
		driveByDistanceLow.whenPressed(new DriveByDistance(12, Shifters.Speed.kLow));
		driveByDistanceHigh.whenPressed(new DriveByDistance(12, Shifters.Speed.kHigh));
		
		//liftUp.whileHeld(new LiftUp());
		//liftDown.whileHeld(new LiftDown());
		
		//collect.whileHeld(new Collect());
		//release.whileHeld(new Release());
		
		drivingGamePad.setTwistChannel(3);
		/* Drive by vision (plus back up a few inches when done)
		driveByVision = new JoystickButton(gamePad, 1);
		driveByVision.whenPressed(new CreateMotionProfile("/home/lvuser/leftMP.dat", "/home/lvuser/rightMP.dat"));*/
				
	}

	
	
	public Command getAutonCommand() {
		switch (getAutonSelector()) {
		case 0:
			return new DriveByDistance(45, Shifters.Speed.kHigh);
		case 1:
			return new DriveByDistance(112.0, Shifters.Speed.kHigh);
		case 2:
			return new AutoCenterGear();
		case 3: // red boiler
			return new AutoGear(44.0, TurnToGear.Direction.kLeft); //updated 1:04p 4/47/17
		case 4: // red loader
			return new AutoGear(55.0, TurnToGear.Direction.kRight);
		case 5: // blue boiler
			return new AutoGear(44.0, TurnToGear.Direction.kRight); //updated 1:04p 4/47/17
		case 6: // blue loader
			return new AutoGear(50.0, TurnToGear.Direction.kLeft); //was previously 55.0
		case 7:
			return new AutoShooter();
		//case 8:
			//return new DriveByDistance(-3, Shifters.Speed.kLow);
		case 9: //red boiler
			return new AutoBoilerGearAndShoot(44.0, TurnToGear.Direction.kLeft); //updated 1:04p 4/47/17
		case 10: //blue boiler
			return new AutoBoilerGearAndShoot(44.0, TurnToGear.Direction.kRight); //updated 1:04p 4/47/17
		/*case 11: 
			return new TurnByDistance(-13.0, -3.0, Shifters.Speed.kLow); 
		case 12:
			return new DriveByMotionProfile("/home/lvuser/leftCenterGear.dat", "/home/lvuser/rightCenterGear.dat", 1.0); 
		case 13:
			return new AutoShooterAndCrossLine();*/
		case 15:
			return new AutoDoNothing();
		default:
			return new DriveByDistance(75.5, Shifters.Speed.kLow);
		}
	}

	public double getDrivingJoystickY() {
		if (driveStyle == DriveStyle.gamePadArcade){
			return drivingGamePad.getY();
		}
		else if (driveStyle == DriveStyle.joystickArcade) {
			return drivingJoystickOne.getY();
		} 
		else if (driveStyle == DriveStyle.gamePadTank) {
			return drivingGamePad.getY(); //TODO: this should get the Z vertical/rotate value								
		}
		else if (driveStyle == DriveStyle.joystickTank) {
			return drivingJoystickOne.getY();
		} else {
			return 0.0;
		}
	}
	
	public double getDrivingJoystickX() {
		if (driveStyle == DriveStyle.gamePadArcade) { 
			return -drivingGamePad.getZ(); 
		} 
		else if (driveStyle == DriveStyle.joystickArcade){
				return drivingJoystickOne.getX();
		}
		else if (driveStyle == DriveStyle.gamePadTank) {
			return drivingGamePad.getTwist();							
		} 
		else if (driveStyle == DriveStyle.joystickTank) {
			return drivingJoystickTwo.getY();
		} 
		else {
			return 0.0;
		}
	}
	
	
	
	public double getScaledJoystickValue(double input)
	{
		double output = 0;
		
		if (joystickScale == JoystickScaling.linear)
		{
			output = input;
		}
		else if (joystickScale == JoystickScaling.deadband)
		{
			if (Math.abs(input) < DEADBAND)
				output = 0;
			else
			{
				if (input > 0) output = input - DEADBAND;
				else output = input + DEADBAND;
			}
		}
		else if (joystickScale == JoystickScaling.quadratic)
		{
			if (input > 0) output = Math.pow(input, 2);
			else output = -1 * Math.pow(input, 2);
		}
		
		return output;
	}

	public void setDriveDirection(DriveDirection driveDirection) {
		this.driveDirection = driveDirection;
		System.out.println("Drive direction set to: " + driveDirection);
	}
	
	public void setJoystickScale(JoystickScaling joystickScale) {
		this.joystickScale = joystickScale;
		System.out.println("Joystick direction set to: " + joystickScale);
	}

	public boolean isJoystickReversed() {
		return (driveDirection == DriveDirection.kREV);
	}
	
	public void setDriveStyle() {
		if (!dio1.get()) {
			driveStyle = DriveStyle.joystickArcade; 
		} else if (!dio2.get()) {
			driveStyle = DriveStyle.gamePadArcade; 
		} else if (!dio3.get()) {
			driveStyle = DriveStyle.joystickTank; 
		} else if (!dio4.get()) {
			driveStyle = DriveStyle.gamePadTank; 
		} else {
			System.out.println("NO DRIVE MODE SELECTED. \nDefaulting to Joystick Arcade...");
			driveStyle = DriveStyle.joystickArcade; 
		}
		System.out.println("Drive Mode: " + driveStyle);
	}
	
	public DriveStyle getDriveStyle() {
		return driveStyle; 
	}
	
	public boolean isSquared() {
		return !dio0.get(); 
	}

	/**
	 * Get Autonomous Mode Selector
	 * 
	 * Read a physical pushbutton switch attached to a USB gamepad controller,
	 * returning an integer that matches the current readout of the switch.
	 * 
	 * @return int ranging 0-15
	 */
	public int getAutonSelector() {
		// Each of the four "button" inputs corresponds to a bit of a binary
		// number encoding the current selection. To simplify wiring, buttons
		// 2-5 were used.
		int value = 1 * (autonSelector.getRawButton(2) ? 1 : 0)
				+ 2 * (autonSelector.getRawButton(3) ? 1 : 0)
				+ 4 * (autonSelector.getRawButton(4) ? 1 : 0)
				+ 8 * (autonSelector.getRawButton(5) ? 1 : 0);
		System.out.println("Auto Selector Number: " + value);
		return value;
	}
}
