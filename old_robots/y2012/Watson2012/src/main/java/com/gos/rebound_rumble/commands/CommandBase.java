package com.gos.rebound_rumble.commands;


import edu.wpi.first.wpilibj.Timer;

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
