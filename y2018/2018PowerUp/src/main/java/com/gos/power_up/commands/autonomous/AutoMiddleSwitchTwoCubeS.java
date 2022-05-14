package com.gos.power_up.commands.autonomous;

import com.gos.power_up.GameData;
import com.gos.power_up.commands.Collect;
import com.gos.power_up.commands.CollectPosition;
import com.gos.power_up.commands.CollectorHold;
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
public class AutoMiddleSwitchTwoCubeS extends SequentialCommandGroup {
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
        addCommands(new WristToCollect(wrist));
        addCommands(new ParallelCommandGroup(
            new LiftToSwitch(lift),
            new WristHold(wrist),
            new LiftHold(lift)));

        //Drive to the switch plate
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
        addCommands(new ReleaseSlow(collector).withTimeout(0.5));
        addCommands(new DriveByMotionMagic(chassis, LONG_BACK_UP, 0));

        //Put lift down and start collecting
        addCommands(new ParallelCommandGroup(
            new CollectPosition(lift, wrist),
            new WristHold(wrist),
            new LiftHold(lift),
            new Collect(collector)));

        //Grab second cube and come back
        if (switchSide == GameData.FieldSide.RIGHT) {
            addCommands(new DriveByMotionMagic(chassis, TURN_RADIUS_2, TURN_DEGREES_2)
                .alongWith(new CollectorHold(collector)));
            addCommands(new DriveByMotionMagic(chassis, -TURN_RADIUS_2 / 2, -20, false));
        } else if (switchSide == GameData.FieldSide.LEFT) {
            addCommands(new DriveByMotionMagic(chassis, TURN_RADIUS_2, -TURN_DEGREES_2)
                .alongWith(new CollectorHold(collector)));
            addCommands(new DriveByMotionMagic(chassis, -TURN_RADIUS_2 / 2, 20, false));
        } else {
            System.out.println("AutoMiddleSwitch: invalid switch side");
        }

        //lift up to shoot out
        addCommands(new WristToCollect(wrist));
        addCommands(new ParallelCommandGroup(
            new LiftToSwitch(lift),
            new WristHold(wrist),
            new LiftHold(lift)));

        //Approach switch plate
        addCommands(new DriveByMotionMagic(chassis, -LONG_BACK_UP, 0));

        //Release and back up
        addCommands(new ReleaseSlow(collector).withTimeout(0.5));
        addCommands(new DriveByMotionMagic(chassis, SHORT_BACK_UP, 0));
    }
}
