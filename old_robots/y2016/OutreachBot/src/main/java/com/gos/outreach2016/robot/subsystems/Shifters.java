package com.gos.outreach2016.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.outreach2016.robot.RobotMap;

/**
 *
 */
public class Shifters extends SubsystemBase {
    private final DoubleSolenoid m_shifterLeft;
    private final DoubleSolenoid m_shifterRight;

    public enum Speed { kHigh, kLow }

    public Shifters() {
        m_shifterLeft = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.SHIFTER_LEFT_A, RobotMap.SHIFTER_LEFT_B);
        addChild("Shifter Left", m_shifterLeft);

        m_shifterRight = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.SHIFTER_RIGHT_A, RobotMap.SHIFTER_RIGHT_B);
        addChild("Shifter Right", m_shifterRight);
    }



    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void shiftLeft(Speed speed) {
        if (speed == Speed.kHigh) {
            m_shifterLeft.set(DoubleSolenoid.Value.kForward);
            System.out.println("Shifting left side into high gear (fwd)");
        } else {
            m_shifterLeft.set(DoubleSolenoid.Value.kReverse);
            System.out.println("Shifting left side into low gear (rev)");
        }
    }

    public void shiftRight(Speed speed) {
        if (speed == Speed.kHigh) {
            m_shifterRight.set(DoubleSolenoid.Value.kForward);
            System.out.println("Shifting right side into high gear (fwd)");
        } else {
            m_shifterRight.set(DoubleSolenoid.Value.kReverse);
            System.out.println("Shifting right side into low gear (rev)");
        }
    }
}
