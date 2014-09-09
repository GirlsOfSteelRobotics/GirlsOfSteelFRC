package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author arushibandi
 */
public class AutonomousMobility extends CommandGroup {

    public AutonomousMobility() {
        addSequential(new MoveToPositionLSPB(2.5)); //should move 2.5 meters forward
    }    
}
