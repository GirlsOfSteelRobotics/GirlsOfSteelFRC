package com.gos.rebound_rumble.commands;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

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
