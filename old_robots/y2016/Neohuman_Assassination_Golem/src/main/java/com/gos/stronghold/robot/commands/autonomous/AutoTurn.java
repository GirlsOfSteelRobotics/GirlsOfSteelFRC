package com.gos.stronghold.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.stronghold.robot.subsystems.Chassis;

/**
 *
 */
public class AutoTurn extends Command {

    private final Chassis m_chassis;
    private final double m_turnAmt;
    private final double m_speed;

    public AutoTurn(Chassis chassis, double turnAmt, double speed) {
        m_chassis = chassis;
        requires(m_chassis);
        this.m_turnAmt = turnAmt;
        this.m_speed = speed;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_chassis.resetEncoderDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_chassis.drive(m_speed, -1);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Math.abs(m_chassis.getEncoderDistance()) >= m_turnAmt; //competition bot
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
