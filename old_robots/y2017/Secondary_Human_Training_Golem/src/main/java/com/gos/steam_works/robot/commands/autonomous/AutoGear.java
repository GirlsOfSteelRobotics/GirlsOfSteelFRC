package com.gos.steam_works.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.steam_works.robot.commands.DriveByDistance;
import com.gos.steam_works.robot.commands.DriveByMotionProfile;
import com.gos.steam_works.robot.commands.DriveByVision;
import com.gos.steam_works.robot.commands.TurnToGear.Direction;
import com.gos.steam_works.robot.subsystems.Camera;
import com.gos.steam_works.robot.subsystems.Chassis;
import com.gos.steam_works.robot.subsystems.Shifters;

/**
 *
 */
public class AutoGear extends CommandGroup {

    public AutoGear(Chassis chassis, Shifters shifters, Camera camera, double distance, Direction direction) {
        // Using motion profiles for turns:
        addSequential(new DriveByDistance(chassis, shifters, distance, Shifters.Speed.kLow));
        if (direction == Direction.kLeft) {
            addSequential(new DriveByMotionProfile(chassis, "/home/lvuser/shortTurn.dat", "/home/lvuser/longTurn.dat", 1.0));
        } else if (direction == Direction.kRight) {
            addSequential(new DriveByMotionProfile(chassis, "/home/lvuser/longTurn.dat", "/home/lvuser/shortTurn.dat", 1.0));
        }
        addSequential(new DriveByVision(chassis, camera));
        addSequential(new DriveByDistance(chassis, shifters, -3.0, Shifters.Speed.kLow));
    }
}
