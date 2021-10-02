package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.Shooting;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {

	private Joystick operatorGamePad ;
	private Joystick drivingJoystick ;

	private JoystickButton shoot;
	private JoystickButton stopShoot;

	public OI() {

		/* BUTTON MAPPING */
		
		drivingJoystick = new Joystick(RobotMap.JOYSTICK_1);
		operatorGamePad = new Joystick(RobotMap.JOYSTICK_2);
		shoot = new JoystickButton(operatorGamePad, 4);
		
		/* BUTTON ACTIONS */
		
		shoot.whileHeld(new Shooting());
	}
		
		public double getDrivingJoyStickY(){
			return drivingJoystick.getY();
		}

		public double getDrivingJoyStickX(){
			return drivingJoystick.getX();
		}

	
}


