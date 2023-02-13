package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import com.gos.lib.checklists.DoubleSolenoidMovesChecklist;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.DoubleSupplier;

public class ClawSubsystem extends SubsystemBase {

    private final DoubleSolenoid m_claw;


    public ClawSubsystem() {
        m_claw = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.CLAW_PISTON_FORWARD, Constants.CLAW_PISTON_REVERSE);

    }

    //intake in
    public void moveClawIntakeIn() {
        m_claw.set(DoubleSolenoid.Value.kForward);
    }

    //intake out
    public void moveClawIntakeOut() {
        m_claw.set(DoubleSolenoid.Value.kReverse);
    }

    /////////////////////
    // Command Factories
    /////////////////////
    public CommandBase createMoveClawIntakeInCommand() {
        return this.runOnce(this::moveClawIntakeIn).withName("ClawIntakeIn");
    }

    public CommandBase createMoveClawIntakeOutCommand() {
        return this.runOnce(this::moveClawIntakeOut).withName("ClawIntakeOut");
    }

    public CommandBase createIsClawPneumaticMoving(DoubleSupplier pressureSupplier) {
        return new DoubleSolenoidMovesChecklist(this, pressureSupplier, m_claw, "Claw: Right Piston");
    }
}

