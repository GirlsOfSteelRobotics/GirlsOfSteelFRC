package com.gos.testboard2020.commands;

import com.gos.testboard2020.subsystems.Blinkin;
import edu.wpi.first.wpilibj2.command.Command;

public class TryBlinkin extends Command {

    private final Blinkin m_blinkin;

    public TryBlinkin(Blinkin blinkin) {
        m_blinkin = blinkin;
        // Use super.addRequirements here to declare subsystem dependencies
        super.addRequirements(m_blinkin);
    }


    @Override
    public void initialize() {
        System.out.println("init TryBlinking(HATCH_RELEASE) all green");
        m_blinkin.setLightPattern(Blinkin.LightPattern.HATCH_RELEASE);
    }


    @Override
    public void execute() {

    }


    @Override
    public boolean isFinished() {
        return true;
    }


    @Override
    public void end(boolean interrupted) {
        m_blinkin.stop();
    }
}
