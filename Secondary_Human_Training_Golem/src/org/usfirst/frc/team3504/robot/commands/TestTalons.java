//RUNS FROM AUTONOMOUS CHOOSER (SAME AS AUTONOMOUS COMMANDS), ENABLE IN AUTONOMOUS MODE TO RUN
//ONCE DONE WITH THIS CODE, TURN OFF ROBOT TO ENSURE TALONS FOLLOW CORRECTLY

package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Command;

public class TestTalons extends Command {
	private CANTalon leftTalonB = Robot.chassis.getLeftTalonB();
	private CANTalon leftTalonC = Robot.chassis.getLeftTalonC();
	private CANTalon rightTalonB = Robot.chassis.getRightTalonB();
	private CANTalon rightTalonC = Robot.chassis.getRightTalonC();
	
	private int talonNumber;

    public TestTalons(int talonNum) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);

    	talonNumber = talonNum;
    	requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.chassis.getLeftTalonB().changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	Robot.chassis.getLeftTalonC().changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	Robot.chassis.getRightTalonB().changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	Robot.chassis.getRightTalonC().changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(talonNumber) {
	    	case 1 : Robot.chassis.getLeftTalon().set(1);
	    		break;
	    	case 2 : leftTalonB.set(1);
	    		break;
	    	case 3 : leftTalonC.set(1);
	    		break;
	    	case 4 : Robot.chassis.getRightTalon().set(1);
	    		break;
	    	case 5 : rightTalonB.set(1);
	    		break;
	    	case 6 : rightTalonC.set(1);
	    		break;
	    	default : System.out.println("Invalid talon number");
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	leftTalonB.changeControlMode(CANTalon.TalonControlMode.Follower);
    	leftTalonC.changeControlMode(CANTalon.TalonControlMode.Follower);
		rightTalonB.changeControlMode(CANTalon.TalonControlMode.Follower);
		rightTalonC.changeControlMode(CANTalon.TalonControlMode.Follower);
		leftTalonB.set(Robot.chassis.getLeftTalon().getDeviceID());
		leftTalonC.set(Robot.chassis.getLeftTalon().getDeviceID());
		rightTalonB.set(Robot.chassis.getRightTalon().getDeviceID());
		rightTalonC.set(Robot.chassis.getRightTalon().getDeviceID());
    }

    // Called when another command whi;ch requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
