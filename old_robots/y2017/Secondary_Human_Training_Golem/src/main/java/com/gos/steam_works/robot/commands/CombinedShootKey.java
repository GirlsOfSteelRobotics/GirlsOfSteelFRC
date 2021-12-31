package com.gos.steam_works.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.steam_works.robot.subsystems.Agitator;
import com.gos.steam_works.robot.subsystems.Loader;
import com.gos.steam_works.robot.subsystems.Shooter;

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
