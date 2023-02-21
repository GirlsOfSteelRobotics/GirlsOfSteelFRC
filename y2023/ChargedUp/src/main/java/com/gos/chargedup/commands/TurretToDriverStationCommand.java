package com.gos.chargedup.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;


public class TurretToDriverStationCommand extends CommandBase {
    private final ChassisSubsystem m_chassisSubsystem;
    private final TurretSubsystem m_turretSubsystem;

    private double m_chassisAngle;

    private double m_angleFromField0;

    public TurretToDriverStationCommand(ChassisSubsystem chassisSubsystem, TurretSubsystem turretSubsystem) {
        this.m_chassisSubsystem = chassisSubsystem;
        this.m_turretSubsystem = turretSubsystem;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_chassisSubsystem, this.m_turretSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_chassisAngle = m_chassisSubsystem.getPose().getRotation().getDegrees();
        m_angleFromField0 = m_turretSubsystem.getTurretAngleDeg() - m_chassisAngle;
        m_turretSubsystem.moveTurretToAngleWithPID(m_angleFromField0 + 180);
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return m_turretSubsystem.moveTurretToAngleWithPID(m_angleFromField0 + 180);
    }

    @Override
    public void end(boolean interrupted) {

    }
}
