package com.gos.recycle_rush.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.recycle_rush.robot.commands.collector.AngleCollectorOut;
import com.gos.recycle_rush.robot.commands.shack.ShackOut;
import com.gos.recycle_rush.robot.subsystems.Collector;
import com.gos.recycle_rush.robot.subsystems.Shack;

/**
 *
 */
public class Release extends SequentialCommandGroup {

    public Release(Shack shack, Collector collector) {
        addCommands(new ShackOut(shack));
        addCommands(new AngleCollectorOut(collector));

    }
}
