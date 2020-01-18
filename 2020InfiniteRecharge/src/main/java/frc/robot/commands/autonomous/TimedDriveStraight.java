package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chassis;

public class TimedDriveStraight extends CommandBase {

  Chassis chassis;

  private Timer m_timer;
  private int m_waitTime;
  private double m_speed;

	public TimedDriveStraight(Chassis chassis , int waitTime, double speed) {
        this.chassis = chassis;
        m_waitTime = waitTime;
        m_speed = speed;
        
        m_timer = new Timer();

		// Use requires() here to declare subsystem dependencies
		super.addRequirements(chassis);
	}

    public void initialize(){
        m_timer.start();
    }

	// Called repeatedly when this Command is scheduled to run
	public void execute() { 
        chassis.setSpeed(m_speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
        if (m_timer.get() > m_waitTime){
            return true;
        }
		else {
            return false;
        }
	}

	// Called once after isFinished returns true
	public void end(boolean interrupted) {
		chassis.stop();
  }
}