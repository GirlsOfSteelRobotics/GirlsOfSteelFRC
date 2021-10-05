package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Agitator extends Subsystem {

	private DoubleSolenoid agitator;

	public Agitator() {
		agitator = new DoubleSolenoid(RobotMap.AGITATOR_A, RobotMap.AGITATOR_B);

		LiveWindow.addActuator("Agitator", "agitator", agitator);
	}

	public void agitateForwards() {
		agitator.set(DoubleSolenoid.Value.kForward);
	}

	public void agitateBackwards() {
		agitator.set(DoubleSolenoid.Value.kReverse);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
