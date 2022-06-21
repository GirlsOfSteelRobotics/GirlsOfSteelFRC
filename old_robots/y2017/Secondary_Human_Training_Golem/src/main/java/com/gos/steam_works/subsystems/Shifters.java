package com.gos.steam_works.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.steam_works.RobotMap;

/**
 *
 */
public class Shifters extends SubsystemBase {
    private final DoubleSolenoid m_shifterLeft;
    private final DoubleSolenoid m_shifterRight;

    public enum Speed {
        HIGH, LOW
    }

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
        if (speed == Speed.HIGH) {
            m_shifterLeft.set(DoubleSolenoid.Value.kReverse);
            m_shifterRight.set(DoubleSolenoid.Value.kReverse);
            System.out.println("Shifting left and right side into high gear (rev)");
        } else {
            m_shifterLeft.set(DoubleSolenoid.Value.kForward);
            m_shifterRight.set(DoubleSolenoid.Value.kForward);
            System.out.println("Shifting left and right side into low gear (fwd)");
        }
    }

    public Speed getGearSpeed() {
        return m_speed;
    }


}
