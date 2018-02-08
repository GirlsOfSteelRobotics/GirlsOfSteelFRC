package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftToSwitch extends Command {
	
	private WPI_TalonSRX liftTalon = Robot.lift.lift;
	
	public static final double inches = -5; //TODO: find distance in inches up to switch from 'home'
	
	private double encoderTicks; //in sensor units
	private double liftInitial;

    public LiftToSwitch() {
    		double rotations = inches / (RobotMap.WHEEL_DIAMETER * Math.PI);
		encoderTicks = RobotMap.CODES_PER_WHEEL_REV * rotations;
		
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    		requires(Robot.lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    		System.out.println("LiftToSwitch Started " + encoderTicks);

		liftInitial = liftTalon.getSelectedSensorPosition(0);

		liftTalon.set(ControlMode.Position, (encoderTicks + liftInitial));

		System.out.println("LiftInitial: " + liftInitial);
    		
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		liftTalon.set(ControlMode.Position, (encoderTicks + liftInitial));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    		if (encoderTicks > 0) {
			if (liftTalon.getSelectedSensorPosition(0) > (encoderTicks + liftInitial))
			{
				System.out.println("Finish Case #1");
				return true;
			}
			else return false;
		} else if (encoderTicks < 0) {
			if (liftTalon.getSelectedSensorPosition(0) < (encoderTicks + liftInitial))
			{
				System.out.println("Finish Case #2");
				return true;
			}
			else return false;
		} else {
			System.out.println("Finish Case #3");
			return true;
		}
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
