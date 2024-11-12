package com.gos.rapidreact.commands;

import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;


public class ShooterPIDWithFeederCommand extends Command {
    private final ShooterSubsystem m_shooter;
    private final VerticalConveyorSubsystem m_verticalConveyor;
    private final double m_goalRPM;

    public ShooterPIDWithFeederCommand(ShooterSubsystem shooterSubsystem, VerticalConveyorSubsystem verticalConveyorSubsystem, double goalRPM) {
        this.m_shooter = shooterSubsystem;
        this.m_verticalConveyor = verticalConveyorSubsystem;
        m_goalRPM = goalRPM;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_shooter, this.m_verticalConveyor);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_shooter.setShooterRpmPIDSpeed(m_goalRPM);
        m_verticalConveyor.forwardFeedMotor();
    }

    @Override
    public boolean isFinished() {
        return m_shooter.isShooterAtSpeed();
    }

    @Override
    public void end(boolean interrupted) {

    }
}
