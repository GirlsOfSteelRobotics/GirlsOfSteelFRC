package com.gos.chargedup.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;


public class AutomatedTurretToSelectedPegCommand extends CommandBase {
    private final ChassisSubsystem m_chassisSubsystem;
    private final TurretSubsystem m_turretSubsystem;
    private final Translation2d m_nodePosition;

    public AutomatedTurretToSelectedPegCommand(ChassisSubsystem chassisSubsystem, TurretSubsystem turretSubsystem, Translation2d nodePosition) {
        this.m_chassisSubsystem = chassisSubsystem;
        this.m_turretSubsystem = turretSubsystem;
        this.m_nodePosition = nodePosition;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_turretSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        final Pose2d currentRobotPosition = m_chassisSubsystem.getPose();
        double xDistance = m_nodePosition.getX() - currentRobotPosition.getX();
        double yDistance = m_nodePosition.getY() - currentRobotPosition.getY();
        double robotAngle = Math.toDegrees(Math.atan2(yDistance, xDistance));
        double angleAdjustment = robotAngle + currentRobotPosition.getRotation().getDegrees() + 180;
        m_turretSubsystem.turretPID(angleAdjustment);
    }


    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
