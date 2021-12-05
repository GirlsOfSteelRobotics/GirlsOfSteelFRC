package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.subsystems.Agitator;
import org.usfirst.frc.team3504.robot.subsystems.Loader;
import org.usfirst.frc.team3504.robot.subsystems.Shooter;

/**
 *
 */
public class CombinedShootKey extends CommandGroup {

    public CombinedShootKey(Agitator agitator, Shooter shooter, Loader loader) {
        addParallel(new Shoot(shooter, Shooter.AUTO_SHOOTER_SPEED_KEY));
        addSequential(new TimeDelay(0.75));
        addParallel(new Agitate(agitator));
        addSequential(new LoadBall(loader));
    }
}
