package com.gos.chargedup.commands;

import com.gos.chargedup.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TeleopArcadeDriveCommand extends CommandBase {

    private final ChassisSubsystem m_chassis;

    private final XboxController m_joystick;

    public TeleopArcadeDriveCommand(ChassisSubsystem chassisSubsystem, XboxController joystick) {
        m_chassis = chassisSubsystem;
        m_joystick = joystick;
        addRequirements(this.m_chassis);

    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_chassis.setArcadeDrive(-m_joystick.getLeftY(), .75 * -m_joystick.getRightX());
    }

    @Override
    public boolean isFinished() {
        return false;

    }

}




