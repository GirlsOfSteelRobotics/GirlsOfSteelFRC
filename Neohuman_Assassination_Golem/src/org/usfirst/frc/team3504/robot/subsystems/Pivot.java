package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Pivot extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private CANTalon pivotMotor;
	
	private double encOffsetValue = 0;
	
	/** save the target position to servo to */
	private double targetPositionRotations;
	
	//printouts to Smart Dashboard for PIDs
	private double output;
	private double position;
	private double trg;
	private int errNative;
	
	public Pivot() {
		
		pivotMotor = new CANTalon(RobotMap.PIVOT_MOTOR);
		pivotMotor.ConfigFwdLimitSwitchNormallyOpen(false);
		pivotMotor.ConfigRevLimitSwitchNormallyOpen(false);
		
		pivotMotor.setEncPosition(0);
        pivotMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder); 
        pivotMotor.reverseSensor(true);
        pivotMotor.configEncoderCodesPerRev(256);
        
        pivotMotor.configNominalOutputVoltage(+0.0, -0.0);
        pivotMotor.configPeakOutputVoltage(+12.0, -12.0);

        pivotMotor.setAllowableClosedLoopErr(0);
       
        pivotMotor.setProfile(0);
        pivotMotor.setF(0.0);
        pivotMotor.setP(8.0);
        pivotMotor.setI(0.003); 
        pivotMotor.setD(0.08);  
	}
	
	public int getPosition() {
		if (getTopLimitSwitch() == true) 
			return 1;
		else if(getBottomLimitSwitch() == true)
			return -1;
		else
			return 0;
	}
	
	public void tiltUpandDown(double position) {
		//pivotMotor.set(speed);
		setPIDs(position);
	}
	
	private void setPIDs(double desiredPosition) {
		double motorOutput = pivotMotor.getOutputVoltage() / pivotMotor.getBusVoltage();
		output = motorOutput;
		
		position = pivotMotor.getPosition();
		
		targetPositionRotations = desiredPosition * 1.0;
		pivotMotor.changeControlMode(TalonControlMode.Position);
    	pivotMotor.set(targetPositionRotations);
    	
    	errNative = pivotMotor.getClosedLoopError();
    	trg = targetPositionRotations;
    	
    	printPIDValues();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
    
    public boolean getTopLimitSwitch(){
		return !pivotMotor.isRevLimitSwitchClosed();
	}
    
	public boolean getBottomLimitSwitch(){
		return !pivotMotor.isFwdLimitSwitchClosed();
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
	
	private void printPIDValues() {
		SmartDashboard.putNumber("PID_output", output);
		SmartDashboard.putNumber("PID_position", position);
		SmartDashboard.putNumber("PID_ErrNative", errNative);
		SmartDashboard.putNumber("PID_trg", trg);
	}
}

