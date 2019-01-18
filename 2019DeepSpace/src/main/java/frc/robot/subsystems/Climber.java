/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap; 


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

    
    public Climber(){
      pistonFront = new DoubleSolenoid(RobotMap.PISTON_FRONT); 
      pistonBack = new DoubleSolenoid(RobotMap.PISTON_BACK); 

    }

    public void shiftUpBack(){
      pistonBack.set(DoubleSolenoid.Value.kForward); 
    }

    public void shiftUpFront(){
      pistonFront.set(DoubleSolenoid.Value.kForward);
    }

    public void shiftDownBack(){
      pistonBack.set(DoubleSolenoid.Value.kReverse); 
    }

    public void shiftDownFront(){
      pistonFront.set(DoubleSolenoid.Value.kReverse); 
    }
 

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
