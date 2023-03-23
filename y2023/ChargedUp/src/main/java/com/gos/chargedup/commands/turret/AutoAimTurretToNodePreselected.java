package com.gos.chargedup.commands.turret;

import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.LEDManagerSubsystem;
import edu.wpi.first.math.geometry.Translation2d;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;


public class AutoAimTurretToNodePreselected extends BaseAutoAimTurretCommand {
    private final Translation2d m_baseTargetLocation;
    private final double m_targetPitch;


    public AutoAimTurretToNodePreselected(ArmPivotSubsystem armSubsystem, ArmExtensionSubsystem armExtension, ChassisSubsystem chassisSubsystem, TurretSubsystem turretSubsystem, Translation2d targetPos, String position, GamePieceType gamePiece, AutoPivotHeight height, LEDManagerSubsystem ledManagerSubsystem) {
        super(armSubsystem, chassisSubsystem, turretSubsystem, ledManagerSubsystem, armExtension);
        setName("Score " + gamePiece + " " + position + " " + height);

        m_baseTargetLocation = targetPos;
        m_targetPitch = ArmPivotSubsystem.getArmAngleForScoring(height, gamePiece);
    }

    @Override
    public void execute() {
        runAutoAim(m_baseTargetLocation, m_targetPitch);
    }

}
