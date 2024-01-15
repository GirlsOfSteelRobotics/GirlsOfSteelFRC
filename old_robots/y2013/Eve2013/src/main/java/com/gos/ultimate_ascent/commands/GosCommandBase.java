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
public abstract class GosCommandBase extends Command {
    protected Timer m_timer;

    public GosCommandBase(String name) {
        setName(name);
    }

    public GosCommandBase() {
        super();
    }

    protected double timeSinceInitialized() {
        return m_timer.get();
    }
}
