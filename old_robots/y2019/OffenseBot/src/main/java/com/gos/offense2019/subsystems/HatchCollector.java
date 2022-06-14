package com.gos.offense2019.subsystems;

import com.gos.offense2019.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 *
 */
public class HatchCollector extends SubsystemBase {

    public enum HatchState {
        GRAB, RELEASE
    }

    private final DoubleSolenoid m_piston;

    private HatchState m_state;

    public HatchCollector() {
        m_piston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.PISTON_A, RobotMap.PISTON_B);
        m_state = HatchState.RELEASE;

        addChild("piston", m_piston);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void driveHatch(HatchState speed) {
        this.m_state = speed;
        if (speed == HatchState.RELEASE) {
            m_piston.set(DoubleSolenoid.Value.kForward);
        } else {
            m_piston.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public HatchState getHatchState() {
        return m_state;
    }


}
