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
    //private WPI_TalonSRX screwBack; 

    public static final double CLIMBER_UP = 1500.0;
    public static final double CLIMBER_DOWN = 0.0;


    
    public ScrewClimber(){
      screwFront = new WPI_TalonSRX(RobotMap.SCREW_FRONT_TALON); 
      //screwBack = new WPI_TalonSRX(RobotMap.SCREW_BACK);  

      screwFront.setSensorPhase(true);
      screwFront.setSensorPhase(true);
    }
 
    //the value in set expiration is in SECONDS not milliseconds
    public void setClimberPosition(double pos){
      screwFront.set(ControlMode.Position, pos);
      //screwBack.set(ControlMode.Position, pos);
    }

    public void setClimberSpeed(double speed){
      screwFront.set(ControlMode.Position, speed);
    }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
