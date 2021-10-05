package com.gos.steam_works.commands.autonomous;

import com.gos.steam_works.subsystems.Shifters;
import com.gos.steam_works.commands.CombinedShootKey;
import com.gos.steam_works.commands.DriveByDistance;
import com.gos.steam_works.commands.TimeDelay;
import com.gos.steam_works.commands.TurnByDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoShooterAndCrossLine extends CommandGroup {

    public AutoShooterAndCrossLine() {
        addParallel(new CombinedShootKey());
        addSequential(new TimeDelay(10.0));
        addSequential(new TurnByDistance(-10.0, -60.0, Shifters.Speed.kLow));
        addSequential(new DriveByDistance(-48.0, Shifters.Speed.kLow));
    }
}
