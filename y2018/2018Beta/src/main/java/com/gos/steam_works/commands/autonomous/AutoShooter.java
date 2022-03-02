package com.gos.steam_works.commands.autonomous;

import com.gos.steam_works.commands.CombinedShootKey;
import com.gos.steam_works.subsystems.Agitator;
import com.gos.steam_works.subsystems.Loader;
import com.gos.steam_works.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 *
 */
public class AutoShooter extends SequentialCommandGroup {

    public AutoShooter(Shooter shooter, Loader loader, Agitator agitator) {
        addCommands(new CombinedShootKey(loader, shooter, agitator));
    }
}
