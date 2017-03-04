package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StayClimbed extends Command {

	private double encPositionA;
	private double encPositionB;
	
    public StayClimbed() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.climber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.climber.climbMotorA.changeControlMode(TalonControlMode.Position);
    	Robot.climber.climbMotorB.changeControlMode(TalonControlMode.Position);
		
    	Robot.climber.climbMotorA.setF(0);
    	Robot.climber.climbMotorA.setP(1.0); 
    	Robot.climber.climbMotorA.setI(0);
    	Robot.climber.climbMotorA.setD(0);
    	Robot.climber.climbMotorB.setF(0);
    	Robot.climber.climbMotorB.setP(1.0); 
    	Robot.climber.climbMotorB.setI(0);
    	Robot.climber.climbMotorB.setD(0);
    	
    	encPositionA = Robot.climber.climbMotorA.getEncPosition();
    	System.out.println("Climber Encoder Position A: " + encPositionA);
    	encPositionB = Robot.climber.climbMotorB.getEncPosition();
    	System.out.println("Climber Encoder Position B: " + encPositionB);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.climber.climbMotorA.set(encPositionA);
    	Robot.climber.climbMotorB.set(encPositionB);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
