package com.gos.steam_works.commands.autonomous;

import com.gos.steam_works.commands.CombinedShootKey;
import com.gos.steam_works.commands.DriveByDistance;
import com.gos.steam_works.commands.TimeDelay;
import com.gos.steam_works.commands.TurnByDistance;
import com.gos.steam_works.subsystems.Agitator;
import com.gos.steam_works.subsystems.Chassis;
import com.gos.steam_works.subsystems.Loader;
import com.gos.steam_works.subsystems.Shifters;
import com.gos.steam_works.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoShooterAndCrossLine extends CommandGroup {

    public AutoShooterAndCrossLine(Chassis chassis, Shifters shifters, Shooter shooter, Loader loader, Agitator agitator) {
        addParallel(new CombinedShootKey(loader, shooter, agitator));
        addSequential(new TimeDelay(10.0));
        addSequential(new TurnByDistance(chassis, shifters, -10.0, -60.0, Shifters.Speed.kLow));
        addSequential(new DriveByDistance(chassis, shifters, -48.0, Shifters.Speed.kLow));
    }
}
