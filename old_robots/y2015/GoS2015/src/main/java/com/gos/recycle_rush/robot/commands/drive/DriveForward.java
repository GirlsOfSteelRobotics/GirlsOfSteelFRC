package com.gos.recycle_rush.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.recycle_rush.robot.subsystems.Chassis;

public class DriveForward extends Command {
    private final Chassis m_chassis;

    public DriveForward(Chassis chassis) {
        m_chassis = chassis;
        addRequirements(m_chassis);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_chassis.driveForward();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.stop();
    }



}
