package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.subsystems.Manipulator;

/**
 *
 */
public class Shoot extends CommandGroup {

    public  Shoot(Manipulator manipulator) {
        addParallel(new Release(manipulator));
        addSequential(new TimeDelay(1.0));
        addSequential(new PusherOut(manipulator));
        addSequential(new StopShooter(manipulator));
    }
}
