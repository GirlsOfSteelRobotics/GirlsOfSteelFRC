package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import com.gos.chargedup.commands.PneumaticsMoveTest;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsystem extends SubsystemBase {

    private final Solenoid m_rightIntake;

    private final Solenoid m_leftIntake; //NOPMD

    private static final double CLAW_WAIT = 2;


    public ClawSubsystem() {
        m_rightIntake = new Solenoid(PneumaticsModuleType.REVPH, Constants.RIGHT_CLAW_PISTON);
        m_leftIntake = new Solenoid(PneumaticsModuleType.REVPH, Constants.LEFT_INTAKE_PISTON);

    }

    //intake in
    public void moveClawIntakeIn() {
        m_rightIntake.set(true);
        m_leftIntake.set(true);
    }

    //intake out
    public void moveClawIntakeOut() {
        m_rightIntake.set(false);
        m_leftIntake.set(false);
    }

    /////////////////////
    // Command Factories
    /////////////////////
    public CommandBase createMoveClawIntakeInCommand() {
        return this.run(this::moveClawIntakeIn).withName("ClawIntakeIn").withTimeout(CLAW_WAIT);
    }

    public CommandBase createMoveClawIntakeOutCommand() {
        return this.run(this::moveClawIntakeOut).withName("ClawIntakeOut").withTimeout(CLAW_WAIT);
    }

    public CommandBase createIsRightClawPneumaticMoving(PneumaticHub pneumaticHub) {
        return new PneumaticsMoveTest(pneumaticHub, m_rightIntake, Constants.RIGHT_CLAW_PISTON, "Claw: Right Piston");
    }

    public CommandBase createIsLeftClawPneumaticMoving(PneumaticHub pneumaticHub) {
        return new PneumaticsMoveTest(pneumaticHub, m_leftIntake, Constants.LEFT_CLAW_PISTON, "Claw: Left Piston");
    }
}

