package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import com.gos.chargedup.commands.DoubleSolenoidMoveTest;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

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

    public CommandBase createIsClawPneumaticMoving(PneumaticHub pneumaticHub) {
        return new DoubleSolenoidMoveTest(pneumaticHub, m_claw, Constants.PRESSURE_SENSOR_PORT, "Claw: Right Piston");
    }
}

