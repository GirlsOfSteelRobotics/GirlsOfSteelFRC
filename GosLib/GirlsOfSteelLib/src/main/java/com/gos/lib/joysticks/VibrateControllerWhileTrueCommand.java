package com.gos.lib.joysticks;

import org.wpilib.driverstation.GenericHID;
import org.wpilib.command2.Command;
import org.wpilib.command2.button.CommandNiDsXboxController;

import java.util.function.BooleanSupplier;


public class VibrateControllerWhileTrueCommand extends Command {
    private final CommandNiDsXboxController m_driverController;
    private final BooleanSupplier m_isReady;

    public VibrateControllerWhileTrueCommand(CommandNiDsXboxController controller, BooleanSupplier isReadySupplier) {
        m_driverController = controller;
        m_isReady = isReadySupplier;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (m_isReady.getAsBoolean()) {
            m_driverController.getHID().setRumble(GenericHID.RumbleType.LEFT_RUMBLE, 1);
            m_driverController.getHID().setRumble(GenericHID.RumbleType.RIGHT_RUMBLE, 1);
        } else {
            m_driverController.getHID().setRumble(GenericHID.RumbleType.LEFT_RUMBLE, 0);
            m_driverController.getHID().setRumble(GenericHID.RumbleType.RIGHT_RUMBLE, 0);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_driverController.getHID().setRumble(GenericHID.RumbleType.LEFT_RUMBLE, 0);
        m_driverController.getHID().setRumble(GenericHID.RumbleType.RIGHT_RUMBLE, 0);
    }
}
