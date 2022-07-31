package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ChassisSubsystem;


public class TeleopCurvatureDriveCommand extends CommandBase {
    private final ChassisSubsystem m_chassis;
    private final XboxController m_joystick;

    public TeleopCurvatureDriveCommand(ChassisSubsystem chassisSubsystem, XboxController joystick) {
        m_chassis = chassisSubsystem;
        m_joystick = joystick;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_chassis);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_chassis.setCurvatureDrive(-m_joystick.getLeftY(), m_joystick.getRightX());
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
