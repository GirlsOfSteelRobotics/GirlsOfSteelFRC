package com.gos.offense2019.subsystems;

import com.gos.offense2019.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class HatchCollector extends Subsystem {
    private final DoubleSolenoid piston;

    private HatchState state;

    public enum HatchState {
        kGrab, kRelease
    }

    public HatchCollector() {
        piston = new DoubleSolenoid(RobotMap.PISTON_A, RobotMap.PISTON_B);
        state = HatchState.kRelease;

        addChild("piston", piston);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void driveHatch(HatchState speed) {
        this.state = speed;
        if (speed == HatchState.kRelease) {
            piston.set(DoubleSolenoid.Value.kForward);
        } else {
            piston.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public HatchState getHatchState() {
        return state;
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
}
