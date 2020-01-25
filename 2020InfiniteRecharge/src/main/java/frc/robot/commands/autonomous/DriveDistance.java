package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chassis;

public class DriveDistance extends CommandBase {

    Chassis chassis;

    private double m_distance;
    private double m_allowableError;
    private double m_error;

    private double AUTO_KP = 0.1;

	public DriveDistance(Chassis chassis, double distance, double allowableError) {
		// Use requires() here to declare subsystem dependencies
        //super.addRequirements(Shooter); When a subsystem is written, add the requires line back in.
        this.chassis = chassis;

        m_distance = distance + chassis.getAverageEncoderDistance();
        m_allowableError = allowableError;
	}

    public void initialize(){
    }

	// Called repeatedly when this Command is scheduled to run
	public void execute() { 
        double currentDistance;

        currentDistance = chassis.getAverageEncoderDistance();

        m_error = m_distance - currentDistance;

        double speed = m_error * AUTO_KP;

        chassis.setSpeed(speed);

        //System.out.println("error:" + m_error + "speed:" + speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
        if(Math.abs(m_error) < m_allowableError){
            System.out.println("Done!");
            return true;
        }
        else {
            System.out.println("drive to distance" + "error:" + m_error + "allowableError" + m_allowableError);
            return false;
        }
	}

	// Called once after isFinished returns true
	public void end(boolean interrupted) {
        chassis.setSpeed(0);
  }
}