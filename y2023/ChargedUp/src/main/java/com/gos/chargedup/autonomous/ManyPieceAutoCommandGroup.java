package com.gos.chargedup.autonomous;

import com.gos.chargedup.Constants;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ManyPieceAutoCommandGroup extends SequentialCommandGroup {

    public static final PathPlannerTrajectory MANY_PIECE_AUTO = PathPlanner.loadPath("ManyPieceAuto", new PathConstraints(Constants.AUTO_VELOCITY, Constants.AUTO_ACCELERATION));

    public ManyPieceAutoCommandGroup(ChassisSubsystem chassis) {
        super(
            chassis.followTrajectoryCommand(MANY_PIECE_AUTO, true)
        );
    }
}
