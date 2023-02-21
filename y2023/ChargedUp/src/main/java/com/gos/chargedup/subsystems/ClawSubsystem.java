package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import com.gos.lib.checklists.DoubleSolenoidMovesChecklist;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.DoubleSupplier;

public class ClawSubsystem extends SubsystemBase {
    public static boolean clawNoWork;
    public static boolean clawReverse;

    private final DoubleSolenoid m_claw;
    private static final double CLAW_WAIT = 2;


    public ClawSubsystem() {
        clawNoWork = false;
        clawReverse = false;

        m_claw = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.CLAW_PISTON_FORWARD, Constants.CLAW_PISTON_REVERSE);

    }

    //intake close
    public void moveClawIntakeClose() {
        if(!clawNoWork && !clawReverse)
            m_claw.set(DoubleSolenoid.Value.kReverse);
        else if(clawReverse)
            m_claw.set(DoubleSolenoid.Value.kForward);
    }

    //intake open
    public void moveClawIntakeOpen() {
        if(!clawNoWork && !clawReverse)
            m_claw.set(DoubleSolenoid.Value.kForward);
        else if(clawReverse)
            m_claw.set(DoubleSolenoid.Value.kReverse);
    }

    /////////////////////
    // Command Factories
    /////////////////////
    public CommandBase createMoveClawIntakeCloseCommand() {
        return this.run(this::moveClawIntakeClose).withTimeout(CLAW_WAIT).withName("ClawIntakeClose");
    }

    public CommandBase createMoveClawIntakeOpenCommand() {
        return this.run(this::moveClawIntakeOpen).withTimeout(CLAW_WAIT).withName("ClawIntakeOpen");
    }

    //////////////
    // Checklists
    //////////////
    public CommandBase createIsClawPneumaticMoving(DoubleSupplier pressureSupplier) {
        return new DoubleSolenoidMovesChecklist(this, pressureSupplier, m_claw, "Claw: Right Piston");
    }
}

