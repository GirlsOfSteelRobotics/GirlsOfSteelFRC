package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.LiftHold;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem {

	public WPI_TalonSRX lift;
	private DigitalInput limitSwitch;
	
	private static double goalLiftPosition;
	public static final double LIFT_INCREMENT = -100; //TODO tune
	public static final double LIFT_SWITCH = -500; //TODO tune
	public static final double LIFT_SCALE = -1500; //TODO tune
	public static final double LIFT_MAX = -2000; //TODO tune
	public static final double LIFT_MIN = 0; //TODO tune
	
	public Lift() {
		lift = new WPI_TalonSRX(RobotMap.LIFT); 
		limitSwitch = new DigitalInput(RobotMap.LIMIT_SWITCH);
		lift.setSensorPhase(true);
		setupLiftFPID();
		goalLiftPosition = 0;
		System.out.println("Lift Constructed");
	}
	
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new LiftHold());
        System.out.println("Lift Default command initialized");
    }
    
    public void setupLiftFPID() {
		//talon.setPosition(0); //TODO figure out new syntax
    	lift.config_kF(0, 0, 10);
		lift.config_kP(0, 1.5, 10);
		lift.config_kI(0, 0.001, 10);
		lift.config_kD(0, 0, 10);	
	}
    
   
//    public WPI_TalonSRX getLiftTalon() {
//    	return lift;
//    }
    
    public void setLiftSpeed(double speed) {
    		lift.set(speed); //value between -1.0 and 1.0;
    }

    public void stop() {
    		lift.stopMotor();
    }
	
	public boolean getLimitSwitch(){
		return limitSwitch.get();
	}
	
	public double getGoalLiftPosition()
	{
		return goalLiftPosition;
	}
	
	public void setGoalLiftPosition(double goal)
	{
		goalLiftPosition = goal;
	}
	
	public void holdLiftPosition()
	{
		lift.set(ControlMode.Position, goalLiftPosition);
		System.out.println("GoalLiftPosition: " + goalLiftPosition);
	}
	public void setLiftToScale()
	{
		goalLiftPosition = LIFT_SCALE;
	}
	public void setLiftToSwitch()
	{
		goalLiftPosition = LIFT_SWITCH;
	}
	
	public void setLiftToGround()
	{
		goalLiftPosition = LIFT_MIN;
	}
	
	public void incrementLift()
	{
		double currentPosition = goalLiftPosition;
		double goalPosition = currentPosition + Lift.LIFT_INCREMENT;
		if (goalPosition <= Lift.LIFT_MAX)
		{
			Robot.lift.setGoalLiftPosition(Lift.LIFT_MAX);
		}
		else
		{
			Robot.lift.setGoalLiftPosition(goalPosition);
		}
		System.out.println("Lift incremented");
	}
	
	public void decrementLift()
	{
		double currentPosition = goalLiftPosition;
		double goalPosition = currentPosition - Lift.LIFT_INCREMENT;
		if (goalPosition >= Lift.LIFT_MIN)
		{
			Robot.lift.setGoalLiftPosition(Lift.LIFT_MIN);
		}
		else
		{
			Robot.lift.setGoalLiftPosition(goalPosition);
		}
		System.out.println("Lift decremented");
	}
}

