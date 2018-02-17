package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.WristHold;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Wrist extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private WPI_TalonSRX wrist;
	private double goalWristPosition;
	
	public static final double WRIST_IN_BOUND = -60; //TODO tune
	public static final double WRIST_OUT_BOUND = -1020; //TODO tune
	public static final double WRIST_INCREMENT = 50; //TODO tune
	
	public Wrist() {
		wrist = new WPI_TalonSRX(RobotMap.WRIST);
		setupWristFPID();
		LiveWindow.add(wrist);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new WristHold());
    	
    }
    
    public void setupWristFPID() {
		//talon.setPosition (0); TODO figure out new syntax
		wrist.config_kF(0, 0, 10);
		wrist.config_kP(0, 1.5, 10);
		wrist.config_kI(0, 0, 10);
		wrist.config_kD(0, 0, 10);	
	}

    public void setWristSpeed(double speed) {
    	wrist.set(speed); //value between -1.0 and 1.0;
    }
    
    public void setWristPosition(double pos){
    	wrist.set(ControlMode.Position, pos);
    }

	public void wristStop(){
		wrist.stopMotor();
	}
	
	public void holdWristPosition()
	{
		wrist.set(ControlMode.Position, goalWristPosition);
	}
	
	public void wristIn()
	{
		double goalPosition = goalWristPosition + WRIST_INCREMENT;
		if (goalPosition >= WRIST_IN_BOUND)
		{
			goalWristPosition = WRIST_IN_BOUND;
		}
		else
		{
			goalWristPosition = goalPosition;
			System.out.println("Wrist moved in. New goal : " + goalWristPosition);
		}
		
	}
	
	public void wristOut()
	{
		double goalPosition = goalWristPosition - WRIST_INCREMENT;
		if (goalPosition <= WRIST_OUT_BOUND)
		{
			goalWristPosition = WRIST_OUT_BOUND;
		}
		else
		{
			goalWristPosition = goalPosition;
			System.out.println("Wrist moved out. New goal : " + goalWristPosition);
		}
		
	}
	
	public void setGoalWristPosition(double goal)
	{
		goalWristPosition = goal;
	}
	
	public double getWristPosition()
	{
		return wrist.getSelectedSensorPosition(0);
	}
}

