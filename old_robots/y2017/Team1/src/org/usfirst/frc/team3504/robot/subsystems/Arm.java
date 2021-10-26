package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 *
 */
public class Arm extends Subsystem {

    private DoubleSolenoid armPiston;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public Arm()
    {
        // armPiston = new DoubleSolenoid(RobotMap.ARM_PISTON_A, RobotMap.ARM_PISTON_B);
    }

    public void armUp()
    {
        armPiston.set(DoubleSolenoid.Value.kForward);
    }

    public void armDown()
    {
        armPiston.set(DoubleSolenoid.Value.kReverse);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
