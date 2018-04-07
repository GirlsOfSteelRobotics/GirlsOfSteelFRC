package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.WristHold;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Wrist extends Subsystem {

	private static final boolean ENABLE_ABSOLUTE_MODE = false;

	private WPI_TalonSRX wrist;
	private double goalWristPosition;

	/**
	 * Set ABS_IN_POSITION to the value of the absolute encoder when the wrist
	 * is all the way up. Read the value from http://roborio-3504-frc.local/ in
	 * the wrist Talon self-test screen. The particular line looks like this,
	 * where 3545 is the position: PosEncPulse Pos:3545u
	 */
	private static final int ABS_IN_POSITION = ENABLE_ABSOLUTE_MODE ? 4150 : 0; //TODO: Find this value for Clyde
	public static final int WRIST_IN_BOUND = Math.floorMod(ABS_IN_POSITION - 60,
			4096);
	public static final int WRIST_OUT_BOUND = Math
			.floorMod(ABS_IN_POSITION - 1000, 4096);
	public static final int WRIST_COLLECT = Math.floorMod(ABS_IN_POSITION - 930,
			4096);
	public static final int WRIST_SWITCH = Math.floorMod(ABS_IN_POSITION - 800,
			4096);
	public static final int WRIST_SHOOT = Math.floorMod(ABS_IN_POSITION - 400,
			4096);
	public static final int WRIST_INCREMENT = 20;

	public Wrist() {
		wrist = new WPI_TalonSRX(RobotMap.WRIST);
		wrist.config_kF(0, 0, 10);
		wrist.config_kP(0, 1.5, 10);
		wrist.config_kI(0, 0, 10);
		wrist.config_kD(0, 15, 10);
		// Set the feedback device based on whether Absolute encoder mode is enabled
		if (ENABLE_ABSOLUTE_MODE)
			wrist.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
		else
			wrist.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);			
		LiveWindow.add(wrist);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new WristHold());
	}

	public void setWristPosition(double pos) {
		wrist.set(ControlMode.Position, pos);
	}

	public void wristStop() {
		wrist.stopMotor();
	}

	public void holdWristPosition() {
		wrist.set(ControlMode.Position, goalWristPosition);
	}

	public void wristIn() {
		goalWristPosition = goalWristPosition + WRIST_INCREMENT;
		if (goalWristPosition >= WRIST_IN_BOUND) {
			goalWristPosition = WRIST_IN_BOUND;
		}
	}

	public void wristOut() {
		goalWristPosition = goalWristPosition - WRIST_INCREMENT;
		if (goalWristPosition <= WRIST_OUT_BOUND) {
			goalWristPosition = WRIST_OUT_BOUND;
		}
	}

	public void setGoalWristPosition(double goal) {
		goalWristPosition = goal;
	}

	public double getWristPosition() {
		return wrist.getSelectedSensorPosition(0);
	}
}
