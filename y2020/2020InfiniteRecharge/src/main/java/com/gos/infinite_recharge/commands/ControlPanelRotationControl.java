package com.gos.infinite_recharge.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.infinite_recharge.subsystems.ControlPanel;

public class ControlPanelRotationControl extends CommandBase {
    private final ControlPanel m_controlPanel;
    private int m_colorCount;

    public ControlPanelRotationControl(ControlPanel controlPanel) {
        this.m_controlPanel = controlPanel;
        super.addRequirements(controlPanel);
    }

    @Override
    public void initialize() {
        m_colorCount = 0;
    }

    @Override
    public void execute() {
        m_controlPanel.start();
        m_colorCount = m_controlPanel.getColorCounter();
        System.out.println("number of colors: " + m_colorCount);
    }

    @Override
    public boolean isFinished() {
        return m_colorCount == 24;
    }

    @Override
    public void end(boolean interrupted) {
        m_controlPanel.stop();
    }

}
