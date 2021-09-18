
package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TestFlapSubsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private CANTalon flapTalon;
	private static final double maxEncoder = 360; //max encoder val

	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private double encOffsetValue = 0;
    
	
	 public void initDefaultCommand() {
	        // Set the default command for a subsystem here.
	        //setDefaultCommand(new MySpecialCommand());
	 }
	
	 public TestFlapSubsystem(){
		 flapTalon = new CANTalon(RobotMap.FLAP_MOTOR);
		 flapTalon.ConfigRevLimitSwitchNormallyOpen(false);
		 flapTalon.ConfigFwdLimitSwitchNormallyOpen(false);
	 }
	 
	public void setTalon(double speed){
		flapTalon.set(speed);
	}
	
	public void stopTalon(){
		flapTalon.set(0.0);
	}
	/*
	public double getMaxEnc() {
		return maxEncoder;
	}
	*/
	//assuming that going forward will raise the flap and going backwards will lower the flap
	public boolean getTopLimitSwitch(){
		return flapTalon.isFwdLimitSwitchClosed();
	}
	public boolean getBottomLimitSwitch(){
		return flapTalon.isRevLimitSwitchClosed();
	}
	 
		public double getFlapEncoder() {
			return flapTalon.getEncPosition();
		}

		public double getFlapEncoderDistance() {
			return (getFlapEncoder() - encOffsetValue); //TODO: know how far encoder is
		}

		public void resetDistance() {
			encOffsetValue = getFlapEncoder();
		}
	

}