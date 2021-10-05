package com.gos.steam_works.subsystems;

import com.gos.steam_works.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Agitator extends Subsystem {

    private DoubleSolenoid agitator;

    public Agitator() {
        agitator = new DoubleSolenoid(RobotMap.AGITATOR_A, RobotMap.AGITATOR_B);

        addChild("agitator", agitator);
    }

    public void agitateForwards() {
        agitator.set(DoubleSolenoid.Value.kForward);
    }

    public void agitateBackwards() {
        agitator.set(DoubleSolenoid.Value.kReverse);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
}
