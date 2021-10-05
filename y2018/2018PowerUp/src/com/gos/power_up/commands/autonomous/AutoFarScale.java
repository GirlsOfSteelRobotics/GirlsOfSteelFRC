package com.gos.power_up.commands.autonomous;

import com.gos.power_up.GameData;
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

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoFarScale extends CommandGroup {
    private final double DISTANCE_FORWARD_1 = 175.0;
    private final double TURN_RADIUS_1 = 100.0;
    private final double DISTANCE_SIDE_1 = 80.0;
    private final double TURN_RADIUS_2 = 150.0;
    private final double DEGREES_2 = 90.0;

    public AutoFarScale(GameData.FieldSide scaleSide) {
        System.out.println("AutoFarScale starting: scaleSide=" + scaleSide);

        //Initial forward distance past switch
        addSequential(new DriveByMotionMagic(DISTANCE_FORWARD_1, 0));

        //First turn behind the switch
        if (scaleSide == GameData.FieldSide.right) addSequential(new AutoTurnRight(TURN_RADIUS_1));
        else addSequential(new AutoTurnLeft(TURN_RADIUS_1));

        //Get lift and wrist into position
        addSequential(new LiftToScale());
        addSequential(new WristToShoot());
        addParallel(new WristHold());
        addParallel(new LiftHold());

        //Driving across the field behind the switch
        if (scaleSide == GameData.FieldSide.right) addSequential(new DriveByMotionMagic(DISTANCE_SIDE_1, -90, false));
        else addSequential(new DriveByMotionMagic(DISTANCE_SIDE_1, 90, false));

        //Turning towards the scale
        if (scaleSide == GameData.FieldSide.right)
        {
            addSequential(new DriveByMotionMagic(TURN_RADIUS_2, DEGREES_2));
            addSequential(new TurnByMotionMagic(90));
        }
        else
        {
            addSequential(new DriveByMotionMagic(TURN_RADIUS_2, -DEGREES_2));
            addSequential(new TurnByMotionMagic(-90));
        }

        //Release cube
        addParallel(new ReleaseFast(0.5));
        addSequential(new TimeDelay(1.0));

        //Put lift down and stop collector motors
        addSequential(new CollectPosition());
        addSequential(new CollectorStop());
        addParallel(new WristHold());
        addParallel(new LiftHold());

    }
}
