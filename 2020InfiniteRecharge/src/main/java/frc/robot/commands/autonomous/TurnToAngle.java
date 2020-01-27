package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chassis;

public class TurnToAngle extends CommandBase {

    Chassis chassis;

    private double m_angle;
    private double m_allowableError;
    private double m_error;

    private double AUTO_KP = 0.05;

	public TurnToAngle(Chassis chassis, double angle, double allowableError) {
		// Use requires() here to declare subsystem dependencies
        //super.addRequirements(Shooter); When a subsystem is written, add the requires line back in.
        this.chassis = chassis;

        m_angle = angle;
        m_allowableError = allowableError;
	}

    public void initialize(){
    }

	// Called repeatedly when this Command is scheduled to run
	public void execute() { 
        double currentAngle;

        currentAngle = chassis.getHeading();

        m_error = m_angle - currentAngle;

        double turnSpeed = m_error * AUTO_KP;

        chassis.setSpeedAndSteer(0, turnSpeed);

        //System.out.println("error:" + m_error + "speed:" + speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
        if(Math.abs(m_error) < m_allowableError){
            System.out.println("Done!");
            return true;
        }
        else {
            System.out.println("Turn to angle" + "error:" + m_error + "allowableError" + m_allowableError);
            return false;
        }
	}

	// Called once after isFinished returns true
	public void end(boolean interrupted) {
        chassis.setSpeed(0);
  }
}