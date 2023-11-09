package com.gos.rebound_rumble.commands;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

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
