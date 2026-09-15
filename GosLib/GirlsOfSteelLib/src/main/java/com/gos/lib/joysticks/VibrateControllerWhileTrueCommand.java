package com.gos.lib.joysticks;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import java.util.function.BooleanSupplier;


public class VibrateControllerWhileTrueCommand extends Command {
    private final CommandXboxController m_driverController;
    private final BooleanSupplier m_isReady;

    public VibrateControllerWhileTrueCommand(CommandXboxController controller, BooleanSupplier isReadySupplier) {
        m_driverController = controller;
        m_isReady = isReadySupplier;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (m_isReady.getAsBoolean()) {
            m_driverController.getHID().setRumble(GenericHID.RumbleType.kBothRumble, 1);
        } else {
            m_driverController.getHID().setRumble(GenericHID.RumbleType.kBothRumble, 0);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_driverController.getHID().setRumble(GenericHID.RumbleType.kBothRumble, 0);
    }
}
