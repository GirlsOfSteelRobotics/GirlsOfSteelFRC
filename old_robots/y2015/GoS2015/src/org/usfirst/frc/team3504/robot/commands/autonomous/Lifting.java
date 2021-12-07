package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.commands.collector.AngleCollectorIn;
import org.usfirst.frc.team3504.robot.commands.collector.AngleCollectorOut;
import org.usfirst.frc.team3504.robot.commands.shack.ShackIn;
import org.usfirst.frc.team3504.robot.commands.shack.ShackOut;
import org.usfirst.frc.team3504.robot.subsystems.Collector;
import org.usfirst.frc.team3504.robot.subsystems.Lifter;
import org.usfirst.frc.team3504.robot.subsystems.Shack;


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
