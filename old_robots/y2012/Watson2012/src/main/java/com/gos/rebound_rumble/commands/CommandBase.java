package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.command.Command;


public abstract class CommandBase extends Command {

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
