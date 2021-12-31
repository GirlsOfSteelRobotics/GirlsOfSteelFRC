package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use
 * CommandBase.exampleSubsystem
 *
 * @author Author
 */
@SuppressWarnings({"PMD.GodClass", "PMD.DataClass"})
public abstract class CommandBase extends Command {

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
