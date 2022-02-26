package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ShooterSubsystem;



public class ShootFromTableCommand extends CommandBase {
    private final ShooterSubsystem m_shooter;

    public ShootFromTableCommand(ShooterSubsystem shooterSubsystem) {
        this.m_shooter = shooterSubsystem;
        addRequirements(this.m_shooter);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

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
