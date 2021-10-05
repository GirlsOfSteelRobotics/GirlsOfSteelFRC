package com.gos.power_up.subsystems;

import com.gos.power_up.RobotMap;
import com.gos.power_up.commands.LiftHold;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StickyFaults;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem {

	private WPI_TalonSRX lift;
	private DigitalInput limitSwitch;

	private static double goalLiftPosition;
	private static boolean inRecoveryMode;

	public static final double LIFT_MAX = 32500; //TODO tune
	public static final double LIFT_MIN = 0; //TODO tune
	public static final double LIFT_SWITCH = 12500; //TODO tune
	public static final double LIFT_SCALE = 32500; //TODO tune
	public static final double LIFT_GROUND = 1000; //TODO tune
	public static final double LIFT_INCREMENT = 300; //TODO tune 250

	private StickyFaults faults = new StickyFaults();

	public Lift() {
		lift = new WPI_TalonSRX(RobotMap.LIFT);
		limitSwitch = new DigitalInput(RobotMap.LIMIT_SWITCH);
		lift.setInverted(true);
		lift.setSensorPhase(true);
		lift.configAllowableClosedloopError(0, 100, 0);
		lift.configContinuousCurrentLimit(0, 10);
		lift.enableCurrentLimit(false);
		lift.clearStickyFaults(10);
		setupLiftFPID();
		goalLiftPosition = 0;
		inRecoveryMode = false;
		//System.out.println("Lift Constructed");
		addChild(lift);
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new LiftHold());
	}

	public void setupLiftFPID() {
		//talon.setPosition(0); //TODO figure out new syntax
		lift.config_kF(0, 0, 10);
		lift.config_kP(0, 0.3, 10);
		lift.config_kI(0, 0, 10);
		lift.config_kD(0, 0, 10);	
	}

	public void setLiftSpeed(double speed) {
		lift.set(speed); //value between -1.0 and 1.0;
	}

	public void stop() {
		lift.stopMotor();
	}

	public boolean isAtBottom(){
		return !limitSwitch.get();
	}

	public double getGoalLiftPosition()
	{
		return goalLiftPosition;
	}

	public void setGoalLiftPosition(double goal)
	{
		goalLiftPosition = goal;
	}

	public double getLiftPosition()
	{
		return lift.getSelectedSensorPosition(0);
	}

	public void enterRecoveryMode()
	{
		inRecoveryMode = true;
		System.out.println("Lift IN RECOVERY MODE");
	}

	public void printLimitSwitch()
	{
		if (isAtBottom()) System.out.println("Lift: Limit switch ACTIVATED at bottom");
		else System.out.println("Lift: Limit switch NOT activated at bottom");
	}

	public void holdLiftPosition()
	{
		//printLimitSwitch(); ///Testing Limit Switch

		lift.getStickyFaults(faults);
		if (faults.ResetDuringEn) {
			inRecoveryMode = true;
			goalLiftPosition = 0;
			lift.clearStickyFaults(10);
			System.out.println("Lift: Sticky fault detected, IN RECOVERY MODE");
		}
		if (inRecoveryMode)
		{
			if (isAtBottom()) 
			{
				lift.setSelectedSensorPosition(0, 0, 10);
				inRecoveryMode = false;
				System.out.println("Lift: encoder position recovered (limit switch activated at bottom)");
			}
		}
		lift.set(ControlMode.Position, goalLiftPosition);
		//System.out.println("GoalLiftPosition: " + goalLiftPosition);
	}
	public void setLiftToScale()
	{
		if (!inRecoveryMode) goalLiftPosition = LIFT_SCALE;
		else System.out.println("Lift in recovery mode, can't go to scale");
	}
	public void setLiftToSwitch()
	{
		if (!inRecoveryMode) goalLiftPosition = LIFT_SWITCH;
		else System.out.println("Lift in recovery mode, can't go to switch");
	}
	public void setLiftToGround()
	{
		if (!inRecoveryMode) goalLiftPosition = LIFT_GROUND;
		else System.out.println("Lift in recovery mode, can't go to ground");
	}

	public void incrementLift()
	{
		double goalPosition = goalLiftPosition + LIFT_INCREMENT;
		if (!inRecoveryMode && (goalPosition >= LIFT_MAX))
		{
			goalLiftPosition = LIFT_MAX;
		}
		else
		{
			goalLiftPosition = goalPosition;
			//System.out.println("Lift incremented. New goal : " + goalLiftPosition);
		}
	}

	public void decrementLift()
	{
		double goalPosition = goalLiftPosition - LIFT_INCREMENT;
		if (!inRecoveryMode && (goalPosition <= LIFT_MIN))
		{
			goalLiftPosition = LIFT_MIN;
		}
		else
		{
			goalLiftPosition = goalPosition;
			//System.out.println("Lift decremented. New goal : " + goalLiftPosition);
		}
	}
}

