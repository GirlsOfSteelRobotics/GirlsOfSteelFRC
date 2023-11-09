package com.gos.stronghold.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.stronghold.robot.subsystems.Chassis;

/**
 *
 */
public class AutoDriveForward extends Command {

    private final Chassis m_chassis;
    private final double m_inches;
    private final double m_speed;

    public AutoDriveForward(Chassis chassis, double distance, double speed) {
        m_chassis = chassis;
        addRequirements(m_chassis);
        m_inches = distance;
        m_speed = speed;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_chassis.resetEncoderDistance();
        System.out.println("Encoder distance initially: " + m_chassis.getEncoderDistance());
        System.out.println("Inches: " + m_inches);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_chassis.driveSpeed(m_speed);

        System.out.println("Encoder distance: " + m_chassis.getEncoderDistance());

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return m_chassis.getEncoderDistance() >= Math.abs(m_inches); //competition bot
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_chassis.stop();
        System.out.println("Stopped");
    }


}
