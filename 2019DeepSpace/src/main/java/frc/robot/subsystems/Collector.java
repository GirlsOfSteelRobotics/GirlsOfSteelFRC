
package frc.robot.subsystems;
import frc.robot.RobotMap;


import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.CollectorHold;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


/**
 * Add your docs here.
 */
public class Collector extends Subsystem {

    private WPI_TalonSRX leftCollect; 
    private WPI_TalonSRX rightCollect; 

    public double collectorSpeed; 

    public Collector(){
        leftCollect = new WPI_TalonSRX(RobotMap.COLLECT_LEFT);
		rightCollect = new WPI_TalonSRX(RobotMap.COLLECT_RIGHT);
		leftCollect.setSensorPhase(true);
		rightCollect.setSensorPhase(true);
		collectorSpeed = 0; 
    }
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new CollectorHold());
  }

  public void stop(){
      leftCollect.stopMotor(); 
      rightCollect.stopMotor(); 
      collectorSpeed = 0; 
      System.out.println("Collector stopped motors"); 

  }

  public void collect(){
      leftCollect.set(-1.00); 
      rightCollect.set(1.00); //these two may need to be reversed 

  }

  public void release(){
      leftCollect.set(1.00); 
      rightCollect.set(-1.00); //these two may need to be reversed
  }

  public int getRightCollectorID(){
        return rightCollect.getDeviceID(); 
  }

  public WPI_TalonSRX getRightCollector(){
        return rightCollect; 
  }

  public void runCollector(){
      leftCollect.set(-collectorSpeed); 
      rightCollect.set(collectorSpeed); 
  }

  public void slowCollect(){
    System.out.println("Collector holding ball"); 
    collectorSpeed = 0.15; 
  }
}
