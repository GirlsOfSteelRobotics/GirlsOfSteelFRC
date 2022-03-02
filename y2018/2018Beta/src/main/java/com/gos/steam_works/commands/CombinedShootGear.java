package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Agitator;
import com.gos.steam_works.subsystems.Loader;
import com.gos.steam_works.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 *
 */
public class CombinedShootGear extends SequentialCommandGroup {

    public CombinedShootGear(Loader loader, Shooter shooter, Agitator agitator) {
        addCommands(new Shoot(shooter, Shooter.SHOOTER_SPEED_GEAR).withTimeout(0.75));
        addCommands(new Agitate(agitator).alongWith(new LoadBall(loader)));
    }
}
