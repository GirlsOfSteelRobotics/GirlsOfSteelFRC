package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftDown extends Command {


	
    public LiftDown() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    		requires(Robot.lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	    	//liftTalon.set(ControlMode.PercentOutput, 1.0);
	    	System.out.println("LiftDown");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		Robot.lift.setLiftSpeed(1.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    		return false;
    		//return Robot.lift.getLimitSwitch();
    }

    // Called once after isFinished returns true
    protected void end() {
    		Robot.lift.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    		end();
    }
}