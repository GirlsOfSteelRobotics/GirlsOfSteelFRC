package com.gos.power_up.commands.autonomous;

import com.gos.power_up.commands.DriveByMotionMagic;
import com.gos.power_up.commands.LiftHold;
import com.gos.power_up.commands.LiftToSwitch;
import com.gos.power_up.commands.ReleaseFast;
import com.gos.power_up.commands.TimeDelay;
import com.gos.power_up.commands.WristHold;
import com.gos.power_up.commands.WristToCollect;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSwitchSimple extends CommandGroup {

    public final double DISTANCE_FORWARD = 105;
    public final double BACK_UP = -30;

    public AutoSwitchSimple() {
        System.out.println("AutoSimpleSwitch starting");

        //Get lift & wrist into position
        addSequential(new WristToCollect());
        addSequential(new LiftToSwitch());
        addParallel(new WristHold());
        addParallel(new LiftHold());

        //Move Robot into position
        addSequential(new DriveByMotionMagic(DISTANCE_FORWARD, 0));

        //Release and back up
        addParallel(new ReleaseFast());
        addSequential(new TimeDelay(1.0));
        addSequential(new DriveByMotionMagic(BACK_UP, 0));

        /* Position Control
        //Get lift & wrist into position
        addSequential(new WristToCollect());
        addSequential(new LiftToSwitch());
        addParallel(new WristHold());
        addParallel(new LiftHold());

        //Move Robot into position
        addSequential(new DriveByDistance(DISTANCE_FORWARD, Shifters.Speed.kLow));

        //Release and back up
        addParallel(new Release());
        addSequential(new TimeDelay(1.0));
        addSequential(new DriveByDistance(BACK_UP, Shifters.Speed.kLow));
        */
    }
}
