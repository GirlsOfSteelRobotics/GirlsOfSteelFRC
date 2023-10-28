package com.gos.ultimate_ascent.commands;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use
 * CommandBase.exampleSubsystem
 *
 * @author Author
 */
@SuppressWarnings({"PMD.GodClass", "PMD.DataClass"})
public abstract class GosCommandBase extends CommandBase {
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
