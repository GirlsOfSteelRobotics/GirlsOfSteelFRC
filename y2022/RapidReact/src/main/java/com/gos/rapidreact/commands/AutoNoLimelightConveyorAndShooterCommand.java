package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;


public class AutoNoLimelightConveyorAndShooterCommand extends CommandBase {
    private final ShooterSubsystem m_shooter;
    private final VerticalConveyorSubsystem m_verticalConveyor;

    public AutoNoLimelightConveyorAndShooterCommand(ShooterSubsystem shooterSubsystem, VerticalConveyorSubsystem verticalConveyorSubsystem) {
        this.m_shooter = shooterSubsystem;
        this.m_verticalConveyor = verticalConveyorSubsystem;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_shooter, this.m_verticalConveyor);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_shooter.setShooterRpmPIDSpeed(ShooterSubsystem.TARMAC_EDGE_RPM_HIGH);

        m_verticalConveyor.forwardFeedMotor();

        // We are ready to shoot, move the conveyor up
        if (m_shooter.isShooterAtSpeed()) {
            m_verticalConveyor.forwardVerticalConveyorMotor();
        }
        // We aren't at speed, but we will move the balls up until we trip the top ball sensor
        // else if (!m_verticalConveyor.getUpperIndexSensor()) {
        //     m_verticalConveyor.forwardVerticalConveyorMotor();
        // }
        // We aren't at speed, but we detect a ball. Wait till we can shoot.
        else {
            m_verticalConveyor.stopVerticalConveyorMotor();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_verticalConveyor.stopVerticalConveyorMotor();
    }
}
