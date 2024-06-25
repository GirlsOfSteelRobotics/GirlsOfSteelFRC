package com.gos.chargedup.commands;

import com.gos.chargedup.subsystems.TankDriveChassisSubsystem;
import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class TeleopMediumArcadeDriveCommand extends Command {
    private static final GosDoubleProperty ARCADE_DRIVE_MEDIUM_SPEED = new GosDoubleProperty(true, "Chassis speed medium", 0.5);
    private final TankDriveChassisSubsystem m_chassisSubsystem;
    private final CommandXboxController m_joystick;

    public TeleopMediumArcadeDriveCommand(TankDriveChassisSubsystem chassisSubsystem, CommandXboxController joystick) {
        m_joystick = joystick;
        m_chassisSubsystem = chassisSubsystem;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_chassisSubsystem);

    }

    @Override
    public void initialize() {
        m_chassisSubsystem.drivetrainToBrakeMode();
    }

    @Override
    public void execute() {
        m_chassisSubsystem.setArcadeDrive(ARCADE_DRIVE_MEDIUM_SPEED.getValue() * -m_joystick.getLeftY(), ARCADE_DRIVE_MEDIUM_SPEED.getValue() * -m_joystick.getRightX());
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        this.m_chassisSubsystem.drivetrainToCoastMode();
    }
}
