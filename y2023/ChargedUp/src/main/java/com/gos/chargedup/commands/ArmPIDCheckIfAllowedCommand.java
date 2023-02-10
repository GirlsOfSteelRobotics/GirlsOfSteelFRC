package com.gos.chargedup.commands;

import com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.IntakeSubsystem;


public class ArmPIDCheckIfAllowedCommand extends CommandBase {
    private final ArmSubsystem m_armSubsystem;
    private final IntakeSubsystem m_intakeSubsystem;
    private final double m_goalAngle;

    private final TurretSubsystem m_turretSubsytem;

    public ArmPIDCheckIfAllowedCommand(ArmSubsystem armSubsystem, IntakeSubsystem intakeSubsystem, TurretSubsystem turretSubsystem, double goalAngle) {
        this.m_armSubsystem = armSubsystem;
        this.m_intakeSubsystem = intakeSubsystem;
        this.m_turretSubsytem = turretSubsystem;
        m_goalAngle = goalAngle;


        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_armSubsystem, this.m_intakeSubsystem);
    }

    @Override
    public void initialize() {

    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    @Override
    public void execute() {
        if (m_turretSubsytem.getTurretAngleDeg() < TurretSubsystem.TURRET_LEFT_OF_INTAKE || m_turretSubsytem.getTurretAngleDeg() > TurretSubsystem.TURRET_RIGHT_OF_INTAKE) {
            m_armSubsystem.pivotArmToAngle(m_goalAngle);
        }
        else if (m_turretSubsytem.getTurretAngleDeg() > TurretSubsystem.TURRET_LEFT_OF_INTAKE && m_turretSubsytem.getTurretAngleDeg() < TurretSubsystem.TURRET_RIGHT_OF_INTAKE) {

            if (m_intakeSubsystem.getIntakeOut()) {
                m_armSubsystem.pivotArmToAngle(m_goalAngle);
            } else if (!m_intakeSubsystem.getIntakeOut()) {
                if (m_armSubsystem.getArmAngleDeg() < m_armSubsystem.ARM_HIT_INTAKE_ANGLE) {
                    m_armSubsystem.pivotArmStop();
                } else if (m_armSubsystem.getArmAngleDeg() > m_armSubsystem.ARM_HIT_INTAKE_ANGLE && m_armSubsystem.getArmAngleDeg() < m_armSubsystem.getArmAngleGoal()) {
                    m_armSubsystem.pivotArmToAngle(m_goalAngle);
                } else {
                    m_armSubsystem.pivotArmStop();
                }

            }
        }
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return m_armSubsystem.pivotArmToAngle(m_goalAngle);
    }

    @Override
    public void end(boolean interrupted) {

    }
}
