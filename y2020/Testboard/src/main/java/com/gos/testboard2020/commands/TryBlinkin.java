package com.gos.testboard2020.commands;

import com.gos.testboard2020.subsystems.Blinkin;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TryBlinkin extends CommandBase {

    private final Blinkin m_blinkin;

    public TryBlinkin(Blinkin blinkin) {
        m_blinkin = blinkin;
        // Use super.addRequirements here to declare subsystem dependencies
        super.addRequirements(m_blinkin);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        System.out.println("init TryBlinking(HATCH_RELEASE) all green");
        m_blinkin.setLightPattern(Blinkin.LightPattern.HATCH_RELEASE);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_blinkin.stop();
    }
}
