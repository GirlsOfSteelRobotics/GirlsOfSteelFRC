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
import com.gos.power_up.subsystems.Chassis;
import com.gos.power_up.subsystems.Collector;
import com.gos.power_up.subsystems.Lift;
import com.gos.power_up.subsystems.Wrist;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
@SuppressWarnings("PMD.DataClass")
public class AutoMiddleSwitchTwoCubeS extends CommandGroup {
    //Parameters for first cube
    public static final double RIGHT_ANGLE = 53.0;
    public static final double RIGHT_DISTANCE = 72.0;
    public static final double LEFT_ANGLE = 65.0;
    public static final double LEFT_DISTANCE = 75.0;

    //Parameters for collecting second cube
    public static final double LONG_BACK_UP = -80.0;
    public static final double TURN_RADIUS_2 = 100.0;
    public static final double TURN_DEGREES_2 = 70.0;

    //After second cube
    public static final double SHORT_BACK_UP = -30.0;

    public AutoMiddleSwitchTwoCubeS(Chassis chassis, Lift lift, Wrist wrist, Collector collector, GameData.FieldSide switchSide) {

        //Raise lift, lower wrist to get ready to spit cube out
        addSequential(new WristToCollect(wrist));
        addSequential(new LiftToSwitch(lift));
        addParallel(new WristHold(wrist));
        addParallel(new LiftHold(lift));

        //Drive to the switch plate
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
        addSequential(new TimeDelay(0.5));
        addSequential(new DriveByMotionMagic(chassis, LONG_BACK_UP, 0));

        //Put lift down and start collecting
        addSequential(new CollectPosition(lift, wrist));
        addParallel(new WristHold(wrist));
        addParallel(new LiftHold(lift));
        addParallel(new Collect(collector));

        //Grab second cube and come back
        if (switchSide == GameData.FieldSide.right) {
            addSequential(new DriveByMotionMagic(chassis, TURN_RADIUS_2, TURN_DEGREES_2));
            addParallel(new CollectorHold(collector));
            addSequential(new DriveByMotionMagic(chassis, -TURN_RADIUS_2 / 2, -20, false));
        } else if (switchSide == GameData.FieldSide.left) {
            addSequential(new DriveByMotionMagic(chassis, TURN_RADIUS_2, -TURN_DEGREES_2));
            addParallel(new CollectorHold(collector));
            addSequential(new DriveByMotionMagic(chassis, -TURN_RADIUS_2 / 2, 20, false));
        } else {
            System.out.println("AutoMiddleSwitch: invalid switch side");
        }

        //lift up to shoot out
        addSequential(new WristToCollect(wrist));
        addSequential(new LiftToSwitch(lift));
        addParallel(new WristHold(wrist));
        addParallel(new LiftHold(lift));

        //Approach switch plate
        addSequential(new DriveByMotionMagic(chassis, -LONG_BACK_UP, 0));

        //Release and back up
        addParallel(new ReleaseSlow(collector));
        addSequential(new TimeDelay(0.5));
        addSequential(new DriveByMotionMagic(chassis, SHORT_BACK_UP, 0));
    }
}
