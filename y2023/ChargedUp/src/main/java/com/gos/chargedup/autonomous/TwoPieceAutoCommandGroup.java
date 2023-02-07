package com.gos.chargedup.autonomous;

import com.gos.chargedup.Constants;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TwoPieceAutoCommandGroup extends SequentialCommandGroup {

    public static final PathPlannerTrajectory TWO_PIECE_AUTO = PathPlanner.loadPath("TwoPieceAuto", new PathConstraints(Constants.AUTO_VELOCITY, Constants.AUTO_ACCELERATION));

    public TwoPieceAutoCommandGroup(ChassisSubsystem chassis) {
        super(
            chassis.followTrajectoryCommand(TWO_PIECE_AUTO, true)
        );
    }
}
