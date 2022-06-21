package com.gos.recycle_rush.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.recycle_rush.robot.subsystems.Chassis;
import com.gos.recycle_rush.robot.subsystems.Collector;
import com.gos.recycle_rush.robot.subsystems.Lifter;
import com.gos.recycle_rush.robot.subsystems.Shack;

/**
 *
 */
public class AutoOneTote extends SequentialCommandGroup {

    // Collects one tote/container and takes it to the auto zone
    private final double m_distanceRight1;

    public AutoOneTote(Chassis chassis, Collector collector, Shack shack, Lifter lifter) {
        m_distanceRight1 = 50; // 107 (50 being used for testing)

        addCommands(new AutoCollector(collector));
        addCommands(new Lifting(shack, collector, lifter));
        addCommands(new AutoDriveRight(chassis, m_distanceRight1));
        addCommands(new Release(shack, collector));
    }
}
