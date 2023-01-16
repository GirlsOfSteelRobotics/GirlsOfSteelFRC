package com.gos.chargedup.commands;

import com.gos.chargedup.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class CurvatureDriveCommand extends CommandBase {
    private final ChassisSubsystem m_chassis;

    private final CommandXboxController m_joystick;
    private final double m_steerSpeed = 0.75; // NOPMD

    public CurvatureDriveCommand(ChassisSubsystem chassisSubsystem, CommandXboxController joystick) {
        m_chassis = chassisSubsystem;
        m_joystick = joystick;
        addRequirements(this.m_chassis);

    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute() {
        m_chassis.setCurvatureDrive(-m_joystick.getLeftY(), m_steerSpeed * -m_joystick.getRightX());
    }

    @Override
    public boolean isFinished() {
        return false;

    }

}
