package com.gos.recycle_rush.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.recycle_rush.robot.subsystems.Chassis;
import com.gos.recycle_rush.robot.subsystems.Collector;
import com.gos.recycle_rush.robot.subsystems.Lifter;
import com.gos.recycle_rush.robot.subsystems.Shack;

/**
 *
 */
public class AutoOneTote extends CommandGroup {

    // Collects one tote/container and takes it to the auto zone
    private final double m_distanceRight1;

    public AutoOneTote(Chassis chassis, Collector collector, Shack shack, Lifter lifter) {
        m_distanceRight1 = 50; // 107 (50 being used for testing)

        addSequential(new AutoCollector(collector));
        addSequential(new Lifting(shack, collector, lifter));
        addSequential(new AutoDriveRight(chassis, m_distanceRight1));
        addSequential(new Release(shack, collector));
    }
}
