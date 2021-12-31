package com.gos.recycle_rush.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.recycle_rush.robot.commands.collector.AngleCollectorIn;
import com.gos.recycle_rush.robot.commands.collector.AngleCollectorOut;
import com.gos.recycle_rush.robot.commands.shack.ShackIn;
import com.gos.recycle_rush.robot.commands.shack.ShackOut;
import com.gos.recycle_rush.robot.subsystems.Collector;
import com.gos.recycle_rush.robot.subsystems.Lifter;
import com.gos.recycle_rush.robot.subsystems.Shack;


/**
 *
 */
public class Lifting extends CommandGroup {

    public Lifting(Shack shack, Collector collector, Lifter lifter) {

        addSequential(new ShackOut(shack));
        addSequential(new AngleCollectorIn(collector));
        addSequential(new AutoCollector(collector));
        addSequential(new ShackIn(shack));
        addSequential(new AngleCollectorOut(collector));
        addSequential(new AutoLift(lifter, Lifter.DISTANCE_ONE_TOTE));
        // addSequential(new ShackOut());
        addSequential(new AutoLift(lifter, Lifter.DISTANCE_ZERO_TOTES));

    }
}
