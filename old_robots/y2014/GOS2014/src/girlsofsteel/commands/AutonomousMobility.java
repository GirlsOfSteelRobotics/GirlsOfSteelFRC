package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Driving;

/**
 *
 * @author arushibandi
 */
public class AutonomousMobility extends CommandGroup {

    public AutonomousMobility(Chassis chassis, Driving driving) {
        addSequential(new MoveToPositionLSPB(chassis, driving, 2.5)); //should move 2.5 meters forward
    }
}
