package com.gos.codelabs.gitflow.commands;

import com.gos.codelabs.gitflow.subsystems.ChassisSubsystem;
import org.wpilib.driverstation.NiDsXboxController;
import org.wpilib.command2.Command;


public class TeleopDriveCommand extends Command {
    private final NiDsXboxController m_joystick;
    private final ChassisSubsystem m_chassisSubsystem;

    public TeleopDriveCommand(ChassisSubsystem chassisSubsystem) {
        m_chassisSubsystem = chassisSubsystem;
        m_joystick = new NiDsXboxController(0);

        addRequirements(this.m_chassisSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_chassisSubsystem.setSpeedAndSteer(m_joystick.getLeftY(), m_joystick.getRightX());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
