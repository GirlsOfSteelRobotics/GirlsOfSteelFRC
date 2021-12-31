package com.gos.recycle_rush.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.recycle_rush.robot.commands.collector.AngleCollectorOut;
import com.gos.recycle_rush.robot.commands.shack.ShackOut;
import com.gos.recycle_rush.robot.subsystems.Collector;
import com.gos.recycle_rush.robot.subsystems.Shack;

/**
 *
 */
public class Release extends CommandGroup {

    public Release(Shack shack, Collector collector) {
        addSequential(new ShackOut(shack));
        addSequential(new AngleCollectorOut(collector));

    }
}
