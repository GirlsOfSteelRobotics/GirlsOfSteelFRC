package com.gos.power_up.subsystems;

import com.gos.power_up.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shifters extends Subsystem {
    private final DoubleSolenoid shifterLeft;
    private final DoubleSolenoid shifterRight;
    private static final double SHIFTING_THRESHOLD = 0;
    //TODO find correct shifting value

    public enum Speed {
        kHigh, kLow
    }

    private Speed speed;

    public Shifters() {
        shifterLeft = new DoubleSolenoid(RobotMap.SHIFTER_LEFT_A, RobotMap.SHIFTER_LEFT_B);
        shifterRight = new DoubleSolenoid(RobotMap.SHIFTER_RIGHT_A, RobotMap.SHIFTER_RIGHT_B);

        addChild("shifterLeft", shifterLeft);
        addChild("shifterRight", shifterRight);

    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void shiftGear(Speed speed) {
        this.speed = speed;
        if (speed == Speed.kLow) {
            shifterLeft.set(DoubleSolenoid.Value.kForward);
            shifterRight.set(DoubleSolenoid.Value.kForward);
            System.out.println("Shifting left and right side into low gear (fwd)");
        } else {
            shifterLeft.set(DoubleSolenoid.Value.kReverse);
            shifterRight.set(DoubleSolenoid.Value.kReverse);
            System.out.println("Shifting left and right side into high gear (rev)");
        }
    }

    public Speed getGearSpeed() {
        return speed;
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public double getShiftingThreshold() {
        return SHIFTING_THRESHOLD;
    }
}
