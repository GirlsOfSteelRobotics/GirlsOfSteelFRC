package com.gos.preseason2017.team2.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.preseason2017.team2.robot.subsystems.Manipulator;

/**
 *
 */
public class Shoot extends SequentialCommandGroup {

    public Shoot(Manipulator manipulator) {
        addCommands(new Release(manipulator).withTimeout(1.0));
        addCommands(new PusherOut(manipulator));
        addCommands(new StopShooter(manipulator));
    }
}
