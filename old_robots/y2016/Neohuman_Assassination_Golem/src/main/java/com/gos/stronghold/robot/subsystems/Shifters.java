package com.gos.stronghold.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.stronghold.robot.RobotMap;

/**
 *
 */
public class Shifters extends SubsystemBase {
    private final DoubleSolenoid m_shifterLeft;
    private final DoubleSolenoid m_shifterRight;

    public enum Speed { HIGH, LOW }

    private boolean m_inHighGear;

    public Shifters() {
        m_shifterLeft = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.SHIFTER_LEFT_A, RobotMap.SHIFTER_LEFT_B);
        m_shifterRight = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.SHIFTER_RIGHT_A, RobotMap.SHIFTER_RIGHT_B);

    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void shiftLeft(Speed speed) {
        if (speed == Speed.HIGH) {
            m_shifterLeft.set(DoubleSolenoid.Value.kReverse);
            System.out.println("Shifting left side into high gear (fwd)");
            m_inHighGear = true;
        } else {
            m_shifterLeft.set(DoubleSolenoid.Value.kForward);
            System.out.println("Shifting left side into low gear (rev)");
            m_inHighGear = false;
        }
    }

    public void shiftRight(Speed speed) {
        if (speed == Speed.HIGH) {
            m_shifterRight.set(DoubleSolenoid.Value.kReverse);
            System.out.println("Shifting right side into high gear (fwd)");
            m_inHighGear = true;
        } else {
            m_shifterRight.set(DoubleSolenoid.Value.kForward);
            System.out.println("Shifting right side into low gear (rev)");
            m_inHighGear = false;
        }

    }

    public boolean getRightShifterValue() {
        return m_shifterRight.get() != DoubleSolenoid.Value.kForward;
    }

    public boolean getLeftShifterValue() {
        return m_shifterLeft.get() != DoubleSolenoid.Value.kForward;
    }

    public boolean getGearSpeed() {
        return m_inHighGear;
    }


}
