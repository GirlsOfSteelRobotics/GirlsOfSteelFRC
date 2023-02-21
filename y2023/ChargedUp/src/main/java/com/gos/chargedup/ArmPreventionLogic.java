package com.gos.chargedup;

import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.IntakeSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;

public class ArmPreventionLogic {

    private final ArmSubsystem m_arm;

    private final TurretSubsystem m_turret;

    private final IntakeSubsystem m_intake;

    public ArmPreventionLogic(ArmSubsystem arm, TurretSubsystem turret, IntakeSubsystem intake) {
        this.m_arm = arm;
        this.m_turret = turret;
        this.m_intake = intake;

    }


    public boolean canArmExtend() {
        if (m_intake.getIntakeOut()) {
            return m_arm.isArmAtAngle(m_arm.MIN_ANGLE_DEG);
        } else if (m_arm.getArmAngleDeg() > m_arm.ARM_HIT_INTAKE_ANGLE) {
            return true;
        } else {
            m_arm.pivotArmStop();
            return false;
        }
    }

    public boolean canArmMoveUP() {
        if (m_intake.getIntakeOut()) {
            return true;
        } else {
            if (m_arm.getArmAngleDeg() > m_arm.ARM_HIT_INTAKE_ANGLE && m_arm.getArmAngleDeg() < m_arm.MAX_ANGLE_DEG) {
                return true;
            }
        }
        m_arm.pivotArmStop();
        return false;
    }

    public boolean canArmPivot() {
        if (m_turret.getTurretAngleDeg() == 0 && !m_arm.isBottomPistonIn() && m_arm.isArmAtAngle(m_arm.MIN_ANGLE_DEG)) {
            m_arm.pivotArmStop();
            return false;
        }
        return true;

    }

}
