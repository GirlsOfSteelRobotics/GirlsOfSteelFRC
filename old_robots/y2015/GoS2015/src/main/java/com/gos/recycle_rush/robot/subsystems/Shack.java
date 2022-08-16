package com.gos.recycle_rush.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.recycle_rush.robot.RobotMap;

public class Shack extends SubsystemBase {

    private final DoubleSolenoid m_shack;

    public Shack() {
        m_shack = new DoubleSolenoid(RobotMap.LEFT_SHACK_MODULE, PneumaticsModuleType.CTREPCM, RobotMap.LEFT_SHACK_CHANNEL_A, RobotMap.LEFT_SHACK_CHANNEL_B);
    }

    public void shackIn() {
        m_shack.set(DoubleSolenoid.Value.kForward);
    }

    public void shackOut() {
        m_shack.set(DoubleSolenoid.Value.kReverse);
    }



}
