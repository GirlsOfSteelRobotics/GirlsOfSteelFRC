package com.gos.steam_works.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
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
public class AutoShooterAndCrossLine extends SequentialCommandGroup {

    public AutoShooterAndCrossLine(Chassis chassis, Shifters shifters, Agitator agitator, Shooter shooter, Loader loader) {
        addParallel(new CombinedShootKey(agitator, shooter, loader));
        addCommands(new TimeDelay(10.0));
        addCommands(new TurnByDistance(chassis, shifters, -10.0, -60.0, Shifters.Speed.kLow));
        addCommands(new DriveByDistance(chassis, shifters, -48.0, Shifters.Speed.kLow));
    }
}
