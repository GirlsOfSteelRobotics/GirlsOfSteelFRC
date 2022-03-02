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
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 *
 */
public class AutoNearScale extends SequentialCommandGroup {

    private static final double DISTANCE_FORWARD = 295.0;
    private static final double DISTANCE_SIDE = -30.0;

    public AutoNearScale(Chassis chassis, Shifters shifters, Lift lift, Wrist wrist, Collector collector, GameData.FieldSide robotPosition) {
        System.out.println("AutoNearScale starting");

        //Move Robot, lift, wrist into position
        addCommands(new DriveByMotionMagic(chassis, DISTANCE_FORWARD, 0));
        shifters.shiftGear(Shifters.Speed.kLow);
        if (robotPosition == GameData.FieldSide.left) {
            addCommands(new TurnByMotionMagic(chassis, -90.0));
            addCommands(new DriveByMotionMagic(chassis, DISTANCE_SIDE, -90, false));
            addCommands(new WristToShoot(wrist));
            addCommands(new ParallelCommandGroup(
                new LiftToScale(lift),
                new WristHold(wrist),
                new LiftHold(lift)));
        } else {
            addCommands(new TurnByMotionMagic(chassis, 90.0));
            addCommands(new DriveByMotionMagic(chassis, DISTANCE_SIDE, 90, false));
            addCommands(new WristToShoot(wrist));
            addCommands(new ParallelCommandGroup(
                new LiftToScale(lift),
                new WristHold(wrist),
                new LiftHold(lift)));

        }

        //Wait for lift and wrist to get into position then shoot
        addCommands(new ReleaseFast(collector, 0.75).withTimeout(2.5));
        addCommands(new TimeDelay(1.5));

        //Put lift down and stop collector
        addCommands(new CollectPosition(lift, wrist));
        addCommands(new ParallelCommandGroup(
            new CollectorStop(collector),
            new WristHold(wrist),
            new LiftHold(lift)));

    }
}
