package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import com.gos.lib.properties.PropertyManager;
import frc.robot.subsystems.Shooter;


public class TuneRPM extends CommandBase {

    private static final PropertyManager.IProperty<Double> TUNE_RPM_PROP = new PropertyManager.DoubleProperty("TuneRpm", Constants.DEFAULT_RPM);
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
