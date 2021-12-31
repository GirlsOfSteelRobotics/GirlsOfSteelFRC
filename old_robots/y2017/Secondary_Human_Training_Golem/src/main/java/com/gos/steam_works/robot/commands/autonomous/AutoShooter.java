package com.gos.steam_works.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.steam_works.robot.commands.CombinedShootKey;
import com.gos.steam_works.robot.subsystems.Agitator;
import com.gos.steam_works.robot.subsystems.Loader;
import com.gos.steam_works.robot.subsystems.Shooter;

/**
 *
 */
public class AutoShooter extends CommandGroup {

    public AutoShooter(Agitator agitator, Shooter shooter, Loader loader) {
        addSequential(new CombinedShootKey(agitator, shooter, loader));
    }
}
