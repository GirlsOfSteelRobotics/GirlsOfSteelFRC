package com.gos.scra.wcd.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.scra.wcd.subsystems.ChassisSubsystem;


public class ChassisArcadeDriveCommand extends CommandBase {
    private final ChassisSubsystem m_chassis;
    private final XboxController m_joystick;

    public ChassisArcadeDriveCommand(ChassisSubsystem chassisSubsystem, XboxController joystick) {
        m_chassis = chassisSubsystem;
        m_joystick = joystick;
        addRequirements(this.m_chassis);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_chassis.setArcadeDrive(-m_joystick.getLeftY(), -m_joystick.getRightX());
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
