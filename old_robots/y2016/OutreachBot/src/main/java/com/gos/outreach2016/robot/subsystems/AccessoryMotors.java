package com.gos.outreach2016.robot.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.gos.outreach2016.robot.RobotMap;

/**
 *
 */
public class AccessoryMotors extends Subsystem {
    private final Victor m_accessoryLeft;
    private final Victor m_accessoryRight;

    public enum Direction {kFwd, kRev}

    public AccessoryMotors() {
        m_accessoryLeft = new Victor(RobotMap.ACCESSORY_LEFT_PORT);
        addChild("Accessory Left", m_accessoryLeft);

        m_accessoryRight = new Victor(RobotMap.ACCESSORY_RIGHT_PORT);
        addChild("Accessory Right", m_accessoryRight);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        //There's no default command for this subsystem
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void startLeft(Direction direction) {
        if (direction == Direction.kFwd) {
            m_accessoryLeft.set(1.0);
        } else {
            m_accessoryLeft.set(-1.0);
        }
    }

    public void startRight(Direction direction) {
        if (direction == Direction.kFwd) {
            m_accessoryRight.set(1.0);
        } else {
            m_accessoryRight.set(-1.0);
        }
    }

    public void stopLeft() {
        m_accessoryLeft.set(0.0);
    }

    public void stopRight() {
        m_accessoryRight.set(0.0);
    }
}
