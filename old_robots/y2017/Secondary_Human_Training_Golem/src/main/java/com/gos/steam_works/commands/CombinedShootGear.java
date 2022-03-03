package com.gos.steam_works.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.steam_works.subsystems.Agitator;
import com.gos.steam_works.subsystems.Loader;
import com.gos.steam_works.subsystems.Shooter;

/**
 *
 */
public class CombinedShootGear extends SequentialCommandGroup {

    public CombinedShootGear(Agitator agitator, Shooter shooter, Loader loader) {
        addCommands(new Shoot(shooter, Shooter.SHOOTER_SPEED_GEAR).withTimeout(0.75));
        addCommands(new Agitate(agitator).alongWith(new LoadBall(loader)));
    }
}
