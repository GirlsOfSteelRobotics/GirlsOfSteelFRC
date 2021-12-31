package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.RobotMap;

/**
 *
 */
public class Arm extends Subsystem {

    private final DoubleSolenoid m_armPiston;

    public Arm() {
        m_armPiston = new DoubleSolenoid(RobotMap.ARM_PISTON_A, RobotMap.ARM_PISTON_B);
    }

    public void armUp() {
        m_armPiston.set(DoubleSolenoid.Value.kForward);
    }

    public void armDown() {
        m_armPiston.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    public void initDefaultCommand() {
    }
}
