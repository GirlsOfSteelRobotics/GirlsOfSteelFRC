package com.gos.rapidreact.commands;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class TeleopArcadeChassisCommand extends CommandBase {
    private final ChassisSubsystem m_chassis;
    private final XboxController m_joystick;

    public TeleopArcadeChassisCommand(ChassisSubsystem chassis, XboxController joystick) {
        m_chassis = chassis;
        m_joystick = joystick;


        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(m_chassis);

    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_chassis.setArcadeDrive(m_joystick.getLeftY(), m_joystick.getRightX());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
