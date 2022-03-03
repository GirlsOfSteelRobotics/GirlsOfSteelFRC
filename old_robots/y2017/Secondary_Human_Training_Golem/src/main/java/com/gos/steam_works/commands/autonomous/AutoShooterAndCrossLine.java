package com.gos.steam_works.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.steam_works.commands.CombinedShootKey;
import com.gos.steam_works.commands.DriveByDistance;
import com.gos.steam_works.commands.TurnByDistance;
import com.gos.steam_works.subsystems.Agitator;
import com.gos.steam_works.subsystems.Chassis;
import com.gos.steam_works.subsystems.Loader;
import com.gos.steam_works.subsystems.Shifters;
import com.gos.steam_works.subsystems.Shooter;

/**
 *
 */
public class AutoShooterAndCrossLine extends SequentialCommandGroup {

    public AutoShooterAndCrossLine(Chassis chassis, Shifters shifters, Agitator agitator, Shooter shooter, Loader loader) {
        addCommands(new CombinedShootKey(agitator, shooter, loader).withTimeout(10.0));
        addCommands(new TurnByDistance(chassis, shifters, -10.0, -60.0, Shifters.Speed.kLow));
        addCommands(new DriveByDistance(chassis, shifters, -48.0, Shifters.Speed.kLow));
    }
}
