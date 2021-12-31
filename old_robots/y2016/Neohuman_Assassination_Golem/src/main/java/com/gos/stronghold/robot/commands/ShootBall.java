package com.gos.stronghold.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import com.gos.stronghold.robot.subsystems.Claw;
import com.gos.stronghold.robot.subsystems.Shooter;

/**
 *
 */
public class ShootBall extends CommandGroup {

    public ShootBall(Claw claw, Shooter shooter) {
        //operator lines up pivot
        addSequential(new ShooterPistonsOut(shooter)); //tilt shooter partially so that ball doesn't fall out
        addSequential(new ReleaseBall(claw, shooter)); //need to test exact seconds for delay
        addParallel(new ShooterPistonsIn(shooter)); //tilts shooter all the way up
        addParallel(new WaitCommand(5));
        addSequential(new StopShooterWheels(claw, shooter));
    }
}
