package com.gos.chargedup.autonomous;


import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.gos.chargedup.subsystems.IntakeSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.HashMap;


public class TWOPieceNodes0And1CommandGroup extends SequentialCommandGroup {

    public static final PathPlannerTrajectory TWOPieceNodes0And1 = PathPlanner.loadPath("TWOPieceNodes0And1", new PathConstraints(4, 3));

    public TWOPieceNodes0And1CommandGroup(ChassisSubsystem chassis, TurretSubsystem turret, ArmSubsystem arm, ClawSubsystem claw, IntakeSubsystem intake) {

        HashMap<String, Command> eventMap = new HashMap<>();
        eventMap.put("pickUpObject", new SequentialCommandGroup(
            claw.createMoveClawIntakeInCommand() //piece is firmly in the claw? finish + tune soon



        ));

        addCommands(chassis.followTrajectoryCommand(TWOPieceNodes0And1, true));
    }
}