package com.gos.chargedup.autonomous;


import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TestLineClassCommandGroup extends SequentialCommandGroup {

    static PathPlannerTrajectory examplePath = PathPlanner.loadPath("TestLine", new PathConstraints(4, 3));



    public TestLineClassCommandGroup(ChassisSubsystem chassis) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super(
            chassis.followTrajectoryCommand(examplePath, true)
        );
    }
}