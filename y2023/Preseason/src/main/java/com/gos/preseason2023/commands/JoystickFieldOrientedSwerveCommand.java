package com.gos.preseason2023.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.preseason2023.subsystems.ChassisSubsystem;


public class JoystickFieldOrientedSwerveCommand extends CommandBase {
    private final ChassisSubsystem m_chassisSubsystem;

    private final XboxController m_joystick;

    public JoystickFieldOrientedSwerveCommand(ChassisSubsystem chassisSubsystem, XboxController joystick) {
        this.m_chassisSubsystem = chassisSubsystem;
        m_joystick = joystick;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_chassisSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

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
