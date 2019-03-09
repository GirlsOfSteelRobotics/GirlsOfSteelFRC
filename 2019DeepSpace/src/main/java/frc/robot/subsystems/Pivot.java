/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap; 
import frc.robot.commands.PivotHold;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX; 
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Pivot extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private WPI_TalonSRX pivot; 
	private double goalPivotPosition;

  // TODO: tune all
	public static final double PIVOT_INCREMENT = 200; 
	public static final double PIVOT_GROUND = -4143; // -4143 (pre-MVR values)
	public static final double PIVOT_ROCKET = -1852; // -1852
	public static final double PIVOT_CARGO = -1191; // -1091
	public static final double PIVOT_TOLERANCE = 100;
  
  public Pivot() {
		pivot = new WPI_TalonSRX(RobotMap.PIVOT_TALON);
		pivot.setSensorPhase(true);
		setupPivotFPID();
		LiveWindow.add(pivot);
	}

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new PivotHold()); 
  }

  public void setupPivotFPID() {
		pivot.config_kF(0, 0, 10);
		pivot.config_kP(0, 1.5, 10);
		pivot.config_kI(0, 0, 10);
		pivot.config_kD(0, 15, 10);	
  }


  public void setPivotPosition(double pos){
    pivot.set(ControlMode.Position, pos); 
  }


  public void holdPivotPosition(){
		pivot.set(ControlMode.Position, goalPivotPosition); 
		//System.out.println("Pivot goal position" + goalPivotPosition +  "actual position " + pivot.getSelectedSensorPosition(0));
  }

  public void pivotToGround(){
		pivot.set(ControlMode.Position, PIVOT_GROUND); 
  }

  public void pivotToRocket(){
		pivot.set(ControlMode.Position, PIVOT_ROCKET); 
	}
	
	public void pivotToCargo(){
		pivot.set(ControlMode.Position, PIVOT_CARGO); 
  }
	
	public void setGoalPivotPosition(double goal) {
		goalPivotPosition = goal;
	}
	
	public double getPivotPosition() {
		return pivot.getSelectedSensorPosition(0);
	}

	public void incrementPivot () {
		goalPivotPosition = getPivotPosition(); 
		goalPivotPosition += PIVOT_INCREMENT; // TODO: Adjust
	}

	public void decrementPivot () {
		goalPivotPosition = getPivotPosition(); 
		goalPivotPosition -= PIVOT_INCREMENT; //TODO: Adjust
	}

	public boolean checkCurrentPivotPosition(double goalPos) {
		boolean isFinished = (goalPos <= getPivotPosition() + PIVOT_TOLERANCE 
    && goalPos  >= getPivotPosition() - PIVOT_TOLERANCE);
    //System.out.println("isFinished: " + isFinished);
    return isFinished;
	}

	public void pivotStop(){
		pivot.stopMotor(); 
	}	
}

