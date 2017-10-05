package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.Climb;
import org.usfirst.frc.team3504.robot.commands.CombinedShoot;
import org.usfirst.frc.team3504.robot.commands.CombinedShootGear;
import org.usfirst.frc.team3504.robot.commands.CombinedShootKey;
import org.usfirst.frc.team3504.robot.commands.DecrementHighShooter;
import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
import org.usfirst.frc.team3504.robot.commands.IncrementHighShooter;
import org.usfirst.frc.team3504.robot.commands.ShiftDown;
import org.usfirst.frc.team3504.robot.commands.ShiftUp;
import org.usfirst.frc.team3504.robot.commands.SwitchBackward;
import org.usfirst.frc.team3504.robot.commands.SwitchForward;
import org.usfirst.frc.team3504.robot.commands.TurnToGear;
import org.usfirst.frc.team3504.robot.commands.UnClimb;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoBoilerGearAndShoot;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoCenterGear;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoDoNothing;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoGear;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoShooter;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;

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
	
	
	public enum DriveStyle {
		oneStickArcade, twoStickArcade, gamePadArcade, twoStickTank, gamePadTank, droperation
	}; 
	
	public static DriveStyle driveStyle = DriveStyle.oneStickArcade;
	
	//IF ROZIE IS GAMEPAD; TURN TRUE. ELSE; TURN FALSE.
		boolean rozieDrive = false;
		
		//Rozie's Nonsense: Project Droperation.
		private Joystick roziePad = new Joystick(5);

	private Joystick drivingStick; 
	private Joystick drivingStickTurn; 
	private Joystick drivingStickForward;
	private Joystick drivingStickBackward;
	private Joystick drivingStickRight;
	private Joystick drivingStickLeft; 
	private Joystick gamePad;
	private Joystick drivingGamePad;
	private Joystick operatingGamePad; 
	private Joystick autonSelector;

	private DriveDirection driveDirection = DriveDirection.kFWD;
	
	private JoystickScaling joystickScale = JoystickScaling.linear;
	private static double DEADBAND = 0.1; //TODO: find a good value

	private JoystickButton switchToForward;
	private JoystickButton switchToBackward;

	private JoystickButton shifterUp;
	private JoystickButton shifterDown;

	private JoystickButton shoot;
	private JoystickButton shootGear;
	private JoystickButton shootKey;

	private JoystickButton agitate;

	private JoystickButton climb;
	private JoystickButton unClimb;

	private JoystickButton incrementHighShooter;
	private JoystickButton decrementHighShooter;

	private JoystickButton driveByVision;

	public OI() {
		// Define the joysticks
		autonSelector = new Joystick(3);
		if (driveStyle == DriveStyle.oneStickArcade){
			drivingStickForward = new Joystick(0);
			drivingStickBackward = new Joystick(1);
			gamePad = new Joystick(2);
		} 
		if (driveStyle == DriveStyle.twoStickArcade){
			drivingStick = new Joystick(0);
			drivingStickTurn = new Joystick(1);
			gamePad = new Joystick(2);
		}
		if (driveStyle == DriveStyle.gamePadArcade){
			drivingGamePad = new Joystick(0);
			operatingGamePad = new Joystick(1); 
		}
		if (driveStyle == DriveStyle.twoStickTank){
			drivingStickRight = new Joystick(0);
			drivingStickLeft = new Joystick(1);
			gamePad = new Joystick(2);
		}
		if (driveStyle == DriveStyle.gamePadTank){
			gamePad = new Joystick(2);
		}
		if (driveStyle == DriveStyle.droperation){
			gamePad = new Joystick(0); 
		}
		
		//BUTTON ASSIGNMENTS
		if (driveStyle == DriveStyle.oneStickArcade){
			//DRIVER BUTTONS
			// Button to change between drive joysticks on trigger of both joysticks
			switchToForward = new JoystickButton(drivingStickForward, 1);
			switchToBackward = new JoystickButton(drivingStickBackward, 1);
			// Buttons for shifters copied to both joysticks
			shifterDown = new JoystickButton(drivingStickForward, 2);
			shifterDown = new JoystickButton(drivingStickBackward, 2);
			shifterUp = new JoystickButton(drivingStickForward, 3);
			shifterUp = new JoystickButton(drivingStickBackward, 3);
			
			//OPERATOR BUTTONS
			// Shooter buttons
			shootKey = new JoystickButton(gamePad, 2);
			shootGear = new JoystickButton(gamePad, 3);
			shoot = new JoystickButton(gamePad, 4);
			incrementHighShooter = new JoystickButton(gamePad, 6);
			decrementHighShooter = new JoystickButton(gamePad, 8);
			// Climber
			unClimb = new JoystickButton(gamePad, 9);
			climb = new JoystickButton(gamePad, 10);
		} 
		else if (driveStyle == DriveStyle.twoStickArcade) {
			//DRIVER BUTTONS
			// Buttons for shifters copied to both joysticks
			shifterDown = new JoystickButton(drivingStickForward, 2);
			shifterDown = new JoystickButton(drivingStickBackward, 2);
			shifterUp = new JoystickButton(drivingStickForward, 3);
			shifterUp = new JoystickButton(drivingStickBackward, 3);
			
			//OPERATOR BUTTONS
			// Shooter buttons
			shootKey = new JoystickButton(gamePad, 2);
			shootGear = new JoystickButton(gamePad, 3);
			shoot = new JoystickButton(gamePad, 4);
			incrementHighShooter = new JoystickButton(gamePad, 6);
			decrementHighShooter = new JoystickButton(gamePad, 8);
			// Climber
			unClimb = new JoystickButton(gamePad, 9);
			climb = new JoystickButton(gamePad, 10);
		}
		else if (driveStyle == DriveStyle.gamePadArcade){
			//DRIVER BUTTONS
			// Buttons for shifters copied to both joysticks
			shifterDown = new JoystickButton(gamePad, 1); //TODO: change button value
			shifterUp = new JoystickButton(gamePad, 11); //TODO: change button value
			
			//OPERATOR BUTTONS
			// Shooter buttons
			shootKey = new JoystickButton(gamePad, 2);
			shootGear = new JoystickButton(gamePad, 3);
			shoot = new JoystickButton(gamePad, 4);
			incrementHighShooter = new JoystickButton(gamePad, 6);
			decrementHighShooter = new JoystickButton(gamePad, 8);
			// Climber
			unClimb = new JoystickButton(gamePad, 9);
			climb = new JoystickButton(gamePad, 10);
			
		}
		else if (driveStyle == DriveStyle.twoStickTank){
			//DRIVER BUTTONS
			// Buttons for shifters copied to both joysticks
			shifterDown = new JoystickButton(drivingStickRight, 2);
			shifterDown = new JoystickButton(drivingStickLeft, 2);
			shifterUp = new JoystickButton(drivingStickRight, 3);
			shifterUp = new JoystickButton(drivingStickLeft, 3);
			
			//OPERATOR BUTTONS
			// Shooter buttons
			shootKey = new JoystickButton(gamePad, 2);
			shootGear = new JoystickButton(gamePad, 3);
			shoot = new JoystickButton(gamePad, 4);
			incrementHighShooter = new JoystickButton(gamePad, 6);
			decrementHighShooter = new JoystickButton(gamePad, 8);
			// Climber
			unClimb = new JoystickButton(gamePad, 9);
			climb = new JoystickButton(gamePad, 10);
		}
		else if (driveStyle == DriveStyle.gamePadTank){
			//DRIVER BUTTONS
			// Buttons for shifters copied to both joysticks
			shifterDown = new JoystickButton(gamePad, 1); //TODO: change button value
			shifterUp = new JoystickButton(gamePad, 11); //TODO: change button value
			
			//OPERATOR BUTTONS
			// Shooter buttons
			shootKey = new JoystickButton(gamePad, 2);
			shootGear = new JoystickButton(gamePad, 3);
			shoot = new JoystickButton(gamePad, 4);
			incrementHighShooter = new JoystickButton(gamePad, 6);
			decrementHighShooter = new JoystickButton(gamePad, 8);
			// Climber
			unClimb = new JoystickButton(gamePad, 9);
			climb = new JoystickButton(gamePad, 10);
		}
		else if (driveStyle == DriveStyle.droperation){
			shifterDown = new JoystickButton(gamePad, 4); //Y
			shifterUp = new JoystickButton(gamePad, 2); // B
			climb = new JoystickButton(gamePad, 8);//START
			unClimb = new JoystickButton(gamePad, 7);  // BACK
			shoot = new JoystickButton(gamePad, 3); // X
		}
		
		
		// DRIVING BUTTONS
		// Button to change between drive joysticks on trigger of both joysticks
		switchToForward.whenPressed(new SwitchForward());
		switchToBackward.whenPressed(new SwitchBackward());
		// Buttons for shifters
		shifterDown.whenPressed(new ShiftDown());
		shifterUp.whenPressed(new ShiftUp());

		// OPERATOR BUTTONS
		// Shooter buttons
		shootKey.whileHeld(new CombinedShootKey());
		shootGear.whileHeld(new CombinedShootGear());
		shoot.whileHeld(new CombinedShoot());
		incrementHighShooter.whenPressed(new IncrementHighShooter());
		decrementHighShooter.whenPressed(new DecrementHighShooter());
		//Climb buttons
		unClimb.whileHeld(new UnClimb());
		climb.whileHeld(new Climb());
		
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
		double unscaledValue;
		
		if (driveStyle == DriveStyle.droperation || driveStyle == DriveStyle.gamePadArcade){
			unscaledValue = gamePad.getY();
		}
		else if (driveStyle == DriveStyle.oneStickArcade) {
			if (driveDirection == DriveDirection.kFWD) {
				unscaledValue = drivingStickForward.getY();
			} else {
				unscaledValue = -drivingStickBackward.getY();
			}
		}
		else unscaledValue = drivingStickForward.getX(); 
		
		return getScaledJoystickValue(unscaledValue);
	}
	
	public double getDrivingJoystickX() { // keep the redundancy, it breaks if
		double unscaledValue;
		
		if (driveStyle == DriveStyle.gamePadArcade) {
			unscaledValue = gamePad.getZ(); //TODO: this should get the Z rotate value							
		} else if (driveStyle == DriveStyle.droperation) { // removed
			unscaledValue = gamePad.getX();
		} else if (driveStyle == DriveStyle.oneStickArcade){
			if (driveDirection == DriveDirection.kFWD) {
				unscaledValue = drivingStickForward.getX();
			} else {
				unscaledValue =  -drivingStickBackward.getX();
			}
		}
		else unscaledValue = drivingStickForward.getX(); 
		
		return getScaledJoystickValue(unscaledValue);
	}
	
	public double getDrivingJoystickLeft() {
		double unscaledValue;
		
		if (driveStyle == DriveStyle.gamePadTank) {
			unscaledValue = gamePad.getY();							
		} 
		else if (driveStyle == DriveStyle.twoStickTank) {
			unscaledValue = drivingStickLeft.getY();
		} 
		else {
			unscaledValue = 0.0; //TODO: may want to return something else
		}
		
		return getScaledJoystickValue(unscaledValue);
	}
	
	public double getDrivingJoystickRight() {
		double unscaledValue;
		
		if (driveStyle == DriveStyle.gamePadTank) {
			unscaledValue = gamePad.getZ(); //TODO: this should get the Z vertical/rotate value								
		}
		else if (driveStyle == DriveStyle.twoStickTank) {
			unscaledValue = drivingStickRight.getY();
		}
		else {
			unscaledValue = 0.0; //TODO: may want to return something else
		}
		
		return getScaledJoystickValue(unscaledValue);
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
