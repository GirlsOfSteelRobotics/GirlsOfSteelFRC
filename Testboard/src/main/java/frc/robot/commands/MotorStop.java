/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Motor;

public class MotorStop extends CommandBase {

  private Motor m_motor;

  public MotorStop(Motor motor) {
    m_motor = motor;
    
    // Use requires() here to declare subsystem dependencies
    super.addRequirements(m_motor);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    System.out.println("init MotorStop");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    m_motor.stop();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    System.out.println("end MotorStop");
  }
}
