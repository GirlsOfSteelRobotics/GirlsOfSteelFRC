package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;
import org.usfirst.frc.team3504.robot.subsystems.Collector;
import org.usfirst.frc.team3504.robot.subsystems.Lifter;
import org.usfirst.frc.team3504.robot.subsystems.Shack;


/**
 *
 */
public class AutoThreeTotes extends CommandGroup {
    private static final double distanceFwd1 = 55;
    private static final double distanceFwd2 = 55;
    private static final double distanceLeft1 = 107;
    private static final double distanceBack1 = 50;
    private static final double distanceFirst1 = 22.25;

    public AutoThreeTotes(Chassis chassis, Shack shack, Collector collector, Lifter lifter) {
        addSequential(new AutoCollector(collector));
        addSequential(new Lifting(shack, collector, lifter));
        addSequential(new AutoFirstPickup(chassis, distanceFirst1));
        addSequential(new AutoCollector(collector));
        addSequential(new Lifting(shack, collector, lifter));
        // used to get first can and tote

        addSequential(new AutoCollector(collector));
        addSequential(new AutoDriveForward(chassis, distanceFwd1));
        addSequential(new Lifting(shack, collector, lifter));
        // gets middle tote assuming partner cleared second can

        addSequential(new AutoCollector(collector));
        addSequential(new AutoDriveForward(chassis, distanceFwd2));
        addSequential(new Lifting(shack, collector, lifter));
        // gets last tote assuming partner cleared third can

        addSequential(new AutoDriveLeft(chassis, distanceLeft1));
        addSequential(new Release(shack, collector));
        addSequential(new AutoDriveBackwards(chassis, distanceBack1));
        // turn into the autozone to get robot set
    }
}
