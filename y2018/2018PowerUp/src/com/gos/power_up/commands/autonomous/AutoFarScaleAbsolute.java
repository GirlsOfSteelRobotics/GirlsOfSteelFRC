package com.gos.power_up.commands.autonomous;

import com.gos.power_up.GameData;
import com.gos.power_up.commands.CollectPosition;
import com.gos.power_up.commands.CollectorStop;
import com.gos.power_up.commands.DriveByMotionMagic;
import com.gos.power_up.commands.DriveByMotionMagicAbsolute;
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
public class AutoFarScaleAbsolute extends CommandGroup {
    private static final double DISTANCE_FORWARD_1 = 160.0;
    private static final double TURN_RADIUS_1 = 110.0;
    private static final double TURN_HEADING_1 = 90.0;//absolute value
    private static final double DISTANCE_SIDE_1 = 110.0;
    private static final double TURN_RADIUS_2 = 80.0;
    private static final double TURN_HEADING_2 = 10;
    private static final double BACK_UP = 30.0;

    public AutoFarScaleAbsolute(GameData.FieldSide scaleSide) {
        System.out.println("AutoFarScaleAbsolute starting: scaleSide=" + scaleSide);

        addSequential(new WristToShoot());
        addParallel(new WristHold());

        //Initial forward distance past switch
        if (scaleSide == GameData.FieldSide.right) {
            addSequential(new DriveByMotionMagicAbsolute(DISTANCE_FORWARD_1 + 10.0, 0, false));
        } else {
            addSequential(new DriveByMotionMagicAbsolute(DISTANCE_FORWARD_1, 0, false));
        }

        //First turn behind the switch
        if (scaleSide == GameData.FieldSide.right) {
            addSequential(new DriveByMotionMagicAbsolute((TURN_RADIUS_1), -TURN_HEADING_1, true));
        } else {
            addSequential(new DriveByMotionMagicAbsolute(TURN_RADIUS_1, TURN_HEADING_1, true));
        }

        //Get lift and wrist into position
        addSequential(new LiftToScale());
        addParallel(new LiftHold());

        //Driving across the field behind the switch
        if (scaleSide == GameData.FieldSide.right) {
            addSequential(new DriveByMotionMagicAbsolute((DISTANCE_SIDE_1 - 7.0), -TURN_HEADING_1, false));
        } else {
            addSequential(new DriveByMotionMagicAbsolute(DISTANCE_SIDE_1, TURN_HEADING_1, false));
        }

        //Turning towards the scale
        if (scaleSide == GameData.FieldSide.right) {
            addSequential(new DriveByMotionMagicAbsolute(TURN_RADIUS_2, TURN_HEADING_2, true));
        } else {
            addSequential(new DriveByMotionMagicAbsolute(TURN_RADIUS_2, -TURN_HEADING_2, true));
        }

        //Release cube
        addParallel(new ReleaseFast(0.3));
        addSequential(new TimeDelay(1.0));
        addSequential(new DriveByMotionMagic(-BACK_UP, 0));

        //Put lift down and stop collector motors
        addSequential(new CollectPosition());
        addSequential(new CollectorStop());
        addParallel(new WristHold());
        addParallel(new LiftHold());

    }
}
