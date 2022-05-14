package com.gos.power_up.commands.autonomous;

import com.gos.power_up.GameData;
import com.gos.power_up.commands.CollectPosition;
import com.gos.power_up.commands.CollectorStop;
import com.gos.power_up.commands.DriveByMotionMagic;
import com.gos.power_up.commands.LiftHold;
import com.gos.power_up.commands.LiftToSwitch;
import com.gos.power_up.commands.ReleaseFast;
import com.gos.power_up.commands.TimeDelay;
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
public class AutoFarSwitch extends SequentialCommandGroup {
    private static final double DISTANCE_FORWARD_1 = 30.0;
    private static final double DISTANCE_SIDE = 130.0;
    private static final double DISTANCE_FORWARD_2 = 30.0;
    private static final double BACK_UP = -30.0;

    public AutoFarSwitch(Chassis chassis, Lift lift, Wrist wrist, Collector collector, GameData.FieldSide robotPosition) {
        System.out.println("AutoFarSwitch starting");

        //Get lift & wrist into position
        addCommands(new WristToCollect(wrist));
        addCommands(new ParallelCommandGroup(
            new LiftToSwitch(lift),
            new WristHold(wrist),
            new LiftHold(lift)));

        //Move Robot into position
        addCommands(new DriveByMotionMagic(chassis, DISTANCE_FORWARD_1, 0));
        if (robotPosition == GameData.FieldSide.LEFT) {
            addCommands(new AutoTurnRight(chassis, 25.0));
        } else {
            addCommands(new AutoTurnLeft(chassis, 25.0));
        }
        addCommands(new DriveByMotionMagic(chassis, DISTANCE_SIDE, 0));
        if (robotPosition == GameData.FieldSide.LEFT) {
            addCommands(new AutoTurnLeft(chassis, 25.0));
        } else {
            addCommands(new AutoTurnRight(chassis, 25.0));
        }
        addCommands(new DriveByMotionMagic(chassis, DISTANCE_FORWARD_2, 0)
            .alongWith(new ReleaseFast(collector)));

        //Release and back up
        addCommands(new TimeDelay(1.0));
        addCommands(new DriveByMotionMagic(chassis, BACK_UP, 0));

        //Put lift down and stop collector
        addCommands(new CollectPosition(lift, wrist));
        addCommands(new ParallelCommandGroup(
            new CollectorStop(collector),
            new WristHold(wrist),
            new LiftHold(lift)));
    }
}
