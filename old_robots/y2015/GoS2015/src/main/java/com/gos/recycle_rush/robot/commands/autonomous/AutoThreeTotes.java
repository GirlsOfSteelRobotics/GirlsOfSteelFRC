package com.gos.recycle_rush.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.recycle_rush.robot.subsystems.Chassis;
import com.gos.recycle_rush.robot.subsystems.Collector;
import com.gos.recycle_rush.robot.subsystems.Lifter;
import com.gos.recycle_rush.robot.subsystems.Shack;


/**
 *
 */
public class AutoThreeTotes extends SequentialCommandGroup {
    private static final double distanceFwd1 = 55;
    private static final double distanceFwd2 = 55;
    private static final double distanceLeft1 = 107;
    private static final double distanceBack1 = 50;
    private static final double distanceFirst1 = 22.25;

    public AutoThreeTotes(Chassis chassis, Shack shack, Collector collector, Lifter lifter) {
        addCommands(new AutoCollector(collector));
        addCommands(new Lifting(shack, collector, lifter));
        addCommands(new AutoFirstPickup(chassis, distanceFirst1));
        addCommands(new AutoCollector(collector));
        addCommands(new Lifting(shack, collector, lifter));
        // used to get first can and tote

        addCommands(new AutoCollector(collector));
        addCommands(new AutoDriveForward(chassis, distanceFwd1));
        addCommands(new Lifting(shack, collector, lifter));
        // gets middle tote assuming partner cleared second can

        addCommands(new AutoCollector(collector));
        addCommands(new AutoDriveForward(chassis, distanceFwd2));
        addCommands(new Lifting(shack, collector, lifter));
        // gets last tote assuming partner cleared third can

        addCommands(new AutoDriveLeft(chassis, distanceLeft1));
        addCommands(new Release(shack, collector));
        addCommands(new AutoDriveBackwards(chassis, distanceBack1));
        // turn into the autozone to get robot set
    }
}
