/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap; 

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Add your docs here.
 */

public class ScrewClimber extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

    private WPI_TalonSRX screwFront; 
    private WPI_TalonSRX screwBack; 

    public static final double DURATION_TIME = 2;
    public static final double SPEED = 1;


    
    public ScrewClimber(){
      screwFront = new WPI_TalonSRX(RobotMap.SCREW_FRONT); 
      screwBack = new WPI_TalonSRX(RobotMap.SCREW_BACK);  

      screwFront.setSensorPhase(true);
      screwFront.setSensorPhase(true);

      
    }
 
    //the value in set expiration is in SECONDS not milliseconds
    public void screwBackUp(){
      screwBack.set(SPEED);
      screwBack.setExpiration(DURATION_TIME); //TODO tune this time and speed!!!
    }

    public void screwFrontUp(){
      screwFront.set(SPEED);
      screwFront.setExpiration(DURATION_TIME); //TODO tune this time and speed!!!

    }

    public void screwBackDown(){
      screwBack.set(-SPEED);
      screwBack.setExpiration(DURATION_TIME); //TODO tune this time and speed!!!

    }

    public void screwFrontDown(){
      screwFront.set(-SPEED);
      screwFront.setExpiration(DURATION_TIME); //TODO tune this time and speed!!!

    }

    public void stop(){
      screwFront.stopMotor();
      screwBack.stopMotor();
      System.out.println("Ball Screw Climber Stopped!");

    }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
