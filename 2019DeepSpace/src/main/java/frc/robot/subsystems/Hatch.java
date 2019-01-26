/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap; 


import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Hatch extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

    private Solenoid leftCollect; 
    //private Solenoid rightCollect;

    public Hatch(){
        leftCollect = new Solenoid(RobotMap.PISTON_COLLECT_A);
        //rightCollect = new Solenoid(RobotMap.PISTON_COLLECT_B); 
    }

    public void Collect(){
        leftCollect.set(true); 
        //rightCollect.set(Solenoid.kForward); 
      }
  
      public void Release(){
        leftCollect.set(false); 
        //rightCollect.set(Solenoid.Value.kForward); 
  
      }
 
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
