package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoToteAndContainer extends CommandGroup {

    // collects one tote and one container and takes them to the auto zone
    double distanceLeft1;
    double distanceFirst1;

    public AutoToteAndContainer() {
        addSequential(new AutoCollector());
        addSequential(new Lifting());
        addSequential(new AutoFirstPickup(distanceFirst1));
        addSequential(new AutoCollector());
        addSequential(new Lifting());
        addSequential(new AutoDriveLeft(distanceLeft1));
    }
}
