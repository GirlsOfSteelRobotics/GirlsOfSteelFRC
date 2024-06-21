package com.gos.preseason2023.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.preseason2023.subsystems.CollectorExampleSubsystem;


public class CollectorRollerCommand extends Command {
    private final CollectorExampleSubsystem m_collector;
    private final double m_speed;

    public CollectorRollerCommand(CollectorExampleSubsystem collectorExampleSubsystem, double speed) {
        this.m_collector = collectorExampleSubsystem;
        m_speed = speed;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_collector);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_collector.moveRoller(m_speed);
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_collector.moveRoller(0);
    }
}
