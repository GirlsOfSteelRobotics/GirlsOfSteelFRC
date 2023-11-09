package com.gos.ultimate_ascent.commands;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * The base for all commands. All atomic commands should subclass Command.
 * Command stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use
 * Command.exampleSubsystem
 *
 * @author Author
 */
@SuppressWarnings({"PMD.GodClass", "PMD.DataClass"})
public abstract class GosCommand extends Command {
    protected Timer m_timer;

    public GosCommand(String name) {
        setName(name);
    }

    public GosCommand() {
        super();
    }

    protected double timeSinceInitialized() {
        return m_timer.get();
    }
}
