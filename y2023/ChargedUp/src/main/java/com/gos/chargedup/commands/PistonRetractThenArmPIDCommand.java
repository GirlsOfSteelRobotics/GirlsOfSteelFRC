package com.gos.chargedup.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.chargedup.subsystems.ArmSubsystem;


public class PistonRetractThenArmPIDCommand extends CommandBase {
    private final ArmSubsystem m_armSubsytem;
    private final double m_armAngleGoal;

    private boolean m_isFinished;

    public PistonRetractThenArmPIDCommand(ArmSubsystem armSubsystem, double armAngleGoal) {
        this.m_armSubsytem = armSubsystem;
        m_armAngleGoal = armAngleGoal;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_armSubsytem);
    }

    @Override
    public void initialize() {
        m_armSubsytem.fullRetract();
    }

    @Override
    public void execute() {
        m_isFinished = m_armSubsytem.pivotArmToAngle(m_armAngleGoal);
    }

    @Override
    public boolean isFinished() {
        return m_isFinished;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
