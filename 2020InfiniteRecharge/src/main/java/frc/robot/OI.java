/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    
	public XboxController drivingPad;
	public XboxController operatingPad;

	public OI(Chassis chassis, ControlPanel controlPanel, Limelight limelight, 
				Shooter shooter, ShooterIntake shooterIntake, ShooterConveyor shooterConveyor,
				 Lift lift, Winch winch) {
		drivingPad = new XboxController(0);
        operatingPad = new XboxController(1);
        
		new JoystickButton(operatingPad, Button.kA.value).whileHeld(new OuterShootAlign(chassis, limelight));
		
		new JoystickButton(operatingPad, Button.kB.value).whileHeld(new RunShooterRPM(shooter, 400));
		new JoystickButton(operatingPad, Button.kX.value).whileHeld(new RunShooterRPM(shooter, 600));

		new JoystickButton(operatingPad, Button.kBumperLeft.value).whileHeld(new IntakeCells(shooterIntake, true));
		new JoystickButton(operatingPad, Button.kBack.value).whileHeld(new IntakeCells(shooterIntake, false));
		new JoystickButton(operatingPad, Button.kBumperRight.value).whileHeld(new Conveyor(shooterConveyor, true));
		new JoystickButton(operatingPad, Button.kStart.value).whileHeld(new Conveyor(shooterConveyor, false));
		new JoystickButton(drivingPad, Button.kA.value).whileHeld(new HangerLift(lift, false));
		new JoystickButton(drivingPad, Button.kBumperRight.value).whileHeld(new WinchWind(winch, false));
		new JoystickButton(drivingPad, Button.kBumperLeft.value).whileHeld(new WinchWind(winch, true));
		new JoystickButton(drivingPad, Button.kB.value).whileHeld(new HangerLift(lift, true));
	}

    // Y is negated so that pushing the joystick forward results in positive values
	public double getJoystickSpeed() {
		return -drivingPad.getY(Hand.kLeft);
	}	

	public double getJoystickSpin() {
		return drivingPad.getX(Hand.kRight);
	}
	
}