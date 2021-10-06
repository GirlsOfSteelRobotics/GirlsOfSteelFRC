package com.gos.steam_works.commands.autonomous;

import com.gos.steam_works.commands.CombinedShootKey;
import com.gos.steam_works.subsystems.Agitator;
import com.gos.steam_works.subsystems.Loader;
import com.gos.steam_works.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoShooter extends CommandGroup {

    public AutoShooter(Shooter shooter, Loader loader, Agitator agitator) {
        addSequential(new CombinedShootKey(loader, shooter, agitator));
    }
}
