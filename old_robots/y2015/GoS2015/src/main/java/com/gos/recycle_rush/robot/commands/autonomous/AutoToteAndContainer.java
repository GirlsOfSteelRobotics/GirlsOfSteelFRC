package com.gos.recycle_rush.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.recycle_rush.robot.subsystems.Chassis;
import com.gos.recycle_rush.robot.subsystems.Collector;
import com.gos.recycle_rush.robot.subsystems.Lifter;
import com.gos.recycle_rush.robot.subsystems.Shack;

/**
 *
 */
public class AutoToteAndContainer extends SequentialCommandGroup {

    // collects one tote and one container and takes them to the auto zone
    private double m_distanceLeft1;
    private double m_distanceFirst1;

    public AutoToteAndContainer(Chassis chassis, Shack shack, Collector collector, Lifter lifter) {
        addCommands(new AutoCollector(collector));
        addCommands(new Lifting(shack, collector, lifter));
        addCommands(new AutoFirstPickup(chassis, m_distanceFirst1));
        addCommands(new AutoCollector(collector));
        addCommands(new Lifting(shack, collector, lifter));
        addCommands(new AutoDriveLeft(chassis, m_distanceLeft1));
    }
}
