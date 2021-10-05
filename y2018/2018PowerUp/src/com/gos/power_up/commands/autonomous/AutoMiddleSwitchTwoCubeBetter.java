package com.gos.power_up.commands.autonomous;

import com.gos.power_up.GameData;
import com.gos.power_up.commands.Collect;
import com.gos.power_up.commands.CollectPosition;
import com.gos.power_up.commands.CollectorStop;
import com.gos.power_up.commands.DriveByMotionMagicAbsolute;
import com.gos.power_up.commands.LiftHold;
import com.gos.power_up.commands.LiftToSwitch;
import com.gos.power_up.commands.ReleaseFast;
import com.gos.power_up.commands.TimeDelay;
import com.gos.power_up.commands.TurnByMotionMagicAbsolute;
import com.gos.power_up.commands.WristHold;
import com.gos.power_up.commands.WristToCollect;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
@SuppressWarnings("PMD.DataClass")
public class AutoMiddleSwitchTwoCubeBetter extends CommandGroup {
    public final static double STRAIGHT_1 = 40.0;
    public final static double TURN_DEGREES_1 = 30.0;
    public final static double TURN_DEGREES_2 = 50.0;
    public final static double TURN_AFTER_STACK = 60.0;
    public final static double END_HEADING = 20.0;
    public final static double APPROACH_SWITCH = 70.0;
    public final static double DEPART_CUBE = 5.0;
    public final static double APPROACH_CUBE = 71.0; //was 68.0

    public final static double BACK_UP = -30.0;

    public AutoMiddleSwitchTwoCubeBetter(GameData.FieldSide switchSide) {
        System.out.println("AutoMiddleSwitchTwoCubeBetter - side = " + switchSide);

        //Lift to switch position, ready to release
        addSequential(new WristToCollect());
        addSequential(new LiftToSwitch());
        addParallel(new WristHold());
        addParallel(new LiftHold());

        //Drive forward and turn in place and drive forward to switch
        addSequential(new DriveByMotionMagicAbsolute(STRAIGHT_1, 0, false));

        if (switchSide == GameData.FieldSide.right) {
            addSequential(new TurnByMotionMagicAbsolute(-TURN_DEGREES_1));
            addSequential(new DriveByMotionMagicAbsolute(APPROACH_SWITCH, -TURN_DEGREES_1, false));

            //Spit cube out
            addParallel(new ReleaseFast(0.8));
            addSequential(new TimeDelay(0.1));

            //Drive back
            addSequential(new DriveByMotionMagicAbsolute(-(APPROACH_SWITCH + 5), -TURN_DEGREES_1, false));
        } else if (switchSide == GameData.FieldSide.left) {
            addSequential(new TurnByMotionMagicAbsolute(TURN_DEGREES_2));
            addSequential(new DriveByMotionMagicAbsolute(APPROACH_SWITCH, TURN_DEGREES_2 - 20, false));

            //Spit cube out
            addParallel(new ReleaseFast(0.8));
            addSequential(new TimeDelay(0.1));

            //Drive back
            addSequential(new DriveByMotionMagicAbsolute(-(APPROACH_SWITCH + 5), TURN_DEGREES_1 + 10, false));
        }


        //Put lift down and start collector
        addSequential(new CollectPosition());
        addSequential(new CollectorStop());
        addParallel(new WristHold());
        addParallel(new LiftHold());
        addParallel(new Collect());

        //Turn back to straight
        addSequential(new TurnByMotionMagicAbsolute(0));

        //Drive into cube then back up into place
        addParallel(new DriveByMotionMagicAbsolute(APPROACH_CUBE, 0, false));
        addSequential(new TimeDelay(2.0));

        //Lift to switch position, ready to release
        addSequential(new WristToCollect());
        addSequential(new LiftToSwitch());
        addParallel(new WristHold());
        addParallel(new LiftHold());

        //Turn in place and drive forward
        if (switchSide == GameData.FieldSide.right) {
            addSequential(new TurnByMotionMagicAbsolute(-TURN_AFTER_STACK));
            addSequential(new DriveByMotionMagicAbsolute(APPROACH_SWITCH + 10, -END_HEADING, false));
        } else if (switchSide == GameData.FieldSide.left) {
            addSequential(new TurnByMotionMagicAbsolute(90));
            addSequential(new DriveByMotionMagicAbsolute((APPROACH_SWITCH + 10), 85, false)); //10 was 50 before
        }

        //Spit cube out
        addParallel(new ReleaseFast());
        addSequential(new TimeDelay(1.0));
        addSequential(new DriveByMotionMagicAbsolute(BACK_UP, 0, false));

        //Put lift down and stop collector
        addSequential(new CollectPosition());
        addSequential(new CollectorStop());
        addParallel(new WristHold());
        addParallel(new LiftHold());
    }
}
