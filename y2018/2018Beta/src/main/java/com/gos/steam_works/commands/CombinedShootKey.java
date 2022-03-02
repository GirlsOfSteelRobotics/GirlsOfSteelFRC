package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Agitator;
import com.gos.steam_works.subsystems.Loader;
import com.gos.steam_works.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 *
 */
public class CombinedShootKey extends SequentialCommandGroup {

    public CombinedShootKey(Loader loader, Shooter shooter, Agitator agitator) {
        addCommands(new Shoot(shooter, Shooter.AUTO_SHOOTER_SPEED_KEY).withTimeout(0.75));
        addCommands(new Agitate(agitator).alongWith(new LoadBall(loader)));
    }
}
