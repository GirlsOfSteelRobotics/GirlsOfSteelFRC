/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.*;
import frc.robot.GripPipelineListener;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Timer; 
import edu.wpi.first.wpilibj.command.Command; 
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.vision.VisionRunner.Listener;
import edu.wpi.first.wpilibj.command.Command;

public class DriveByVision extends Command {

  public DriveByVision() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.motor);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("DriveByVision Initialized");

  

		// Change motor control to speed in the -1..+1 range
		Robot.motor.setSpeedMode();

		System.out.println("DriveByVision Initialized");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double targetX;
    double contoursNum;
    synchronized(Robot.listener.cameraLock){
      targetX = Robot.listener.targetX;
      contoursNum = Robot.listener.contours.size();
    }
    if(targetX < 0){
      System.out.println("No contours found.");
      Robot.motor.stop();
    }
    else{
      if(contoursNum == 1){
        System.out.println("Contours found: " + contoursNum);
        Robot.motor.motorGoSlow();
      }
      else if(contoursNum == 2){
        System.out.println("Contours found: " + contoursNum);
        Robot.motor.motorGoFast();
      }
      else{
        Robot.motor.stop();
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("DriveByVision end");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
