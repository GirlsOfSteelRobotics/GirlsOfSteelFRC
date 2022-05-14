package com.gos.outreach2016.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.outreach2016.robot.RobotMap;

/**
 *
 */
public class AccessoryMotors extends SubsystemBase {
    private final Victor m_accessoryLeft;
    private final Victor m_accessoryRight;

    public enum Direction { FWD, REV }

    public AccessoryMotors() {
        m_accessoryLeft = new Victor(RobotMap.ACCESSORY_LEFT_PORT);
        addChild("Accessory Left", m_accessoryLeft);

        m_accessoryRight = new Victor(RobotMap.ACCESSORY_RIGHT_PORT);
        addChild("Accessory Right", m_accessoryRight);
    }



    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void startLeft(Direction direction) {
        if (direction == Direction.FWD) {
            m_accessoryLeft.set(1.0);
        } else {
            m_accessoryLeft.set(-1.0);
        }
    }

    public void startRight(Direction direction) {
        if (direction == Direction.FWD) {
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
