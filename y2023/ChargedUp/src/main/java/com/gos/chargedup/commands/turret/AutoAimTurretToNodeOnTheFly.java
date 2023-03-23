package com.gos.chargedup.commands.turret;

import com.gos.chargedup.AutoAimNodePositions;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.LEDManagerSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;

import java.util.function.Supplier;


public class AutoAimTurretToNodeOnTheFly extends BaseAutoAimTurretCommand {
    private final Supplier<AutoAimNodePositions> m_positionSupplier;

    public AutoAimTurretToNodeOnTheFly(Supplier<AutoAimNodePositions> positionSupplier, ArmPivotSubsystem armSubsystem, ArmExtensionSubsystem armExtension, ChassisSubsystem chassisSubsystem, TurretSubsystem turretSubsystem, LEDManagerSubsystem ledManagerSubsystem) {
        super(armSubsystem, chassisSubsystem, turretSubsystem, ledManagerSubsystem, armExtension);
        m_positionSupplier = positionSupplier;
    }

    @Override
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public void execute() {
        AutoAimNodePositions position = m_positionSupplier.get();
        if (position == null || position == AutoAimNodePositions.NONE) {
            return;
        }

        runAutoAim(position.getBaseLocation(), position.getTargetPitch());
    }
}
