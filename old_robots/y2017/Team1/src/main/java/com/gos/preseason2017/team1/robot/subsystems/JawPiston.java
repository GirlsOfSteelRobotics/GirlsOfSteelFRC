package com.gos.preseason2017.team1.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.preseason2017.team1.robot.RobotMap;

/**
 *
 */
public class JawPiston extends SubsystemBase {


    private final DoubleSolenoid m_jawPiston = new DoubleSolenoid(RobotMap.PCM_ARM, PneumaticsModuleType.CTREPCM, RobotMap.JAW_PISTON_A, RobotMap.JAW_PISTON_B);

    public void pistonsOut() {
        m_jawPiston.set(DoubleSolenoid.Value.kForward);
        System.out.println("Jaw Piston out");
    }

    public void pistonsIn() {
        m_jawPiston.set(DoubleSolenoid.Value.kReverse);
        System.out.println("Jaw Piston in");

    }


}
