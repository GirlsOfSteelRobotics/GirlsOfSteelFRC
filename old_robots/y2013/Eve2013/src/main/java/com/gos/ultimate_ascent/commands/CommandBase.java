package com.gos.ultimate_ascent.commands;


import edu.wpi.first.wpilibj.Timer;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use
 * CommandBase.exampleSubsystem
 *
 * @author Author
 */
@SuppressWarnings({"PMD.GodClass", "PMD.DataClass"})
public abstract class CommandBase extends edu.wpi.first.wpilibj2.command.CommandBase {
    protected Timer m_timer;

    public CommandBase(String name) {
        setName(name);
    }

    public CommandBase() {
        super();
    }

    protected double timeSinceInitialized() {
        return m_timer.get();
    }
}
