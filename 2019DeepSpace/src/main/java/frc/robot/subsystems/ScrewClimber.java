/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap; 

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
/**
 * Add your docs here.
 */

 //commented out screwback for testing purposes
public class ScrewClimber extends Subsystem {
  DigitalInput limitSwitch = new DigitalInput(1);
  SpeedController armMotor = new Victor(1);
  Counter counter = new Counter(limitSwitch);
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

    private WPI_TalonSRX screwFront; 
    private WPI_TalonSRX screwBack; 

    public static final double CLIMBER_UP = 1500.0;
    public static final double CLIMBER_DOWN = 0.0;
    public static final double CLIMBER_INCREMENT = 300.0;

    private double goalClimberPosition;
    private boolean recoveryMode;
  
    public ScrewClimber(){
      screwFront = new WPI_TalonSRX(RobotMap.SCREW_FRONT_TALON); 
      screwBack = new WPI_TalonSRX(RobotMap.SCREW_BACK_TALON);  

      screwFront.setSensorPhase(true);
      screwBack.setSensorPhase(true);
      
      screwFront.config_kF(0, 0, 10);
      screwFront.config_kP(0, 1.5, 10);
      screwFront.config_kI(0, 0, 10);
      screwFront.config_kD(0, 15, 10);	
      
      screwBack.config_kF(0, 0, 10);
      screwBack.config_kP(0, 1.5, 10);
      screwBack.config_kI(0, 0, 10);
      screwBack.config_kD(0, 15, 10);	
    }
    
 
    //the value in set expiration is in SECONDS not milliseconds
    public void setClimberPosition(double pos) {
      screwFront.set(ControlMode.Position, pos);
      screwBack.set(ControlMode.Position, pos);
    }

    public void setClimberSpeed(double speed) {
      screwFront.set(speed);
      screwBack.set(speed);
    }

    public void stop() {
      screwFront.stopMotor(); 
      screwBack.stopMotor();
    }
  
    public double getGoalClimberPosition() {
      return goalClimberPosition;
    }
  
    public void setGoalCLimberPosition(double goal) {
      goalClimberPosition = goal;
    }
  
    public double getFrontPosition() {
      return screwFront.getSelectedSensorPosition(0);
    }

    public double getBackPosition() {
      return screwBack.getSelectedSensorPosition(0);
    }
  
    public void holdClimberPosition() {
      screwFront.set(ControlMode.Position, goalClimberPosition);
      screwBack.set(ControlMode.Position, goalClimberPosition);
    }

    public void holdClimberFrontPosition() {
      screwFront.set(ControlMode.Position, goalClimberPosition);
    }

    public void holdClimberBackPosition() {
      screwBack.set(ControlMode.Position, goalClimberPosition);
    }

    public void setClimberUp() { 
      goalClimberPosition = CLIMBER_UP;
    }
    
    public void setClimberDown() { 
      goalClimberPosition = CLIMBER_DOWN;
    }
  
    public void incrementClimber() {
      goalClimberPosition += CLIMBER_INCREMENT;
    }
  
    public void decrementClimber() {
      goalClimberPosition -= CLIMBER_INCREMENT;
    }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
