package com.gos.lib.joysticks;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class VibrateControllerTimedCommand extends Command {
    private final CommandXboxController m_driverController;
    private final Timer m_intakeTimer;
    private final double m_elapsedTime;

    public VibrateControllerTimedCommand(CommandXboxController controller, double elapsedTime) {
        m_driverController = controller;
        this.m_intakeTimer = new Timer();
        m_elapsedTime = elapsedTime;
    }

    @Override
    public void initialize() {
        m_intakeTimer.restart();
    }

    @Override
    public void execute() {
        m_driverController.getHID().setRumble(GenericHID.RumbleType.kBothRumble, 1);
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
