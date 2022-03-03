package com.gos.steam_works.commands.autonomous;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.steam_works.commands.CombinedShootGear;
import com.gos.steam_works.commands.DriveByDistance;
import com.gos.steam_works.commands.DriveByMotionProfile;
import com.gos.steam_works.commands.DriveByVision;
import com.gos.steam_works.commands.TurnByDistance;
import com.gos.steam_works.commands.TurnToGear.Direction;
import com.gos.steam_works.subsystems.Agitator;
import com.gos.steam_works.subsystems.Camera;
import com.gos.steam_works.subsystems.Chassis;
import com.gos.steam_works.subsystems.Loader;
import com.gos.steam_works.subsystems.Shifters;
import com.gos.steam_works.subsystems.Shooter;

/**
 *
 */
public class AutoBoilerGearAndShoot extends SequentialCommandGroup {

    public AutoBoilerGearAndShoot(Chassis chassis, Shifters shifters, Agitator agitator, Shooter shooter, Loader loader, Camera camera, double distance, Direction direction) {

        addCommands(new DriveByDistance(chassis, shifters, distance, Shifters.Speed.kLow));
        if (direction == Direction.kLeft) {
            addCommands(new DriveByMotionProfile(chassis, "/home/lvuser/shortTurn.dat", "/home/lvuser/longTurn.dat", 1.0));
        } else if (direction == Direction.kRight) {
            addCommands(new DriveByMotionProfile(chassis, "/home/lvuser/longTurn.dat", "/home/lvuser/shortTurn.dat", 1.0));
        }
        addCommands(new DriveByVision(chassis, camera));
        addCommands(new DriveByMotionProfile(chassis, "/home/lvuser/BackupFourInches.dat", "/home/lvuser/BackupFourInches.dat", 1.0));

        Command turnCommand;
        if (direction == Direction.kLeft) {
            turnCommand = new TurnByDistance(chassis, shifters, -8.0, 3.0, Shifters.Speed.kLow);
        } else if (direction == Direction.kRight) {
            turnCommand = new TurnByDistance(chassis, shifters, 3.0, -2.0, Shifters.Speed.kLow);
        } else {
            throw new IllegalArgumentException();
        }

        addCommands(turnCommand.withTimeout(1.5));
        addCommands(new CombinedShootGear(agitator, shooter, loader));
    }
}
