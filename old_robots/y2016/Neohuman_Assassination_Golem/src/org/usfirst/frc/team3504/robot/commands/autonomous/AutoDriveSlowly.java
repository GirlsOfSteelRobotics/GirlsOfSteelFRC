package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;

/**
 *
 */
public class AutoDriveSlowly extends Command {

    private final Chassis m_chassis;
    private final double m_inches;

    public AutoDriveSlowly(Chassis chassis, double distance) {
        m_chassis = chassis;
        requires(m_chassis);
        m_inches = distance;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_chassis.resetEncoderDistance();
        System.out.println("Encoder distance initially: " + m_chassis.getEncoderDistance());
        System.out.println("Inches: " + m_inches);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_chassis.driveSpeed(-.4);

        System.out.println("Encoder distance: " + m_chassis.getEncoderDistance());

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return m_chassis.getEncoderDistance() <= -m_inches;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_chassis.stop();
        System.out.println("Stopped");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
