
package frc.robot.subsystems;
import frc.robot.RobotMap;


import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


/**
 * Add your docs here.
 */
public class Collector extends Subsystem {

    private WPI_TalonSRX leftCollect; 
    private WPI_TalonSRX rightCollect; 

    public static final double SLOW_COLLECTOR_SPEED = 0.15; 
    public static final double COLLECTOR_SPEED = 0.75; 

    public Collector(){
        leftCollect = new WPI_TalonSRX(RobotMap.COLLECT_LEFT);
        rightCollect = new WPI_TalonSRX(RobotMap.COLLECT_RIGHT);
        
		leftCollect.setSensorPhase(true);
        rightCollect.setSensorPhase(true);
    }
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
  }

  public void stop(){
      leftCollect.stopMotor(); 
      rightCollect.stopMotor(); 
      System.out.println("Collector stopped motors"); 
  }

  public void collect(){
      leftCollect.set(-COLLECTOR_SPEED); 
      rightCollect.set(COLLECTOR_SPEED); // TODO: these two may need to be reversed 

  }

  public void release(){
      leftCollect.set(COLLECTOR_SPEED); 
      rightCollect.set(-COLLECTOR_SPEED); //these two may need to be reversed
  }

  public void slowCollect(){
    System.out.println("Collector holding ball"); 
    leftCollect.set(-SLOW_COLLECTOR_SPEED); 
      rightCollect.set(SLOW_COLLECTOR_SPEED); 
  }
}
