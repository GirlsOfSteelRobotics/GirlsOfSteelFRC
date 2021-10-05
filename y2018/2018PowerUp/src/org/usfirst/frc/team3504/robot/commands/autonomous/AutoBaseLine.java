package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoBaseLine extends Command {
	
	private int time;
	
	private WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
	private WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();

    public AutoBaseLine() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	    	time = 0;	    	
	    	leftTalon.set(ControlMode.PercentOutput, 0.5);
	    	rightTalon.set(ControlMode.PercentOutput, 0.5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	    	time++;
	    	leftTalon.set(ControlMode.PercentOutput, 0.5);
	    	rightTalon.set(ControlMode.PercentOutput, 0.5);		
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return time >= 500;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
