package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @authors annika and ziya
 */
public class Fingers extends Subsystem {

	private DoubleSolenoid fingersLeftSolenoid;
	private DoubleSolenoid fingersRightSolenoid;

	public Fingers() {
		fingersLeftSolenoid = new DoubleSolenoid(RobotMap.LEFT_FINGER_MODULE, RobotMap.LEFT_FINGER_PISTON_A,
				RobotMap.LEFT_FINGER_PISTON_B);
		fingersRightSolenoid = new DoubleSolenoid(RobotMap.RIGHT_FINGER_MODULE, RobotMap.RIGHT_FINGER_PISTON_A,
				RobotMap.RIGHT_FINGER_PISTON_B);
	}

	public void fingerDown() {
		fingersLeftSolenoid.set(DoubleSolenoid.Value.kForward);
		fingersRightSolenoid.set(DoubleSolenoid.Value.kForward);
	}

	public void fingerUp() {
		fingersLeftSolenoid.set(DoubleSolenoid.Value.kReverse);
		fingersRightSolenoid.set(DoubleSolenoid.Value.kReverse);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
