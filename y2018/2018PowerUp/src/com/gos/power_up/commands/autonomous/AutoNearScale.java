package com.gos.power_up.commands.autonomous;

import com.gos.power_up.GameData;
import com.gos.power_up.Robot;
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
import com.gos.power_up.subsystems.Shifters;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoNearScale extends CommandGroup {

    private static final double DISTANCE_FORWARD = 295.0;
    private static final double DISTANCE_SIDE = -30.0;

    public AutoNearScale(GameData.FieldSide robotPosition) {
        System.out.println("AutoNearScale starting");

        //Move Robot, lift, wrist into position
        addSequential(new DriveByMotionMagic(DISTANCE_FORWARD, 0));
        Robot.m_shifters.shiftGear(Shifters.Speed.kLow);
        if (robotPosition == GameData.FieldSide.left) {
            addSequential(new TurnByMotionMagic(-90.0));
            addSequential(new DriveByMotionMagic(DISTANCE_SIDE, -90, false));
            addSequential(new WristToShoot());
            addSequential(new LiftToScale());
            addParallel(new WristHold());
            addParallel(new LiftHold());
        } else {
            addSequential(new TurnByMotionMagic(90.0));
            addSequential(new DriveByMotionMagic(DISTANCE_SIDE, 90, false));
            addSequential(new WristToShoot());
            addSequential(new LiftToScale());
            addParallel(new WristHold());
            addParallel(new LiftHold());

        }

        //Wait for lift and wrist to get into position then shoot
        addSequential(new TimeDelay(2.5));
        addParallel(new ReleaseFast(0.75));
        addSequential(new TimeDelay(1.5));

        //Put lift down and stop collector
        addSequential(new CollectPosition());
        addSequential(new CollectorStop());
        addParallel(new WristHold());
        addParallel(new LiftHold());

    }
}
