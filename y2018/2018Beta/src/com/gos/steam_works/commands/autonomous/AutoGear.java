package com.gos.steam_works.commands.autonomous;

import com.gos.steam_works.subsystems.Shifters;
import com.gos.steam_works.commands.DriveByDistance;
import com.gos.steam_works.commands.DriveByMotionProfile;
import com.gos.steam_works.commands.DriveByVision;
import com.gos.steam_works.commands.TurnToGear.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoGear extends CommandGroup {

    public AutoGear(double distance, Direction direction) {
        /*
        addSequential(new DriveByDistance(distance, Shifters.Speed.kHigh));
        addSequential(new TurnToGear(direction));
        addSequential(new DriveByVision());
        addSequential(new DriveByDistance(-3.0, Shifters.Speed.kLow));
        */
        // Using motion profiles for turns:
        addSequential(new DriveByDistance(distance, Shifters.Speed.kLow));
        if (direction == Direction.kLeft){
            addSequential(new DriveByMotionProfile("/home/lvuser/shortTurn.dat", "/home/lvuser/longTurn.dat", 1.0));
        } else if (direction == Direction.kRight){
            addSequential(new DriveByMotionProfile("/home/lvuser/longTurn.dat", "/home/lvuser/shortTurn.dat", 1.0));
        }
        addSequential(new DriveByVision());
        addSequential(new DriveByDistance(-3.0, Shifters.Speed.kLow));


        // Add Commands here:
        // e.g. addSequential(new Command1());
        // addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        // addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
