package com.gos.steam_works.commands.autonomous;

import com.gos.steam_works.GripPipelineListener;
import com.gos.steam_works.commands.DriveByDistance;
import com.gos.steam_works.commands.DriveByMotionProfile;
import com.gos.steam_works.commands.DriveByVision;
import com.gos.steam_works.commands.TurnToGear.Direction;
import com.gos.steam_works.subsystems.Chassis;
import com.gos.steam_works.subsystems.Shifters;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 *
 */
public class AutoGear extends SequentialCommandGroup {

    public AutoGear(Chassis chassis, Shifters shifters, GripPipelineListener listener, double distance, Direction direction) {
        /*
        addCommands(new DriveByDistance(distance, Shifters.Speed.kHigh));
        addCommands(new TurnToGear(direction));
        addCommands(new DriveByVision());
        addCommands(new DriveByDistance(-3.0, Shifters.Speed.kLow));
        */
        // Using motion profiles for turns:
        addCommands(new DriveByDistance(chassis, shifters, distance, Shifters.Speed.kLow));
        if (direction == Direction.kLeft) {
            addCommands(new DriveByMotionProfile(chassis, "/home/lvuser/shortTurn.dat", "/home/lvuser/longTurn.dat", 1.0));
        } else if (direction == Direction.kRight) {
            addCommands(new DriveByMotionProfile(chassis, "/home/lvuser/longTurn.dat", "/home/lvuser/shortTurn.dat", 1.0));
        }
        addCommands(new DriveByVision(chassis, listener));
        addCommands(new DriveByDistance(chassis, shifters, -3.0, Shifters.Speed.kLow));


        // Add Commands here:
        // e.g. addCommands(new Command1());
        // addCommands(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        // addCommands(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
