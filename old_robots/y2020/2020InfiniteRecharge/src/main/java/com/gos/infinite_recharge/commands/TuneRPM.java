package com.gos.infinite_recharge.commands;

import com.gos.infinite_recharge.Constants;
import com.gos.infinite_recharge.subsystems.Shooter;
import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.wpilibj2.command.Command;


public class TuneRPM extends Command {

    private static final GosDoubleProperty TUNE_RPM_PROP = new GosDoubleProperty(false, "TuneRpm", Constants.DEFAULT_RPM);
    private final Shooter m_shooter;

    public TuneRPM(Shooter shooter) {
        this.m_shooter = shooter;

        super.addRequirements(shooter);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double goalRPM = TUNE_RPM_PROP.getValue();
        m_shooter.setRPM(goalRPM);
        System.out.println("goalRPM" + goalRPM);
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
