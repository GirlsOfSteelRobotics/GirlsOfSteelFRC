package com.gos.power_up.commands.autonomous;

import com.gos.power_up.GameData;
import com.gos.power_up.commands.CollectPosition;
import com.gos.power_up.commands.CollectorStop;
import com.gos.power_up.commands.DriveByMotionMagic;
import com.gos.power_up.commands.LiftHold;
import com.gos.power_up.commands.LiftToScale;
import com.gos.power_up.commands.ReleaseFast;
import com.gos.power_up.commands.TimeDelay;
import com.gos.power_up.commands.TurnByMotionMagic;
import com.gos.power_up.commands.WristHold;
import com.gos.power_up.commands.WristToShoot;
import com.gos.power_up.subsystems.Chassis;
import com.gos.power_up.subsystems.Collector;
import com.gos.power_up.subsystems.Lift;
import com.gos.power_up.subsystems.Shifters;
import com.gos.power_up.subsystems.Wrist;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoNearScale extends CommandGroup {

    private static final double DISTANCE_FORWARD = 295.0;
    private static final double DISTANCE_SIDE = -30.0;

    public AutoNearScale(Chassis chassis, Shifters shifters, Lift lift, Wrist wrist, Collector collector, GameData.FieldSide robotPosition) {
        System.out.println("AutoNearScale starting");

        //Move Robot, lift, wrist into position
        addSequential(new DriveByMotionMagic(chassis, DISTANCE_FORWARD, 0));
        shifters.shiftGear(Shifters.Speed.kLow);
        if (robotPosition == GameData.FieldSide.left) {
            addSequential(new TurnByMotionMagic(chassis, -90.0));
            addSequential(new DriveByMotionMagic(chassis, DISTANCE_SIDE, -90, false));
            addSequential(new WristToShoot(wrist));
            addSequential(new LiftToScale(lift));
            addParallel(new WristHold(wrist));
            addParallel(new LiftHold(lift));
        } else {
            addSequential(new TurnByMotionMagic(chassis, 90.0));
            addSequential(new DriveByMotionMagic(chassis, DISTANCE_SIDE, 90, false));
            addSequential(new WristToShoot(wrist));
            addSequential(new LiftToScale(lift));
            addParallel(new WristHold(wrist));
            addParallel(new LiftHold(lift));

        }

        //Wait for lift and wrist to get into position then shoot
        addSequential(new TimeDelay(2.5));
        addParallel(new ReleaseFast(collector, 0.75));
        addSequential(new TimeDelay(1.5));

        //Put lift down and stop collector
        addSequential(new CollectPosition(lift, wrist));
        addSequential(new CollectorStop(collector));
        addParallel(new WristHold(wrist));
        addParallel(new LiftHold(lift));

    }
}
