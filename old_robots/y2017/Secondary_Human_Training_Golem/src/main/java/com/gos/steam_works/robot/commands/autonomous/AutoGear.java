package com.gos.steam_works.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
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
public class AutoGear extends SequentialCommandGroup {

    public AutoGear(Chassis chassis, Shifters shifters, Camera camera, double distance, Direction direction) {
        // Using motion profiles for turns:
        addCommands(new DriveByDistance(chassis, shifters, distance, Shifters.Speed.kLow));
        if (direction == Direction.kLeft) {
            addCommands(new DriveByMotionProfile(chassis, "/home/lvuser/shortTurn.dat", "/home/lvuser/longTurn.dat", 1.0));
        } else if (direction == Direction.kRight) {
            addCommands(new DriveByMotionProfile(chassis, "/home/lvuser/longTurn.dat", "/home/lvuser/shortTurn.dat", 1.0));
        }
        addCommands(new DriveByVision(chassis, camera));
        addCommands(new DriveByDistance(chassis, shifters, -3.0, Shifters.Speed.kLow));
    }
}
