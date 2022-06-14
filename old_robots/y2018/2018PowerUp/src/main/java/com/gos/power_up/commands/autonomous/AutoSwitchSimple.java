package com.gos.power_up.commands.autonomous;

import com.gos.power_up.commands.DriveByMotionMagic;
import com.gos.power_up.commands.LiftHold;
import com.gos.power_up.commands.LiftToSwitch;
import com.gos.power_up.commands.ReleaseFast;
import com.gos.power_up.commands.WristHold;
import com.gos.power_up.commands.WristToCollect;
import com.gos.power_up.subsystems.Chassis;
import com.gos.power_up.subsystems.Collector;
import com.gos.power_up.subsystems.Lift;
import com.gos.power_up.subsystems.Wrist;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 *
 */
public class AutoSwitchSimple extends SequentialCommandGroup {

    private static final double DISTANCE_FORWARD = 105;
    private static final double BACK_UP = -30;

    public AutoSwitchSimple(Chassis chassis, Lift lift, Wrist wrist, Collector collector) {
        System.out.println("AutoSimpleSwitch starting");

        //Get lift & wrist into position
        addCommands(new WristToCollect(wrist));
        addCommands(new ParallelCommandGroup(
            new LiftToSwitch(lift),
            new WristHold(wrist),
            new LiftHold(lift)));

        //Move Robot into position
        addCommands(new DriveByMotionMagic(chassis, DISTANCE_FORWARD, 0));

        //Release and back up
        addCommands(new ReleaseFast(collector).withTimeout(1.0));
        addCommands(new DriveByMotionMagic(chassis, BACK_UP, 0));

        /* Position Control
        //Get lift & wrist into position
        addCommands(new WristToCollect());
        addCommands(new LiftToSwitch());
        addParallel(new WristHold());
        addParallel(new LiftHold());

        //Move Robot into position
        addCommands(new DriveByDistance(DISTANCE_FORWARD, Shifters.Speed.kLow));

        //Release and back up
        addParallel(new Release());
        addCommands(new TimeDelay(1.0));
        addCommands(new DriveByDistance(BACK_UP, Shifters.Speed.kLow));
        */
    }
}
