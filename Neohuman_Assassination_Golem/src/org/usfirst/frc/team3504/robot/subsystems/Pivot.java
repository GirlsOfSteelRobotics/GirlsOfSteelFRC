package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pivot extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private CANTalon pivotMotor;
	
	private double encOffsetValue = 0;
	
	public Pivot() {
		pivotMotor = new CANTalon(RobotMap.PIVOT_MOTOR);
		pivotMotor.changeControlMode(CANTalon.TalonControlMode.Speed);
	}
	
	public int getPosition() {
		if (getTopLimitSwitch() == true) 
			return 1;
		else if(getBottomLimitSwitch() == true)
			return -1;
		else
			return 0;
	}
	
	public void tiltUpandDown(double speed) {
		pivotMotor.set(speed);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
    
    public boolean getTopLimitSwitch(){
		return !pivotMotor.isFwdLimitSwitchClosed();
	}
	public boolean getBottomLimitSwitch(){
		return !pivotMotor.isRevLimitSwitchClosed();
	}
    
    public double getEncoderRight() {
		return pivotMotor.getEncPosition();
	}

	public double getEncoderDistance() {
		return (getEncoderRight() - encOffsetValue); //TODO: Know where encoder is
	}

	public void resetDistance() {
		encOffsetValue = getEncoderRight();
	}
}

