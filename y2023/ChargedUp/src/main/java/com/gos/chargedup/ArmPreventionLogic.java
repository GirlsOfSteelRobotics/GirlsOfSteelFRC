package com.gos.chargedup;

import com.gos.chargedup.subsystems.ArmSubsystem;

public class ArmPreventionLogic {

    private final ArmSubsystem m_arm;

    //private final TurretSubsystem m_turret;

    public ArmPreventionLogic(ArmSubsystem arm) {
        this.m_arm = arm;
        //this.m_turret = turret;
    }

    public boolean canArmExtend() {     //mainly for fullExtend
        //check if arm hits intake
        return m_arm.getArmAngleDeg() > m_arm.ARM_HIT_UP_INTAKE_ANGLE;
    }

    public boolean canArmMoveVertically() {
        //check if arm is within allowable range
        return m_arm.getArmAngleDeg() >= m_arm.ARM_HIT_UP_INTAKE_ANGLE;
    }

    public boolean canTurretMove() {
        //check if arm is above intake
        return m_arm.getArmAngleDeg() > m_arm.ARM_HIT_UP_INTAKE_ANGLE;

        //sad failed range limit
        /*return (m_turret.getTurretAngleDeg() > m_turret.TURRET_LEFT_OF_INTAKE && m_turret.getTurretAngleDeg() < m_turret.TURRET_MAX_ANGLE) //CW range
               || (m_turret.getTurretAngleDeg() < m_turret.TURRET_RIGHT_OF_INTAKE && m_turret.getTurretAngleDeg() > m_turret.TURRET_MIN_ANGLE)  //CCW range*/

    }
}
