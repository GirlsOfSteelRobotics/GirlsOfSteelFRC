package com.gos.chargedup.autonomous;


import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.Constants;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.commands.ScorePieceCommandGroup;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.gos.lib.properties.GosDoubleProperty;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.HashMap;

public class OnePieceLeaveEngageCommandGroup extends SequentialCommandGroup {

    private final GosDoubleProperty m_forwardChargingVel = new GosDoubleProperty(false, "max Velocity Part 1", 2);
    private final GosDoubleProperty m_forwardChargingAcc = new GosDoubleProperty(false, "max Acceleration Part 1", 2);
    private final GosDoubleProperty m_backwardChargingVel = new GosDoubleProperty(false, "max Velocity Part 2", 3);
    private final GosDoubleProperty m_backwardChargingAcc = new GosDoubleProperty(false, "max Acceleration Part 2", 3);



    public OnePieceLeaveEngageCommandGroup(ChassisSubsystem chassis, ArmPivotSubsystem armPivot, ArmExtensionSubsystem armExtension, ClawSubsystem claw, AutoPivotHeight pivotHeightType, GamePieceType gamePieceType) {

        PathPlannerTrajectory forwardOverChargingStation1 = PathPlanner.loadPath("JustEngageBeforeConstraintsp1", Constants.DEFAULT_PATH_CONSTRAINTS, true);
        Command driveForwardOverChargingStation1 = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(forwardOverChargingStation1);

        PathPlannerTrajectory backwardOverChargingStation2 = PathPlanner.loadPath("JustEngageBeforeConstraintsp2", new PathConstraints(m_forwardChargingVel.getValue(), m_forwardChargingAcc.getValue()), true);
        Command driveBackwardOverChargingStation2 = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(backwardOverChargingStation2);

        PathPlannerTrajectory backwardOverChargingStation3 = PathPlanner.loadPath("JustEngageAfterConstraintsp3", new PathConstraints(m_backwardChargingVel.getValue(), m_backwardChargingAcc.getValue()), true);
        Command driveBackwardOverChargingStation3 = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(backwardOverChargingStation3);

        //score piece
        addCommands(new ScorePieceCommandGroup(armPivot, armExtension, claw, pivotHeightType, gamePieceType));

        //drive out
        addCommands(driveForwardOverChargingStation1);

        //drive over
        addCommands(driveBackwardOverChargingStation2);

        //drive back
        addCommands(driveBackwardOverChargingStation3);

        //engage
        addCommands(chassis.createAutoEngageCommand());


    }
}
