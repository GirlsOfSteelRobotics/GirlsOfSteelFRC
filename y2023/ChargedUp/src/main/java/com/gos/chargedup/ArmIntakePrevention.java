package com.gos.chargedup;

import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.IntakeSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;

public class ArmIntakePrevention {
    private final ArmSubsystem m_arm;

    private final TurretSubsystem m_turret;

    private final IntakeSubsystem m_intake;

    public ArmIntakePrevention(ArmSubsystem arm, TurretSubsystem turret, IntakeSubsystem intake) {
        this.m_arm = arm;
        this.m_turret = turret;
        this.m_intake = intake;
    }

    public boolean canArmExtendWithIntake() {     //mainly for fullExtend
        //annoying hard-code stuff cause we can't simulate pneumatic stuff; delete when needed
        boolean isIntakeDown = m_intake.isIntakeDown();
        //boolean isIntakeDown = true;

        if (isIntakeDown) {
            //when intake is down:
            // check if arm hits intake
            return m_arm.getArmAngleDeg() > m_arm.ARM_HIT_DOWN_INTAKE_ANGLE;
        } else {
            //when intake is up:
            //check if arm hits intake
            return m_arm.getArmAngleDeg() > m_arm.ARM_HIT_UP_INTAKE_ANGLE;
        }
    }

    public boolean canArmMoveVerticallyWithIntake() {
        boolean isIntakeDown = m_intake.isIntakeDown();
        //boolean isIntakeDown = true;

        if (isIntakeDown) {    //check if intake is down
            return true;
        } else {
            //when intake is up:
            //check if arm is within allowable range
            return m_arm.getArmAngleDeg() >= m_arm.ARM_HIT_UP_INTAKE_ANGLE;
        }
    }

    public boolean canTurretMoveWithIntake() {
        boolean isIntakeDown = m_intake.isIntakeDown();
        //boolean isIntakeDown = false;

        if (isIntakeDown) {
            //when intake is down:
            //check if arm is above min angle if intake is down
            if (m_arm.getArmAngleDeg() > m_arm.MIN_ANGLE_DEG) {
                return true;
            } else {
                //check if turret hits intake
                return m_turret.getTurretAngleDeg() > m_turret.TURRET_RIGHT_OF_INTAKE
                    || m_turret.getTurretAngleDeg() < m_turret.TURRET_LEFT_OF_INTAKE;
            }
        } else {
            //when intake is up:
            //check if arm is above intake
            return m_arm.getArmAngleDeg() > m_arm.ARM_HIT_UP_INTAKE_ANGLE;

            //sad failed range limit
            /*(m_turret.getTurretAngleDeg() > m_turret.TURRET_LEFT_OF_INTAKE //CW range
                && m_turret.getTurretAngleDeg() < m_turret.TURRET_MAX_ANGLE)
                || (m_turret.getTurretAngleDeg() < m_turret.TURRET_RIGHT_OF_INTAKE //CCW range
                && m_turret.getTurretAngleDeg() > m_turret.TURRET_MIN_ANGLE)*/

        }


    }
}
