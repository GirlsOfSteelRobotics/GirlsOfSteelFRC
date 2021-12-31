package com.gos.steam_works.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.steam_works.robot.commands.CombinedShootGear;
import com.gos.steam_works.robot.commands.DriveByDistance;
import com.gos.steam_works.robot.commands.DriveByMotionProfile;
import com.gos.steam_works.robot.commands.DriveByVision;
import com.gos.steam_works.robot.commands.TimeDelay;
import com.gos.steam_works.robot.commands.TurnByDistance;
import com.gos.steam_works.robot.commands.TurnToGear.Direction;
import com.gos.steam_works.robot.subsystems.Agitator;
import com.gos.steam_works.robot.subsystems.Camera;
import com.gos.steam_works.robot.subsystems.Chassis;
import com.gos.steam_works.robot.subsystems.Loader;
import com.gos.steam_works.robot.subsystems.Shifters;
import com.gos.steam_works.robot.subsystems.Shooter;

/**
 *
 */
public class AutoBoilerGearAndShoot extends CommandGroup {

    public AutoBoilerGearAndShoot(Chassis chassis, Shifters shifters, Agitator agitator, Shooter shooter, Loader loader, Camera camera, double distance, Direction direction) {

        addSequential(new DriveByDistance(chassis, shifters, distance, Shifters.Speed.kLow));
        if (direction == Direction.kLeft) {
            addSequential(new DriveByMotionProfile(chassis, "/home/lvuser/shortTurn.dat", "/home/lvuser/longTurn.dat", 1.0));
        } else if (direction == Direction.kRight) {
            addSequential(new DriveByMotionProfile(chassis, "/home/lvuser/longTurn.dat", "/home/lvuser/shortTurn.dat", 1.0));
        }
        addSequential(new DriveByVision(chassis, camera));
        addSequential(new DriveByMotionProfile(chassis, "/home/lvuser/BackupFourInches.dat", "/home/lvuser/BackupFourInches.dat", 1.0));

        if (direction == Direction.kLeft) {
            addParallel(new TurnByDistance(chassis, shifters, -8.0, 3.0, Shifters.Speed.kLow));
        } else if (direction == Direction.kRight) {
            addParallel(new TurnByDistance(chassis, shifters, 3.0, -2.0, Shifters.Speed.kLow));
        }

        addSequential(new TimeDelay(1.5));
        addSequential(new CombinedShootGear(agitator, shooter, loader));
    }
}
