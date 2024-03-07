package com.gos.crescendo2024.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class VibrateControllerTimedCommand extends Command {
    private final CommandXboxController m_driverController;
    private final Timer m_intakeTimer;
    private final double m_elapsedTime;
    private final boolean m_isReady;

    public VibrateControllerTimedCommand(CommandXboxController controller, double elapsedTime, boolean isReady) {
        m_driverController = controller;
        this.m_intakeTimer = new Timer();
        m_elapsedTime = elapsedTime;
        m_isReady = isReady;
    }

    @Override
    public void initialize() {
        m_intakeTimer.restart();
    }

    @Override
    public void execute() {
        if (m_isReady) {
            m_driverController.getHID().setRumble(GenericHID.RumbleType.kBothRumble, 1);
        }
    }

    @Override
    public boolean isFinished() {
        return m_intakeTimer.hasElapsed(m_elapsedTime);
    }

    @Override
    public void end(boolean interrupted) {
        m_driverController.getHID().setRumble(GenericHID.RumbleType.kBothRumble, 0);

    }
}
