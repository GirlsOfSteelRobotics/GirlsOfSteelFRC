package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc.team3504.robot.subsystems.Claw;
import org.usfirst.frc.team3504.robot.subsystems.Shooter;

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
