package com.gos.offense2019.subsystems;

import com.gos.offense2019.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shifters extends Subsystem {
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
        } else {
            m_shifterLeft.set(DoubleSolenoid.Value.kReverse);
            m_shifterRight.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public Speed getGearSpeed() {
        return m_speed;
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
}
