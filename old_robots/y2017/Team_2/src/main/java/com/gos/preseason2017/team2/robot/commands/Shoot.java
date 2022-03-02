package com.gos.preseason2017.team2.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.preseason2017.team2.robot.subsystems.Manipulator;

/**
 *
 */
public class Shoot extends SequentialCommandGroup {

    public Shoot(Manipulator manipulator) {
        addParallel(new Release(manipulator));
        addCommands(new TimeDelay(1.0));
        addCommands(new PusherOut(manipulator));
        addCommands(new StopShooter(manipulator));
    }
}
