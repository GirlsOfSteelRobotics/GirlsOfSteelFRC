package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.subsystems.Lift;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftReset extends Command {

	
    public LiftReset() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    		requires(Robot.lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("LiftReset: initialize");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.lift.lift.set(ControlMode.PercentOutput, 0.2);
    	System.out.println("LiftReset: execute loop");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.lift.getLimitSwitch();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lift.lift.stopMotor();
    	
    	Robot.lift.lift.setSelectedSensorPosition(0, 0, 10);
    	System.out.println("LiftReset: ended");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    		end();
    }
}