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
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoBoilerGearAndShoot extends CommandGroup {

    public AutoBoilerGearAndShoot(Chassis chassis, Shifters shifters, Shooter shooter, Loader loader, Agitator agitator, GripPipelineListener listener, double distance, Direction direction) {
        addSequential(new DriveByDistance(chassis, shifters, distance, Shifters.Speed.kLow));
        if (direction == Direction.kLeft) {
            addSequential(new DriveByMotionProfile(chassis, "/home/lvuser/shortTurn.dat", "/home/lvuser/longTurn.dat", 1.0));
        } else if (direction == Direction.kRight) {
            addSequential(new DriveByMotionProfile(chassis, "/home/lvuser/longTurn.dat", "/home/lvuser/shortTurn.dat", 1.0));
        }
        addSequential(new DriveByVision(chassis, listener));
        addSequential(new DriveByMotionProfile(chassis, "/home/lvuser/BackupFourInches.dat", "/home/lvuser/BackupFourInches.dat", 1.0));

        if (direction == Direction.kLeft) {
            addParallel(new TurnByDistance(chassis, shifters, -8.0, 3.0, Shifters.Speed.kLow));
        } else if (direction == Direction.kRight) {
            addParallel(new TurnByDistance(chassis, shifters, 3.0, -2.0, Shifters.Speed.kLow));
        }

        addSequential(new TimeDelay(1.5));
        addSequential(new CombinedShootGear(loader, shooter, agitator));
    }
}
