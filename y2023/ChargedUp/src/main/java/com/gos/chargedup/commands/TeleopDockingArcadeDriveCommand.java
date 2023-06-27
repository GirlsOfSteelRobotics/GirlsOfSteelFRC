package com.gos.chargedup.commands;

import com.gos.chargedup.subsystems.LEDManagerSubsystem;
import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.chargedup.subsystems.TankDriveChassisSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class TeleopDockingArcadeDriveCommand extends CommandBase {
    private static final GosDoubleProperty ARCADE_DRIVE_BRAKE_SPEED = new GosDoubleProperty(true, "Chassis speed for brake", 0.6);
    private final TankDriveChassisSubsystem m_chassisSubsystem;
    private final CommandXboxController m_joystick;
    private final LEDManagerSubsystem m_led;

    public TeleopDockingArcadeDriveCommand(TankDriveChassisSubsystem chassisSubsystem, CommandXboxController joystick, LEDManagerSubsystem led) {
        m_joystick = joystick;
        m_chassisSubsystem = chassisSubsystem;
        m_led = led;
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
        m_led.setDockOption();
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        this.m_chassisSubsystem.drivetrainToCoastMode();
        m_led.stopDockAndEngagePatterns();
    }
}
