package com.gos.preseason2017.team2.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.preseason2017.team2.robot.subsystems.Manipulator;

/**
 *
 */
public class Shoot extends CommandGroup {

    public Shoot(Manipulator manipulator) {
        addParallel(new Release(manipulator));
        addSequential(new TimeDelay(1.0));
        addSequential(new PusherOut(manipulator));
        addSequential(new StopShooter(manipulator));
    }
}
