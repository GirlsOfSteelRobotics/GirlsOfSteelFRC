package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousKey extends CommandGroup {
    
    public AutonomousKey(){
        addSequential(new Shoot(CommandBase.shooter.KEY_SPEED));
    }
    
}
