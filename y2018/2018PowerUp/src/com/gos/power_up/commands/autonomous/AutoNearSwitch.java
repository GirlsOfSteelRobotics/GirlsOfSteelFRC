package com.gos.power_up.commands.autonomous;

import com.gos.power_up.GameData;
import com.gos.power_up.Robot;
import com.gos.power_up.commands.CollectPosition;
import com.gos.power_up.commands.CollectorStop;
import com.gos.power_up.commands.DriveByMotionMagic;
import com.gos.power_up.commands.LiftHold;
import com.gos.power_up.commands.LiftToSwitch;
import com.gos.power_up.commands.ReleaseFast;
import com.gos.power_up.commands.TimeDelay;
import com.gos.power_up.commands.WristHold;
import com.gos.power_up.commands.WristToCollect;
import com.gos.power_up.subsystems.Shifters;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoNearSwitch extends CommandGroup {

    private final double DISTANCE_FORWARD = 130.0;
    private final double DISTANCE_SIDE = 0.0;
    private final double BACK_UP = -30;

    public AutoNearSwitch(GameData.FieldSide robotPosition) {
        System.out.println("AutoNearSwitch starting");

        //Get lift & wrist into position
        Robot.shifters.shiftGear(Shifters.Speed.kLow);

        addSequential(new WristToCollect());
        addSequential(new LiftToSwitch());
        addParallel(new WristHold());
        addParallel(new LiftHold());

        //Move Robot into position
        addSequential(new DriveByMotionMagic(DISTANCE_FORWARD, 0));
        if (robotPosition == GameData.FieldSide.left) addSequential(new AutoTurnRight(25.0));
        else addSequential(new AutoTurnLeft(25.0));
        addSequential(new DriveByMotionMagic(DISTANCE_SIDE, 0));

        //Release and back up
        addParallel(new ReleaseFast());
        addSequential(new TimeDelay(1.0));
        addSequential(new DriveByMotionMagic(BACK_UP, 0));

        //Put lift down and stop collector
        addSequential(new CollectPosition());
        addSequential(new CollectorStop());
        addParallel(new WristHold());
        addParallel(new LiftHold());
    }
}
