package com.gos.chargedup.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import java.util.function.BooleanSupplier;


public class CheckRumbleCommand extends CommandBase {
    private final CommandXboxController m_joystick;
    private final BooleanSupplier m_checkValue;

    public CheckRumbleCommand(CommandXboxController joystick, BooleanSupplier checkValue) {
        m_joystick = joystick;
        m_checkValue = checkValue;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (m_checkValue.getAsBoolean()) {
            m_joystick.getHID().setRumble(GenericHID.RumbleType.kBothRumble, 0.5);
        }
        else {
            m_joystick.getHID().setRumble(GenericHID.RumbleType.kBothRumble, 0);
        }
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
