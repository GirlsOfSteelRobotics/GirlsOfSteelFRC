/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap; 
import frc.robot.commands.WristHold;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX; 
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Wrist extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private WPI_TalonSRX wrist; 
  private double goalWristPosition; 

  // TODO: tune all
  public static final double WRIST_IN_BOUND = -60; 
	public static final double WRIST_OUT_BOUND = -1000; 
	public static final double WRIST_COLLECT = -930; 
	public static final double WRIST_SWITCH = -800; 
	public static final double WRIST_INCREMENT = 20; 
	public static final double WRIST_SHOOT = -350;
  
  public Wrist() {
		wrist = new WPI_TalonSRX(RobotMap.PIVOT_TALON);
		setupWristFPID();
		LiveWindow.add(wrist);
	}


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new WristHold()); 
  }

  public void setupWristFPID() {
		// wrist.setPosition (0); TODO: figure out new syntax
		wrist.config_kF(0, 0, 10);
		wrist.config_kP(0, 1.5, 10);
		wrist.config_kI(0, 0, 10);
		wrist.config_kD(0, 15, 10);	
  }

  public void setWristSpeed(double speed){
    wrist.set(speed); 
  }

  public void setWristPosition(double pos){
    wrist.set(ControlMode.Position, pos); 

  }

  public void wristStop(){
    wrist.stopMotor(); 
  }

  public void holdWristPosition(){
    wrist.set(ControlMode.Position, goalWristPosition); 
  }

  public void wristIn(){
    double goalPosition = goalWristPosition + WRIST_INCREMENT;
		if (goalPosition >= WRIST_IN_BOUND)
		{
			goalWristPosition = WRIST_IN_BOUND;
		}
		else
		{
			goalWristPosition = goalPosition;
		}

  }

  public void wristOut()
	{
		double goalPosition = goalWristPosition - WRIST_INCREMENT;
		if (goalPosition <= WRIST_OUT_BOUND) {
			goalWristPosition = WRIST_OUT_BOUND;
		}	else {
			goalWristPosition = goalPosition;
    }
    
  }
	
	public void setGoalWristPosition(double goal) {
		goalWristPosition = goal;
	}
	
	public double getWristPosition() {
		return wrist.getSelectedSensorPosition(0);
	}

}
