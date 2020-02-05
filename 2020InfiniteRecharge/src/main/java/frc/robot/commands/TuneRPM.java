package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;


public class TuneRPM extends CommandBase {

    private final Shooter m_shooter;

    public TuneRPM(Shooter shooter) {
        this.m_shooter = shooter;

        super.addRequirements(shooter);

        SmartDashboard.putNumber("Tune RPM", 0);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double goalRPM;
        goalRPM = SmartDashboard.getNumber("Tune RPM", 0);
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
