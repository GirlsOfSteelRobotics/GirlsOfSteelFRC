package com.gos.steam_works.commands.autonomous;

import com.gos.steam_works.GripPipelineListener;
import com.gos.steam_works.commands.CombinedShootGear;
import com.gos.steam_works.commands.DriveByDistance;
import com.gos.steam_works.commands.DriveByMotionProfile;
import com.gos.steam_works.commands.DriveByVision;
import com.gos.steam_works.commands.TimeDelay;
import com.gos.steam_works.commands.TurnByDistance;
import com.gos.steam_works.commands.TurnToGear.Direction;
import com.gos.steam_works.subsystems.Agitator;
import com.gos.steam_works.subsystems.Chassis;
import com.gos.steam_works.subsystems.Loader;
import com.gos.steam_works.subsystems.Shifters;
import com.gos.steam_works.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 *
 */
public class AutoBoilerGearAndShoot extends SequentialCommandGroup {

    public AutoBoilerGearAndShoot(Chassis chassis, Shifters shifters, Shooter shooter, Loader loader, Agitator agitator, GripPipelineListener listener, double distance, Direction direction) {
        addCommands(new DriveByDistance(chassis, shifters, distance, Shifters.Speed.kLow));
        if (direction == Direction.kLeft) {
            addCommands(new DriveByMotionProfile(chassis, "/home/lvuser/shortTurn.dat", "/home/lvuser/longTurn.dat", 1.0));
        } else if (direction == Direction.kRight) {
            addCommands(new DriveByMotionProfile(chassis, "/home/lvuser/longTurn.dat", "/home/lvuser/shortTurn.dat", 1.0));
        }
        addCommands(new DriveByVision(chassis, listener));
        addCommands(new DriveByMotionProfile(chassis, "/home/lvuser/BackupFourInches.dat", "/home/lvuser/BackupFourInches.dat", 1.0));

        Command turnCommand;
        if (direction == Direction.kLeft) {
            turnCommand = new TurnByDistance(chassis, shifters, -8.0, 3.0, Shifters.Speed.kLow);
        } else if (direction == Direction.kRight) {
            turnCommand = new TurnByDistance(chassis, shifters, 3.0, -2.0, Shifters.Speed.kLow);
        } else {
            throw new IllegalArgumentException();
        }

        addCommands(turnCommand.alongWith(new TimeDelay(1.5)));
        addCommands(new CombinedShootGear(loader, shooter, agitator));
    }
}
