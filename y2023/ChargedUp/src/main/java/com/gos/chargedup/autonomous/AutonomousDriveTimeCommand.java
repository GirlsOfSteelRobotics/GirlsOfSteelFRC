package com.gos.chargedup.autonomous;

import com.gos.chargedup.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class AutonomousDriveTimeCommand extends CommandBase {

    private static final double DRIVE_TIME = 1.0;

    private static final double AUTONOMOUS_DRIVE_SPEED = 0.5;

    private final ChassisSubsystem m_chassis;

    private final Timer m_timer;

    public AutonomousDriveTimeCommand(ChassisSubsystem chassisSubsystem) {
        m_timer = new Timer();
        m_chassis = chassisSubsystem;

        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_chassis);
    }

    @Override
    public void initialize() {
        m_timer.start();

    }

    @Override
    public void execute() {
        m_chassis.setArcadeDrive(AUTONOMOUS_DRIVE_SPEED, 0.0);
    }

    @Override
    public boolean isFinished() {
        return m_timer.hasElapsed(DRIVE_TIME);

    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.setArcadeDrive(0.0, 0.0);
    }
}
