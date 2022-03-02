package com.gos.stronghold.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import com.gos.stronghold.robot.subsystems.Claw;
import com.gos.stronghold.robot.subsystems.Shooter;

/**
 *
 */
public class ShootBall extends SequentialCommandGroup {

    public ShootBall(Claw claw, Shooter shooter) {
        //operator lines up pivot
        addCommands(new ShooterPistonsOut(shooter)); //tilt shooter partially so that ball doesn't fall out
        addCommands(new ReleaseBall(claw, shooter)); //need to test exact seconds for delay
        addParallel(new ShooterPistonsIn(shooter)); //tilts shooter all the way up
        addParallel(new WaitCommand(5));
        addCommands(new StopShooterWheels(claw, shooter));
    }
}
