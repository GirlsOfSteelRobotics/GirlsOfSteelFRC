package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;


public class TeleopSwerveDrive extends Command {
    private final ChassisSubsystem m_subsystem;

    public TeleopSwerveDrive(ChassisSubsystem subsystem) {
        this.m_subsystem = subsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        System.out.println("Implement me!" + m_subsystem);
    }
}
