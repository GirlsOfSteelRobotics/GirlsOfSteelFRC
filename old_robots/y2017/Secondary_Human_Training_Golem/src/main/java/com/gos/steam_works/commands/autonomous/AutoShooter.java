package com.gos.steam_works.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.steam_works.commands.CombinedShootKey;
import com.gos.steam_works.subsystems.Agitator;
import com.gos.steam_works.subsystems.Loader;
import com.gos.steam_works.subsystems.Shooter;

/**
 *
 */
public class AutoShooter extends SequentialCommandGroup {

    public AutoShooter(Agitator agitator, Shooter shooter, Loader loader) {
        addCommands(new CombinedShootKey(agitator, shooter, loader));
    }
}
