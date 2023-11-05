package com.scra.mepi.rapid_react.commands;

import com.scra.mepi.rapid_react.subsystems.ShooterSubsytem;
import com.scra.mepi.rapid_react.subsystems.TowerSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class KickIfShootSetRPMCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final ShooterSubsytem m_shooterSubsystem;

    private final TowerSubsystem m_towerSubsystem;

    private final double m_rpm;

    /**
     * Creates a new KickIfShooterGoBrrCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public KickIfShootSetRPMCommand(ShooterSubsytem subsystem, TowerSubsystem towerSubsystem, double rpm) {
        m_shooterSubsystem = subsystem;
        m_towerSubsystem = towerSubsystem;
        m_rpm = rpm;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
        addRequirements(towerSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_shooterSubsystem.setPidRpm(m_rpm);
        if (m_shooterSubsystem.checkAtSpeed(m_rpm)) {
            m_towerSubsystem.setKickerSpeed(1);
            m_towerSubsystem.setTowerSpeed(0.75);
        }
        SmartDashboard.putBoolean("kickIfShootRPM atSpeed", m_shooterSubsystem.checkAtSpeed(m_rpm));
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_towerSubsystem.setKickerSpeed(0);
        m_towerSubsystem.setTowerSpeed(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
