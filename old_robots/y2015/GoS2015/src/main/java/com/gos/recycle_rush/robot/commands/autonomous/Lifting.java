package com.gos.recycle_rush.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
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
public class Lifting extends SequentialCommandGroup {

    public Lifting(Shack shack, Collector collector, Lifter lifter) {

        addCommands(new ShackOut(shack));
        addCommands(new AngleCollectorIn(collector));
        addCommands(new AutoCollector(collector));
        addCommands(new ShackIn(shack));
        addCommands(new AngleCollectorOut(collector));
        addCommands(new AutoLift(lifter, Lifter.DISTANCE_ONE_TOTE));
        // addCommands(new ShackOut());
        addCommands(new AutoLift(lifter, Lifter.DISTANCE_ZERO_TOTES));

    }
}
