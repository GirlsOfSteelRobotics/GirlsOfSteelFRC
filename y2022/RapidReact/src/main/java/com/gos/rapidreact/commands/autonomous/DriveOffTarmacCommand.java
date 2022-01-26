package com.gos.rapidreact.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ChassisSubsystem;


public class DriveOffTarmacCommand extends CommandBase {

    private final ChassisSubsystem m_chassis;


    public DriveOffTarmacCommand(ChassisSubsystem chassis) {
        m_chassis = chassis;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(chassis);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_chassis.setArcadeDrive(10, 10);

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
