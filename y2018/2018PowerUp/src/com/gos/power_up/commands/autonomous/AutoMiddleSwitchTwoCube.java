package com.gos.power_up.commands.autonomous;

import com.gos.power_up.GameData;
import com.gos.power_up.commands.Collect;
import com.gos.power_up.commands.CollectPosition;
import com.gos.power_up.commands.CollectorHold;
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
public class AutoMiddleSwitchTwoCube extends CommandGroup {
    //Parameters for first cube
    public final static double RIGHT_ANGLE = 50.0;
    public final static double RIGHT_DISTANCE = 63.0;
    public final static double LEFT_ANGLE = 65.0;
    public final static double LEFT_DISTANCE = 70.0;

    //Parameters for collecting second cube
    public final static double LONG_BACK_UP = -80.0;
    public final static double TURN_RADIUS_2 = 100.0;
    public final static double TURN_DEGREES_2 = 70.0;

    //After second cube
    public final static double SHORT_BACK_UP = -30.0;

    public AutoMiddleSwitchTwoCube(GameData.FieldSide switchSide) {

        //Raise lift, lower wrist to get ready to spit cube out
        addSequential(new WristToCollect());
        addSequential(new LiftToSwitch());
        addParallel(new WristHold());
        addParallel(new LiftHold());

        //Drive to the switch plate
        if (switchSide == GameData.FieldSide.right) {
            addParallel(new DriveByMotionMagic(RIGHT_DISTANCE, -RIGHT_ANGLE));
            addSequential(new TimeDelay(2.5));
            addParallel(new DriveByMotionMagic(RIGHT_DISTANCE, RIGHT_ANGLE));
            addSequential(new TimeDelay(2.0));
        } else if (switchSide == GameData.FieldSide.left) {
            addParallel(new DriveByMotionMagic(LEFT_DISTANCE, LEFT_ANGLE));
            addSequential(new TimeDelay(2.5));
            addParallel(new DriveByMotionMagic(LEFT_DISTANCE, -LEFT_ANGLE));
            addSequential(new TimeDelay(2.0));
        } else {
            System.out.println("AutoMiddleSwitch: invalid switch side");
        }

        //Release and back up
        addParallel(new ReleaseSlow());
        addSequential(new TimeDelay(0.5));
        addParallel(new DriveByMotionMagic(LONG_BACK_UP, 0));
        addSequential(new TimeDelay(2.5));

        //Put lift down and start collecting
        addSequential(new CollectPosition());
        addParallel(new WristHold());
        addParallel(new LiftHold());
        addParallel(new Collect());

        //Grab second cube and come back
        if (switchSide == GameData.FieldSide.right) {
            addParallel(new DriveByMotionMagic(TURN_RADIUS_2, TURN_DEGREES_2));
            addSequential(new TimeDelay(2.5));
            addParallel(new CollectorHold());
            addParallel(new DriveByMotionMagic(-TURN_RADIUS_2 / 2, -20, false));
        } else if (switchSide == GameData.FieldSide.left) {
            addParallel(new DriveByMotionMagic(TURN_RADIUS_2, -TURN_DEGREES_2));
            addSequential(new TimeDelay(2.5));
            addParallel(new CollectorHold());
            addParallel(new DriveByMotionMagic(-TURN_RADIUS_2 / 2, 20, false));
        } else {
            System.out.println("AutoMiddleSwitch: invalid switch side");
        }

        //lift up to shoot out
        addSequential(new WristToCollect());
        addSequential(new LiftToSwitch());
        addParallel(new WristHold());
        addParallel(new LiftHold());

        //Approach switch plate
        addSequential(new TimeDelay(2.0));
        addParallel(new DriveByMotionMagic(-LONG_BACK_UP, 0));
        addSequential(new TimeDelay(2.0));

        //Release and back up
        addParallel(new ReleaseSlow());
        addSequential(new TimeDelay(0.5));
        addSequential(new DriveByMotionMagic(SHORT_BACK_UP, 0));
    }
}
