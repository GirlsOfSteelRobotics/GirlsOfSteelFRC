package com.gos.preseason2017.team1.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.preseason2017.team1.robot.subsystems.Collector;
import com.gos.preseason2017.team1.robot.subsystems.JawPiston;

/**
 *
 */
public class Collect extends CommandBase {

    private final JawPiston m_jaw;
    private final Collector m_collector;

    public Collect(JawPiston jaw, Collector collector) {
        m_jaw = jaw;
        m_collector = collector;
        addRequirements(m_jaw);
        addRequirements(m_collector);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        //Robot.arm.armDown();
        m_jaw.pistonsIn();
        m_collector.spinWheels(0.5); //TODO : test
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_collector.stop();
    }


}
