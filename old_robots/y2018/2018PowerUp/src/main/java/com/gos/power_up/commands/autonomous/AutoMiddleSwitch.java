package com.gos.power_up.commands.autonomous;

import com.gos.power_up.GameData;
import com.gos.power_up.commands.CollectPosition;
import com.gos.power_up.commands.CollectorStop;
import com.gos.power_up.commands.DriveByMotionMagic;
import com.gos.power_up.commands.LiftHold;
import com.gos.power_up.commands.LiftToSwitch;
import com.gos.power_up.commands.ReleaseSlow;
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
@SuppressWarnings("PMD.DataClass")
public class AutoMiddleSwitch extends SequentialCommandGroup {

    public static final double RIGHT_ANGLE = 53.0;
    public static final double RIGHT_DISTANCE = 81.0;
    public static final double LEFT_ANGLE = 65.0;
    public static final double LEFT_DISTANCE = 81.0;
    public static final double BACK_UP = -30.0;

    public AutoMiddleSwitch(Chassis chassis, Lift lift, Wrist wrist, Collector collector, GameData.FieldSide switchSide) {

        addCommands(new WristToCollect(wrist));
        addCommands(new ParallelCommandGroup(
            new LiftToSwitch(lift),
            new WristHold(wrist),
            new LiftHold(lift)));

        if (switchSide == GameData.FieldSide.RIGHT) {
            addCommands(new DriveByMotionMagic(chassis, RIGHT_DISTANCE, -RIGHT_ANGLE));
            addCommands(new DriveByMotionMagic(chassis, RIGHT_DISTANCE, RIGHT_ANGLE));
        } else if (switchSide == GameData.FieldSide.LEFT) {
            addCommands(new DriveByMotionMagic(chassis, LEFT_DISTANCE, LEFT_ANGLE));
            addCommands(new DriveByMotionMagic(chassis, LEFT_DISTANCE, -LEFT_ANGLE));
        } else {
            System.out.println("AutoMiddleSwitch: invalid switch side");
        }

        //Release and back up
        addCommands(new ReleaseSlow(collector).withTimeout(1.0));
        addCommands(new DriveByMotionMagic(chassis, BACK_UP, 0));

        //Put lift down and stop collector
        addCommands(new CollectPosition(lift, wrist));
        addCommands(new ParallelCommandGroup(
            new CollectorStop(collector),
            new WristHold(wrist),
            new LiftHold(lift)));
    }
}
