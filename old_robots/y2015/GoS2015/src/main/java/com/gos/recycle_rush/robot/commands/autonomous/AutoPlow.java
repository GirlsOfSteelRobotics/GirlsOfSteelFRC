package com.gos.recycle_rush.robot.commands.autonomous;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.recycle_rush.robot.subsystems.Chassis;
import com.gos.recycle_rush.robot.subsystems.Collector;
import com.gos.recycle_rush.robot.subsystems.Lifter;
import com.gos.recycle_rush.robot.subsystems.Shack;

/**
 *
 */
public class AutoPlow extends SequentialCommandGroup {

    // collects one container and three totes and takes them to the autozone
    private static final double DISTANCE_FWD_1 = 55; // was 82.15 distance on field is actually 55in
    private static final double DISTANCE_FWD_2 = 55; // was 82.15 distance on field is actually 55in
    private static final double DISTANCE_LEFT_1 = 107;
    private static final double DISTANCE_BACK_1 = 50;
    private static final double DISTANCE_FIRST_1 = 22.25;

    public AutoPlow(Chassis chassis, Shack shack, Collector collector, Lifter lifter) {
        addCommands(new AutoCollector(collector));
        addCommands(new Lifting(shack, collector, lifter));
        addCommands(new AutoFirstPickup(chassis, DISTANCE_FIRST_1));
        addCommands(new AutoCollector(collector));
        addCommands(new Lifting(shack, collector, lifter));
        // used to get first can and tote

        addCommands(new AutoCollector(collector));
        addCommands(new AutoDriveForward(chassis, DISTANCE_FWD_1));
        addCommands(new Lifting(shack, collector, lifter));
        // gets middle tote assuming partner cleared second can

        addCommands(new AutoCollector(collector));
        addCommands(new AutoDriveForward(chassis, DISTANCE_FWD_2));
        addCommands(new Lifting(shack, collector, lifter));
        // gets last tote assuming partner cleared third can

        addCommands(new AutoDriveLeft(chassis, DISTANCE_LEFT_1));
        addCommands(new Release(shack, collector));
        addCommands(new AutoDriveBackwards(chassis, DISTANCE_BACK_1));
        // turn into the autozone to get robot set
    }
}
