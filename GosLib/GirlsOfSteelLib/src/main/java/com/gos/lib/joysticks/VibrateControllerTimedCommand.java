package com.gos.lib.joysticks;

import org.wpilib.driverstation.GenericHID;
import org.wpilib.system.Timer;
import org.wpilib.command2.Command;
import org.wpilib.command2.button.CommandNiDsXboxController;


public class VibrateControllerTimedCommand extends Command {
    private final CommandNiDsXboxController m_driverController;
    private final Timer m_intakeTimer;
    private final double m_elapsedTime;

    public VibrateControllerTimedCommand(CommandNiDsXboxController controller, double elapsedTime) {
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
        m_driverController.getHID().setRumble(GenericHID.RumbleType.LEFT_RUMBLE, 1);
        m_driverController.getHID().setRumble(GenericHID.RumbleType.RIGHT_RUMBLE, 1);
    }

    @Override
    public boolean isFinished() {
        return m_intakeTimer.hasElapsed(m_elapsedTime);
    }

    @Override
    public void end(boolean interrupted) {
        m_driverController.getHID().setRumble(GenericHID.RumbleType.LEFT_RUMBLE, 0);
        m_driverController.getHID().setRumble(GenericHID.RumbleType.RIGHT_RUMBLE, 0);
    }
}
