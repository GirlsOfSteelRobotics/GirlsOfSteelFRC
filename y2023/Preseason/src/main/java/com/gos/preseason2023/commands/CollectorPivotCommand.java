package com.gos.preseason2023.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.preseason2023.subsystems.CollectorExampleSubsystem;


public class CollectorPivotCommand extends CommandBase {
    private final CollectorExampleSubsystem m_collector;
    private final double m_speed;

    // parameters -- pass in subsystems & info (ex: we need varying speed)
    public CollectorPivotCommand(CollectorExampleSubsystem collectorExampleSubsystem, double speed) {
        this.m_collector = collectorExampleSubsystem;
        m_speed = speed;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_collector);
    }

    // do we want to do anything before we start running the command?
    @Override
    public void initialize() {

    }

    // what do we want to do every time we loop through the command?
    // ALWAYS code in execute
    @Override
    public void execute() {
        m_collector.movePivot(m_speed);
    }

    // besides letting go of the button, is there a scenario we want to stop running the command?
    @Override
    public boolean isFinished() {
        return false;
    }

    // when we stop running the command, do we want to do anything?
    @Override
    public void end(boolean interrupted) {
        m_collector.movePivot(0);
    }
}
