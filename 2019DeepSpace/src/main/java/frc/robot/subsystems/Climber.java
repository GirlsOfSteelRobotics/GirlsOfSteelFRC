/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Climber extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

    private DoubleSolenoid pistonFront; 
    private DoubleSolenoid pistonBack; 

    public enum Speed{
      kHigh, kLow;
    }
    
    private Speed speed; 

    public Climber() {
      pistonFront = new DoubleSolenoid(RobotMap.CLIMBER_FRONT_A, RobotMap.CLIMBER_FRONT_B); 
      pistonBack = new DoubleSolenoid(RobotMap.CLIMBER_BACK_A, RobotMap.CLIMBER_BACK_B); 

    }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
