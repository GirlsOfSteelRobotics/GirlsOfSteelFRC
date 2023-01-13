package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    private final Solenoid m_intakeSolenoid;


    public IntakeSubsystem() {

        m_intakeSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Constants.SOLENOID_INTAKE);

    }

    public void extend() {
        m_intakeSolenoid.set(true);

    }

    public void retract() {
        m_intakeSolenoid.set(false);

    }

    public Command createExtendSolenoidCommand() {
        return this.runOnce(this::extend);
    }

    public Command createRetractSolenoidCommand() {
        return this.runOnce(this::retract);
    }
}

