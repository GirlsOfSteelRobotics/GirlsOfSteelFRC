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

    public AutoMiddleSwitch(GameData.FieldSide switchSide) {

        addSequential(new WristToCollect());
        addSequential(new LiftToSwitch());
        addParallel(new WristHold());
        addParallel(new LiftHold());

        if (switchSide == GameData.FieldSide.right) {
            addSequential(new DriveByMotionMagic(RIGHT_DISTANCE, -RIGHT_ANGLE));
            addSequential(new DriveByMotionMagic(RIGHT_DISTANCE, RIGHT_ANGLE));
        } else if (switchSide == GameData.FieldSide.left) {
            addSequential(new DriveByMotionMagic(LEFT_DISTANCE, LEFT_ANGLE));
            addSequential(new DriveByMotionMagic(LEFT_DISTANCE, -LEFT_ANGLE));
        } else {
            System.out.println("AutoMiddleSwitch: invalid switch side");
        }

        //Release and back up
        addParallel(new ReleaseSlow());
        addSequential(new TimeDelay(1.0));
        addSequential(new DriveByMotionMagic(BACK_UP, 0));

        //Put lift down and stop collector
        addSequential(new CollectPosition());
        addSequential(new CollectorStop());
        addParallel(new WristHold());
        addParallel(new LiftHold());
    }
}
