package com.gos.aerial_assist.commands;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 *
 * @author Author
 */
public abstract class CommandBase extends edu.wpi.first.wpilibj2.command.CommandBase {

    public CommandBase(String name) {
        setName(name);
    }

    public CommandBase() {
        super();
    }
}
