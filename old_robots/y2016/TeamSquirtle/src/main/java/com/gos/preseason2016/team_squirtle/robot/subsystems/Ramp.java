package com.gos.preseason2016.team_squirtle.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 *
 */
public class Ramp extends SubsystemBase {
    private final DoubleSolenoid m_rampPiston;

    public Ramp() {
        m_rampPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 6, 7);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void up() { // NOPMD(ShortMethodName)
        m_rampPiston.set(DoubleSolenoid.Value.kForward);
    }

    public void down() {
        m_rampPiston.set(DoubleSolenoid.Value.kReverse);
    }


}
