package com.gos.infinite_recharge.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.infinite_recharge.subsystems.Shooter;


public class RunShooterRPMWhileHeld extends Command {

    private final Shooter m_shooter;
    private final double m_goalRPM;

    public RunShooterRPMWhileHeld(Shooter shooter, double goalRPM) {
        this.m_shooter = shooter;
        this.m_goalRPM = goalRPM;

        super.addRequirements(shooter);
    }

    @Override
    public void initialize() {
        System.out.println("init RunShooterRPMWhileHeld");
    }

    @Override
    public void execute() {
        m_shooter.setRPM(m_goalRPM);
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stop();
        System.out.println("end RunShooterRPMWhileHeld");

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
