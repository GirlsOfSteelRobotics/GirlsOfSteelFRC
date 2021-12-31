package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Agitator;
import com.gos.steam_works.subsystems.Loader;
import com.gos.steam_works.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CombinedShootGear extends CommandGroup {

    public CombinedShootGear(Loader loader, Shooter shooter, Agitator agitator) {
        addParallel(new Shoot(shooter, Shooter.SHOOTER_SPEED_GEAR));
        addSequential(new TimeDelay(0.75));
        addParallel(new Agitate(agitator));
        addSequential(new LoadBall(loader));
    }
}
