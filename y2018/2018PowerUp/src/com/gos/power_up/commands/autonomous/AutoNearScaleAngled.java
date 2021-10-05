package com.gos.power_up.commands.autonomous;

import com.gos.power_up.GameData;
import com.gos.power_up.commands.CollectPosition;
import com.gos.power_up.commands.CollectorStop;
import com.gos.power_up.commands.DriveByMotionMagic;
import com.gos.power_up.commands.LiftHold;
import com.gos.power_up.commands.LiftToScale;
import com.gos.power_up.commands.ReleaseFast;
import com.gos.power_up.commands.TimeDelay;
import com.gos.power_up.commands.WristHold;
import com.gos.power_up.commands.WristToShoot;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoNearScaleAngled extends CommandGroup {


    //distance constants
    private static final double DISTANCE_FORWARD_1 = 235.0;
    private static final double DISTANCE_TURN_1 = 25;
    private static final double BACK_UP = -30.0;


    public AutoNearScaleAngled(GameData.FieldSide robotPosition) {
        System.out.println("AutoFarScale starting");

        //moves robot forward
        addParallel(new DriveByMotionMagic(DISTANCE_FORWARD_1, 0));
        addSequential(new TimeDelay(2.0));

        //gets lift & wrist into position
        addSequential(new WristToShoot());
        addSequential(new LiftToScale());
        addParallel(new WristHold());
        addParallel(new LiftHold());
        addSequential(new TimeDelay(3.0));

        //turn
        if (robotPosition == GameData.FieldSide.left) {
            addSequential(new DriveByMotionMagic(DISTANCE_TURN_1, -30));
        } else {
            addSequential(new DriveByMotionMagic(DISTANCE_TURN_1, 30));
        }

        //release cube and back up
        addParallel(new ReleaseFast(0.5));
        addSequential(new TimeDelay(1.0));
        addSequential(new DriveByMotionMagic(BACK_UP, 0));

        //puts lift down and stops collector
        addSequential(new CollectPosition());
        addSequential(new CollectorStop());
        addParallel(new WristHold());
        addParallel(new LiftHold());
    }
}
