package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj2.command.Command;

/**
 * The base for all commands. All atomic commands should subclass Command.
 * Command stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use Command.exampleSubsystem
 *
 * @author Author
 */
public abstract class GosCommandBaseBase extends Command {

    public GosCommandBaseBase(String name) {
        setName(name);
    }

    public GosCommandBaseBase() {
        super();
    }
}
