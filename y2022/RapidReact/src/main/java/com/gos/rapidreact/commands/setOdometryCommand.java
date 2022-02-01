package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ChassisSubsystem;


public class setOdometryCommand extends CommandBase {
    private final ChassisSubsystem chassisSubsystem;

    public setOdometryCommand(ChassisSubsystem chassisSubsystem) {
        this.chassisSubsystem = chassisSubsystem;
        addRequirements(this.chassisSubsystem);
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
