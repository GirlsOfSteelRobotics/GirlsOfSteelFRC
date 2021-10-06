package com.gos.power_up.commands.autonomous;

import com.gos.power_up.GameData;
import com.gos.power_up.commands.CollectPosition;
import com.gos.power_up.commands.CollectorStop;
import com.gos.power_up.commands.DriveByMotionMagic;
import com.gos.power_up.commands.LiftHold;
import com.gos.power_up.commands.LiftToSwitch;
import com.gos.power_up.commands.ReleaseSlow;
import com.gos.power_up.commands.TimeDelay;
import com.gos.power_up.commands.WristHold;
import com.gos.power_up.commands.WristToCollect;
import com.gos.power_up.subsystems.Chassis;
import com.gos.power_up.subsystems.Collector;
import com.gos.power_up.subsystems.Lift;
import com.gos.power_up.subsystems.Wrist;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
@SuppressWarnings("PMD.DataClass")
public class AutoMiddleSwitch extends CommandGroup {

    public static final double RIGHT_ANGLE = 53.0;
    public static final double RIGHT_DISTANCE = 81.0;
    public static final double LEFT_ANGLE = 65.0;
    public static final double LEFT_DISTANCE = 81.0;
    public static final double BACK_UP = -30.0;

    public AutoMiddleSwitch(Chassis chassis, Lift lift, Wrist wrist, Collector collector, GameData.FieldSide switchSide) {

        addSequential(new WristToCollect(wrist));
        addSequential(new LiftToSwitch(lift));
        addParallel(new WristHold(wrist));
        addParallel(new LiftHold(lift));

        if (switchSide == GameData.FieldSide.right) {
            addSequential(new DriveByMotionMagic(chassis, RIGHT_DISTANCE, -RIGHT_ANGLE));
            addSequential(new DriveByMotionMagic(chassis, RIGHT_DISTANCE, RIGHT_ANGLE));
        } else if (switchSide == GameData.FieldSide.left) {
            addSequential(new DriveByMotionMagic(chassis, LEFT_DISTANCE, LEFT_ANGLE));
            addSequential(new DriveByMotionMagic(chassis, LEFT_DISTANCE, -LEFT_ANGLE));
        } else {
            System.out.println("AutoMiddleSwitch: invalid switch side");
        }

        //Release and back up
        addParallel(new ReleaseSlow(collector));
        addSequential(new TimeDelay(1.0));
        addSequential(new DriveByMotionMagic(chassis, BACK_UP, 0));

        //Put lift down and stop collector
        addSequential(new CollectPosition(lift, wrist));
        addSequential(new CollectorStop(collector));
        addParallel(new WristHold(wrist));
        addParallel(new LiftHold(lift));
    }
}
