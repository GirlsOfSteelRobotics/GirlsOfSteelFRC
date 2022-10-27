package com.gos.stronghold.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.stronghold.robot.subsystems.Chassis;

/**
 *
 */
public class AutoTurn extends CommandBase {

    private final Chassis m_chassis;
    private final double m_turnAmt;
    private final double m_speed;

    public AutoTurn(Chassis chassis, double turnAmt, double speed) {
        m_chassis = chassis;
        addRequirements(m_chassis);
        this.m_turnAmt = turnAmt;
        this.m_speed = speed;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_chassis.resetEncoderDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_chassis.drive(m_speed, -1);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return Math.abs(m_chassis.getEncoderDistance()) >= m_turnAmt; //competition bot
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_chassis.stop();
    }


}
