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

    private DoubleSolenoid pistonFrontA; 
    private DoubleSolenoid pistonFrontB; 
    private DoubleSolenoid pistonBackA; 
    private DoubleSolenoid pistonBackB; 

    
    public Climber(){
      pistonFrontA = new DoubleSolenoid(RobotMap.PISTON_FRONT_A1, RobotMap.PISTON_FRONT_A2); 
      pistonFrontB = new DoubleSolenoid(RobotMap.PISTON_FRONT_B1, RobotMap.PISTON_FRONT_B2); 
      pistonBackA = new DoubleSolenoid(RobotMap.PISTON_BACK_A1, RobotMap.PISTON_BACK_A2); 
      pistonBackB = new DoubleSolenoid(RobotMap.PISTON_BACK_B1, RobotMap.PISTON_BACK_B2); 
      

    }

    public void shiftUpBack(){
      pistonBackA.set(DoubleSolenoid.Value.kForward); 
      pistonBackB.set(DoubleSolenoid.Value.kForward); 

    }

    public void shiftUpFront(){
      pistonFrontA.set(DoubleSolenoid.Value.kForward);
      pistonFrontB.set(DoubleSolenoid.Value.kForward);

    }

    public void shiftDownBack(){
      pistonBackA.set(DoubleSolenoid.Value.kReverse); 
      pistonBackB.set(DoubleSolenoid.Value.kReverse); 

    }

    public void shiftDownFront(){
      pistonFrontA.set(DoubleSolenoid.Value.kReverse); 
      pistonFrontB.set(DoubleSolenoid.Value.kReverse); 

    }
 

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
