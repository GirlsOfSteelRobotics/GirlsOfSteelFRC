package com.gos.power_up.subsystems;

import com.gos.power_up.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 *
 */
public class Shifters extends SubsystemBase {
    //TODO find correct shifting value
    private static final double SHIFTING_THRESHOLD = 0;

    public enum Speed {
        kHigh, kLow
    }

    private final DoubleSolenoid m_shifterLeft;
    private final DoubleSolenoid m_shifterRight;


    private Speed m_speed;

    public Shifters() {
        m_shifterLeft = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.SHIFTER_LEFT_A, RobotMap.SHIFTER_LEFT_B);
        m_shifterRight = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.SHIFTER_RIGHT_A, RobotMap.SHIFTER_RIGHT_B);

        addChild("shifterLeft", m_shifterLeft);
        addChild("shifterRight", m_shifterRight);

    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void shiftGear(Speed speed) {
        this.m_speed = speed;
        if (speed == Speed.kLow) {
            m_shifterLeft.set(DoubleSolenoid.Value.kForward);
            m_shifterRight.set(DoubleSolenoid.Value.kForward);
            System.out.println("Shifting left and right side into low gear (fwd)");
        } else {
            m_shifterLeft.set(DoubleSolenoid.Value.kReverse);
            m_shifterRight.set(DoubleSolenoid.Value.kReverse);
            System.out.println("Shifting left and right side into high gear (rev)");
        }
    }

    public Speed getGearSpeed() {
        return m_speed;
    }



    public double getShiftingThreshold() {
        return SHIFTING_THRESHOLD;
    }
}
