package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chassis;

public class FollowTrajectory extends CommandBase {

    Chassis m_chassis;


    public FollowTrajectory(Trajectory trajectory, Chassis chassis) {

        // Use requires() here to declare subsystem dependencies
        //super.addRequirements(Shooter); When a subsystem is written, add the requires line back in.
        this.m_chassis = chassis;
        // RamseteCommand ramseteCommand = new RamseteCommand(
        // exampleTrajectory,
        // m_robotDrive::getPose,
        // new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta),
        // new SimpleMotorFeedforward(DriveConstants.ksVolts,
        //                            DriveConstants.kvVoltSecondsPerMeter,
        //                            DriveConstants.kaVoltSecondsSquaredPerMeter),
        // DriveConstants.kDriveKinematics,
        // m_robotDrive::getWheelSpeeds,
        // new PIDController(DriveConstants.kPDriveVel, 0, 0),
        // new PIDController(DriveConstants.kPDriveVel, 0, 0),
        // // RamseteCommand passes volts to the callback
        // m_robotDrive::tankDriveVolts,
        // m_robotDrive
        // )
    }

    public void initialize(){
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {

    }
    
    

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() { 
        return false; 
    }
    

    // Called once after isFinished returns true
    public void end(boolean interrupted) {
    
    }
}
