package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.commands.collector.AngleCollectorOut;
import org.usfirst.frc.team3504.robot.commands.shack.ShackOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Release extends CommandGroup {

    public Release() {
        addSequential(new ShackOut());
        addSequential(new AngleCollectorOut());

    }
}
