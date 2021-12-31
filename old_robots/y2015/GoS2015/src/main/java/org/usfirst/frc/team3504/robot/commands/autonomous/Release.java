package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.commands.collector.AngleCollectorOut;
import org.usfirst.frc.team3504.robot.commands.shack.ShackOut;
import org.usfirst.frc.team3504.robot.subsystems.Collector;
import org.usfirst.frc.team3504.robot.subsystems.Shack;

/**
 *
 */
public class Release extends CommandGroup {

    public Release(Shack shack, Collector collector) {
        addSequential(new ShackOut(shack));
        addSequential(new AngleCollectorOut(collector));

    }
}
