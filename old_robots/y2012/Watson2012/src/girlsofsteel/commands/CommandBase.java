package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.Command;
import girlsofsteel.OI;
import girlsofsteel.subsystems.Bridge;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Collector;
import girlsofsteel.subsystems.Shooter;
import girlsofsteel.subsystems.Turret;


public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems
    public static final Bridge bridge = new Bridge();
    public static final Chassis chassis = new Chassis();
    public static final Collector collector = new Collector();
    public static final Shooter shooter = new Shooter();
    public static final Turret turret = new Turret();

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
