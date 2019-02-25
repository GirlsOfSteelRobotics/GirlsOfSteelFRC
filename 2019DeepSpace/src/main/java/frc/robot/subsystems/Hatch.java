/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap; 

import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Hatch extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private Servo servo; 


  // TODO: tune all
	public static final double HATCH_IN = -4143; // Fix values
	public static final double HATCH_OUT = -1852; // Fix Values

  public Hatch() {
		servo = new Servo(RobotMap.HATCH_PWMPORT);
		LiveWindow.add(servo);
	}

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void setServoIn(){
    servo.set(HATCH_IN);
  }

  public void setServoOut(){
    servo.set(HATCH_OUT);
  }

  public double getServoPosition() {
    return servo.get();
  }

  
}


