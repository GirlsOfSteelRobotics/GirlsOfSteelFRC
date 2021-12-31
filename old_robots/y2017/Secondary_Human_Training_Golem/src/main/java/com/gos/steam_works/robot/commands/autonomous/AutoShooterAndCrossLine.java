package com.gos.steam_works.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.steam_works.robot.commands.CombinedShootKey;
import com.gos.steam_works.robot.commands.DriveByDistance;
import com.gos.steam_works.robot.commands.TimeDelay;
import com.gos.steam_works.robot.commands.TurnByDistance;
import com.gos.steam_works.robot.subsystems.Agitator;
import com.gos.steam_works.robot.subsystems.Chassis;
import com.gos.steam_works.robot.subsystems.Loader;
import com.gos.steam_works.robot.subsystems.Shifters;
import com.gos.steam_works.robot.subsystems.Shooter;

/**
 *
 */
public class AutoShooterAndCrossLine extends CommandGroup {

    public AutoShooterAndCrossLine(Chassis chassis, Shifters shifters, Agitator agitator, Shooter shooter, Loader loader) {
        addParallel(new CombinedShootKey(agitator, shooter, loader));
        addSequential(new TimeDelay(10.0));
        addSequential(new TurnByDistance(chassis, shifters, -10.0, -60.0, Shifters.Speed.kLow));
        addSequential(new DriveByDistance(chassis, shifters, -48.0, Shifters.Speed.kLow));
    }
}
