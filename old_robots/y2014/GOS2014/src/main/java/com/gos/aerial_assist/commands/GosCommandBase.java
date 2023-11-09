package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj2.command.Command;

/**
 * The base for all commands. All atomic commands should subclass Command.
 * Command stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use Command.exampleSubsystem
 *
 * @author Author
 */
public abstract class GosCommand extends Command {

    public GosCommand(String name) {
        setName(name);
    }

    public GosCommand() {
        super();
    }
}
