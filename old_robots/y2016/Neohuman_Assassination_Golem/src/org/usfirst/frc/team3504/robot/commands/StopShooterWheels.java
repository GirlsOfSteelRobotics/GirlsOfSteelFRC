package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.subsystems.Claw;
import org.usfirst.frc.team3504.robot.subsystems.Shooter;

/**
 *
 */
public class StopShooterWheels extends Command {

    private final Claw m_claw;
    private final Shooter m_shooter;

    public StopShooterWheels(Claw claw, Shooter shooter) {
        m_claw = claw;
        m_shooter = shooter;
        
        if(RobotMap.USING_CLAW) {
            requires(m_claw);
        }
        else {
            requires(m_shooter);    }}

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if(RobotMap.USING_CLAW) {
            m_claw.stopCollecting();
        }
        else {
            m_shooter.stop();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
