package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.RobotMap;
/**
 *
 */
public class Shooter extends Subsystem {

    private final DoubleSolenoid m_shooterPistonLeft;
    private final DoubleSolenoid m_shooterPistonRight;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public Shooter()
    {
        m_shooterPistonLeft = new DoubleSolenoid(RobotMap.PCM_ARM, RobotMap.SHOOTER_PISTON_LEFT_A, RobotMap.SHOOTER_PISTON_LEFT_B);
        m_shooterPistonRight = new DoubleSolenoid(RobotMap.PCM_ARM, RobotMap.SHOOTER_PISTON_RIGHT_A, RobotMap.SHOOTER_PISTON_RIGHT_B);

    }
    public void pistonOut()
    {
        m_shooterPistonLeft.set(DoubleSolenoid.Value.kForward);
        m_shooterPistonRight.set(DoubleSolenoid.Value.kForward);
    }

    public void pistonIn()
    {
        m_shooterPistonLeft.set(DoubleSolenoid.Value.kReverse);
        m_shooterPistonRight.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    public void initDefaultCommand() {
    }
}
