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
            return m_arm.isArmAtAngle(m_arm.MIN_ANGLE_DEG);   //check if intake is down & if arm is at min angle
        } else {
            return m_arm.getArmAngleDeg() > m_arm.ARM_HIT_INTAKE_ANGLE
                   && m_arm.getArmAngleDeg() <= m_arm.MAX_ANGLE_DEG;    //check if arm is within allowable range
        }
    }

    public boolean canArmMoveUP() {
        if (m_intake.getIntakeOut()) {    //check if intake is down
            return true;
        } else {
            //check if arm is within allowable range
            return (m_arm.getArmAngleDeg() > m_arm.ARM_HIT_INTAKE_ANGLE && m_arm.getArmAngleDeg() < m_arm.MAX_ANGLE_DEG);
        }
    }

    public boolean canArmPivot() {
        //check if turret faces intake and arm is extended and at min angle
        if (m_turret.getTurretAngleDeg() == 0 && !m_arm.isBottomPistonIn() && m_arm.isArmAtAngle(m_arm.MIN_ANGLE_DEG)) {
            m_arm.pivotArmStop();
            return false;
        }
        return true;

    }

}
