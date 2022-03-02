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
import com.gos.power_up.subsystems.Chassis;
import com.gos.power_up.subsystems.Collector;
import com.gos.power_up.subsystems.Lift;
import com.gos.power_up.subsystems.Wrist;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 *
 */
public class AutoFarScaleAbsolute extends SequentialCommandGroup {
    private static final double DISTANCE_FORWARD_1 = 160.0;
    private static final double TURN_RADIUS_1 = 110.0;
    private static final double TURN_HEADING_1 = 90.0; //absolute value
    private static final double DISTANCE_SIDE_1 = 110.0;
    private static final double TURN_RADIUS_2 = 80.0;
    private static final double TURN_HEADING_2 = 10;
    private static final double BACK_UP = 30.0;

    public AutoFarScaleAbsolute(Chassis chassis, Lift lift, Wrist wrist, Collector collector, GameData.FieldSide scaleSide) {
        System.out.println("AutoFarScaleAbsolute starting: scaleSide=" + scaleSide);

        addCommands(new WristToShoot(wrist));
        addParallel(new WristHold(wrist));

        //Initial forward distance past switch
        if (scaleSide == GameData.FieldSide.right) {
            addCommands(new DriveByMotionMagicAbsolute(chassis, DISTANCE_FORWARD_1 + 10.0, 0, false));
        } else {
            addCommands(new DriveByMotionMagicAbsolute(chassis, DISTANCE_FORWARD_1, 0, false));
        }

        //First turn behind the switch
        if (scaleSide == GameData.FieldSide.right) {
            addCommands(new DriveByMotionMagicAbsolute(chassis, TURN_RADIUS_1, -TURN_HEADING_1, true));
        } else {
            addCommands(new DriveByMotionMagicAbsolute(chassis, TURN_RADIUS_1, TURN_HEADING_1, true));
        }

        //Get lift and wrist into position
        addCommands(new LiftToScale(lift));
        addParallel(new LiftHold(lift));

        //Driving across the field behind the switch
        if (scaleSide == GameData.FieldSide.right) {
            addCommands(new DriveByMotionMagicAbsolute(chassis, DISTANCE_SIDE_1 - 7.0, -TURN_HEADING_1, false));
        } else {
            addCommands(new DriveByMotionMagicAbsolute(chassis, DISTANCE_SIDE_1, TURN_HEADING_1, false));
        }

        //Turning towards the scale
        if (scaleSide == GameData.FieldSide.right) {
            addCommands(new DriveByMotionMagicAbsolute(chassis, TURN_RADIUS_2, TURN_HEADING_2, true));
        } else {
            addCommands(new DriveByMotionMagicAbsolute(chassis, TURN_RADIUS_2, -TURN_HEADING_2, true));
        }

        //Release cube
        addParallel(new ReleaseFast(collector, 0.3));
        addCommands(new TimeDelay(1.0));
        addCommands(new DriveByMotionMagic(chassis, -BACK_UP, 0));

        //Put lift down and stop collector motors
        addCommands(new CollectPosition(lift, wrist));
        addCommands(new CollectorStop(collector));
        addParallel(new WristHold(wrist));
        addParallel(new LiftHold(lift));

    }
}
