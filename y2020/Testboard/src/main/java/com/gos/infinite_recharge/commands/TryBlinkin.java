package com.gos.infinite_recharge.commands;

import com.gos.infinite_recharge.subsystems.Blinkin;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TryBlinkin extends CommandBase {

	private Blinkin m_blinkin;

	public TryBlinkin(Blinkin blinkin) {
		m_blinkin = blinkin;
		// Use super.addRequirements here to declare subsystem dependencies
		super.addRequirements(m_blinkin);
	}

	// Called just before this Command runs the first time
	public void initialize() {
		System.out.println("init TryBlinking(HATCH_RELEASE) all green");
		m_blinkin.setLightPattern(Blinkin.LightPattern.HATCH_RELEASE);
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {

	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	public void end(boolean interrupted) {
		m_blinkin.stop();
	}
}
