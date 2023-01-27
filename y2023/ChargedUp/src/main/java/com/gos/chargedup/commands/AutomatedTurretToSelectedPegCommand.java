package com.gos.chargedup.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;


public class AutomatedTurretToSelectedPegCommand extends CommandBase {
    private final ChassisSubsystem chassisSubsystem;
    private final TurretSubsystem turretSubsystem;
    private final Pose2d m_nodePosition;

    public AutomatedTurretToSelectedPegCommand(ChassisSubsystem chassisSubsystem, TurretSubsystem turretSubsystem, Pose2d m_nodePosition) {
        this.chassisSubsystem = chassisSubsystem;
        this.turretSubsystem = turretSubsystem;
        m_nodePosition = m_nodePosition;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.chassisSubsystem, this.turretSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        final Pose2d currentRobotPosition = chassisSubsystem.getPose();
        double xDistance = m_nodePosition.getX() - currentRobotPosition.getX();
        double yDistance = m_nodePosition.getY() - currentRobotPosition.getY();
        double robotAngle = Math.arctan2(yDistance/xDistance);
        set robotAngle = turret angle
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
