package com.gos.infinite_recharge.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.infinite_recharge.subsystems.ControlPanel;

public class SpinControlPanel extends CommandBase {
    private final ControlPanel m_controlPanel;

    public SpinControlPanel(ControlPanel controlPanel) {
        this.m_controlPanel = controlPanel;
        super.addRequirements(controlPanel);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_controlPanel.start();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_controlPanel.stop();
    }

}
