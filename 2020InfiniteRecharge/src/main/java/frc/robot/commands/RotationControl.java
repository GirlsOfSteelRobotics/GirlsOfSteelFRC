package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ControlPanel;

public class RotationControl extends CommandBase {
    private final ControlPanel m_controlPanel;
    private int m_colorCount;

    public RotationControl(ControlPanel controlPanel) {
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
        if(m_colorCount == 24){
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_controlPanel.stop(); 
    }

}