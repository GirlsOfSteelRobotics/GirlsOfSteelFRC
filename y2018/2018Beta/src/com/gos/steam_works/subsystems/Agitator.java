package com.gos.steam_works.subsystems;

import com.gos.steam_works.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Agitator extends Subsystem {

    private final DoubleSolenoid m_agitator;

    public Agitator() {
        m_agitator = new DoubleSolenoid(RobotMap.AGITATOR_A, RobotMap.AGITATOR_B);

        addChild("agitator", m_agitator);
    }

    public void agitateForwards() {
        m_agitator.set(DoubleSolenoid.Value.kForward);
    }

    public void agitateBackwards() {
        m_agitator.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    public void initDefaultCommand() {
    }
}
