package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.OI;
import girlsofsteel.subsystems.Shooter;

public class AutonomousKey extends CommandGroup {

    public AutonomousKey(Shooter shooter, OI oi){
        addSequential(new Shoot(shooter, oi, Shooter.KEY_SPEED));
    }

}
