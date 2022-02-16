package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import edu.wpi.first.wpilibj.Timer;


public class VerticalConveyorTimedCommand extends CommandBase {
    @SuppressWarnings("checkstyle:MemberName")
    private final VerticalConveyorSubsystem m_verticalConveyorSubsystem;
    private double m_seconds;
    private final Timer m_timer = new Timer();

    public VerticalConveyorTimedCommand(VerticalConveyorSubsystem verticalConveyorSubsystem, double seconds) {
        this.m_verticalConveyorSubsystem = verticalConveyorSubsystem;
        this.m_seconds = seconds;
        // each subsystem used by the command must be passed into the addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_verticalConveyorSubsystem);
    }

    @Override
    public void initialize() {
        m_timer.reset();
        m_timer.start();
    }

    @Override
    public void execute() {
        m_verticalConveyorSubsystem.forwardVerticalConveyorMotor();
    }

    @Override
    public boolean isFinished() {
        return !(m_timer.get() < m_seconds);
    }

    @Override
    public void end(boolean interrupted) {

    }
}
