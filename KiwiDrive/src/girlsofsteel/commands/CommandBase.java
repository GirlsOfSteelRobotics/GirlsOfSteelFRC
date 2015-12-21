package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.Command;
import girlsofsteel.OI;
import girlsofsteel.subsystems.Chassis;

public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems
    public static Chassis chassis = new Chassis();

    public static void init() {
        oi = new OI();
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}