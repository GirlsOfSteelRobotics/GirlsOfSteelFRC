package com.gos.infinite_recharge.commands;

import com.gos.infinite_recharge.Constants;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.lib.properties.PropertyManager;
import com.gos.infinite_recharge.subsystems.Shooter;


public class TuneRPM extends CommandBase {

    private static final PropertyManager.IProperty<Double> TUNE_RPM_PROP = PropertyManager.createDoubleProperty(false, "TuneRpm", Constants.DEFAULT_RPM);
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
