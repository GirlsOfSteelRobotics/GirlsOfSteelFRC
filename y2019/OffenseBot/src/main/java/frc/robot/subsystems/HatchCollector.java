package frc.robot.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class HatchCollector extends Subsystem {
    private DoubleSolenoid piston;

    private HatchState state;
    
    public static enum HatchState{
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

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}