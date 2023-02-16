package com.gos.chargedup.commands;

import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class TeleopDockingArcadeDriveCommand extends CommandBase {
    private static final GosDoubleProperty ARCADE_DRIVE_BRAKE_SPEED = new GosDoubleProperty(false, "Chassis speed for brake", 0.2);
    private final ChassisSubsystem m_chassisSubsystem;
    private final CommandXboxController m_joystick;

    public TeleopDockingArcadeDriveCommand(ChassisSubsystem chassisSubsystem, CommandXboxController joystick) {
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
        m_chassisSubsystem.setArcadeDrive(ARCADE_DRIVE_BRAKE_SPEED.getValue() * -m_joystick.getLeftY(), ARCADE_DRIVE_BRAKE_SPEED.getValue() * -m_joystick.getRightX());
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
