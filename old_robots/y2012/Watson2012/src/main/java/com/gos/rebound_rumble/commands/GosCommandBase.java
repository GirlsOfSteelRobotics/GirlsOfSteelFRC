package com.gos.rebound_rumble.commands;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public abstract class GosCommandBaseBase extends Command {

    protected Timer m_timer;

    public GosCommandBaseBase(String name) {
        setName(name);
    }

    public GosCommandBaseBase() {
        super();
    }

    protected double timeSinceInitialized() {
        return m_timer.get();
    }
}
