package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.RobotMap;

/**
 *
 */
public class Shifters extends Subsystem {
    private final DoubleSolenoid m_shifterLeft;
    private final DoubleSolenoid m_shifterRight;

    public enum Speed {
        kHigh, kLow
    }

    private Speed m_speed;

    public Shifters() {
        m_shifterLeft = new DoubleSolenoid(RobotMap.SHIFTER_LEFT_A, RobotMap.SHIFTER_LEFT_B);
        m_shifterRight = new DoubleSolenoid(RobotMap.SHIFTER_RIGHT_A, RobotMap.SHIFTER_RIGHT_B);

        addChild("shifterLeft", m_shifterLeft);
        addChild("shifterRight", m_shifterRight);

    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void shiftGear(Speed speed) {
        this.m_speed = speed;
        if (speed == Speed.kHigh) {
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

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
}
