package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.RobotMap;

/**
 *
 */
public class JawPiston extends Subsystem {


    private final DoubleSolenoid m_jawPiston = new DoubleSolenoid(RobotMap.PCM_ARM, RobotMap.JAW_PISTON_A, RobotMap.JAW_PISTON_B);

    public void pistonsOut(){
        m_jawPiston.set(DoubleSolenoid.Value.kForward);
        System.out.println("Jaw Piston out");
    }

    public void pistonsIn(){
        m_jawPiston.set(DoubleSolenoid.Value.kReverse);
        System.out.println("Jaw Piston in");

    }

    @Override
    public void initDefaultCommand() {
    }
}
