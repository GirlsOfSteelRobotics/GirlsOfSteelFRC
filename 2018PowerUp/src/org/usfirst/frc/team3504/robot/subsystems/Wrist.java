package org.usfirst.frc.team3504.robot.subsystems;

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
		wrist.config_kP(0, 2, 10);
		wrist.config_kI(0, 0.005, 10);
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
	
	public void setGoalWristPosition(double goal)
	{
		goalWristPosition = goal;
	}
	
	public void holdWrist()
	{
		wrist.set(ControlMode.Position, goalWristPosition);
	}
	
	public double getWristPosition()
	{
		return wrist.getSelectedSensorPosition(0);
	}
}

