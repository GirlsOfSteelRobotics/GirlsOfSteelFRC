package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftReset extends Command {

	private WPI_TalonSRX liftTalon = Robot.lift.getLiftTalon();
	
    public LiftReset() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    		requires(Robot.lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lift.setupFPID(Robot.lift.getLiftTalon());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	liftTalon.set(ControlMode.PercentOutput, -0.5); //TODO
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.lift.getLimitSwitch();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lift.stop();
    	liftTalon.setSelectedSensorPosition(0,0,0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}