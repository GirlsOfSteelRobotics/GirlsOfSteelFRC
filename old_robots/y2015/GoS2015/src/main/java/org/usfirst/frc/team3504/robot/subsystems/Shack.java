package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.RobotMap;

public class Shack extends Subsystem {

    private final DoubleSolenoid m_shack;

    public Shack() {
        m_shack = new DoubleSolenoid(RobotMap.LEFT_SHACK_MODULE, RobotMap.LEFT_SHACK_CHANNEL_A, RobotMap.LEFT_SHACK_CHANNEL_B);
    }

    public void shackIn() {
        m_shack.set(DoubleSolenoid.Value.kForward);
    }

    public void shackOut() {
        m_shack.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    protected void initDefaultCommand() {
    }

}
