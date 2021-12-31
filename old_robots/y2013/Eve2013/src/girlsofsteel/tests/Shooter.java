package girlsofsteel.tests;

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.subsystems.Feeder;

public class Shooter extends CommandGroup {

    public Shooter(Feeder feeder, girlsofsteel.subsystems.Shooter shooter) {
        addSequential(new ShooterJags(feeder, shooter));
        addSequential(new ShooterPID(shooter));
    }

}
