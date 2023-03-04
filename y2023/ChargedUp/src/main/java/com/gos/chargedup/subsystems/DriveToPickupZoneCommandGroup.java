package com.gos.chargedup.subsystems;


import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class DriveToPickupZoneCommandGroup extends SequentialCommandGroup {

    ChassisSubsystem m_chassis;

    Translation2d targetDestination =  new Translation2d(0,0);

    public DriveToPickupZoneCommandGroup(ChassisSubsystem chassis) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super();
        m_chassis = chassis;

    }

    public void driveToPickupZone() {
        PathPlannerTrajectory traj1 = PathPlanner.generatePath(
            new PathConstraints(4, 3),
            new PathPoint(new Translation2d(m_chassis.getPose().getX(), m_chassis.getPose().getY()), Rotation2d.fromDegrees(m_chassis.getPose().getRotation().getDegrees())), // position, heading
            new PathPoint(targetDestination, Rotation2d.fromDegrees(0)) // position, heading
        );
    }
}