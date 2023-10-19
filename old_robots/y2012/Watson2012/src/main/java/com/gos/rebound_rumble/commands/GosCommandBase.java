package com.gos.rebound_rumble.commands;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

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
