/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap; 
import frc.robot.Robot; 
import frc.robot.commands.DriveByJoystick; 

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Add your docs here.
 */
public class BabyDrive extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private WPI_TalonSRX babyDriveTalon; 

  public BabyDrive(){
    babyDriveTalon = new WPI_TalonSRX(RobotMap.BABY_DRIVE_TALON) ;
  }

  public void babyDriveForward(){
    babyDriveTalon.set(0.5);//TODO; ADJUST SPEED

  }

  public void babyDriveBackwards(){
    babyDriveTalon.set(-0.5);//TODO; ADJUST SPEED 

  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
