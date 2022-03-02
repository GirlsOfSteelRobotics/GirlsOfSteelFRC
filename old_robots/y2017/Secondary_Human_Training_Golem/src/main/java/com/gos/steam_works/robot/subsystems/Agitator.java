package com.gos.steam_works.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.steam_works.robot.RobotMap;

/**
 *
 */
public class Agitator extends SubsystemBase {

    private final DoubleSolenoid m_agitator;

    public Agitator() {
        m_agitator = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.AGITATOR_A, RobotMap.AGITATOR_B);

        addChild("agitator", m_agitator);
    }

    public void agitateForwards() {
        m_agitator.set(DoubleSolenoid.Value.kForward);
    }

    public void agitateBackwards() {
        m_agitator.set(DoubleSolenoid.Value.kReverse);
    }


}
