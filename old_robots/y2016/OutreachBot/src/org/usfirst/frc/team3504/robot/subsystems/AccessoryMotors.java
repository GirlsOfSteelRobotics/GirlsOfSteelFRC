package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class AccessoryMotors extends Subsystem {
    private final Victor accessoryLeft;
    private final Victor accessoryRight;

    public enum Direction {kFwd, kRev}

    public AccessoryMotors() {
        accessoryLeft = new Victor(RobotMap.ACCESSORY_LEFT_PORT);
        addChild("Accessory Left", accessoryLeft);

        accessoryRight = new Victor(RobotMap.ACCESSORY_RIGHT_PORT);
        addChild("Accessory Right", accessoryRight);
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
        if (direction == Direction.kFwd)
            accessoryLeft.set(1.0);
        else
            accessoryLeft.set(-1.0);
    }

    public void startRight(Direction direction) {
        if (direction == Direction.kFwd)
            accessoryRight.set(1.0);
        else
            accessoryRight.set(-1.0);
    }

    public void stopLeft() {
        accessoryLeft.set(0.0);
    }

    public void stopRight() {
        accessoryRight.set(0.0);
    }
}
