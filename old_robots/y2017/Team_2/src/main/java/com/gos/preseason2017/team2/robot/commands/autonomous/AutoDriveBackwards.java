package com.gos.preseason2017.team2.robot.commands.autonomous;


import edu.wpi.first.wpilibj2.command.Command;
import com.gos.preseason2017.team2.robot.subsystems.Chassis;


/**
 *
 */
public class AutoDriveBackwards extends Command {

    @SuppressWarnings("unused")
    private final double m_inches;
    private final double m_speed;
    private final Chassis m_chassis;


    public AutoDriveBackwards(Chassis chassis, double inches, double speed) {
        addRequirements(chassis);
        m_inches = inches;
        m_speed = speed;
        m_chassis = chassis;
    }


    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }


    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_chassis.driveSpeed(-m_speed);
    }


    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }


    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_chassis.stop();
    }



}
