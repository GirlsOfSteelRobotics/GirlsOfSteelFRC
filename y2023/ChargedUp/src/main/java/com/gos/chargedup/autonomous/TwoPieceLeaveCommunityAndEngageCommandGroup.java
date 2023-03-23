package com.gos.chargedup.autonomous;


import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.Constants;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.commands.CombinedCommandsUtil;
import com.gos.chargedup.commands.ScorePieceCommandGroup;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.HashMap;

public class TwoPieceLeaveCommunityAndEngageCommandGroup extends SequentialCommandGroup {
    public TwoPieceLeaveCommunityAndEngageCommandGroup(ChassisSubsystem chassis, ArmPivotSubsystem armPivot, ArmExtensionSubsystem armExtension, ClawSubsystem claw, String path, AutoPivotHeight pivotHeightType, GamePieceType gamePieceType) {
        PathPlannerTrajectory twoPieceLeaveAndEngage = PathPlanner.loadPath(path, Constants.DEFAULT_PATH_CONSTRAINTS, true);
        Command driveAutoOnePieceAndLeave = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(twoPieceLeaveAndEngage);

        //score
        addCommands(new ScorePieceCommandGroup(armPivot, armExtension, claw, pivotHeightType, gamePieceType));

        //drive out of community across engage
        addCommands(driveAutoOnePieceAndLeave);

        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
    }
}