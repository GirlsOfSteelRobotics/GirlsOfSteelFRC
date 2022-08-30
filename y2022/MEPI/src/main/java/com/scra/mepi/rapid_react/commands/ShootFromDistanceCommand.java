package com.scra.mepi.rapid_react.commands;

import com.scra.mepi.rapid_react.ShooterLookupTable;
import com.scra.mepi.rapid_react.subsystems.LimelightSubsystem;
import com.scra.mepi.rapid_react.subsystems.ShooterSubsytem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class ShootFromDistanceCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final ShooterSubsytem m_shooterSubsystem;

    private final ShooterLookupTable m_shooterLookupTable;
    private final LimelightSubsystem m_limelightSubsystem;

    /**
     * Creates a new ShootFromDistanceCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ShootFromDistanceCommand(
        ShooterSubsytem subsystem,
        LimelightSubsystem limelightsubsystem,
        ShooterLookupTable shooterLookupTable) {
        m_shooterSubsystem = subsystem;
        m_limelightSubsystem = limelightsubsystem;
        m_shooterLookupTable = shooterLookupTable;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
        addRequirements(limelightsubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_shooterSubsystem.shootFromDistance(m_limelightSubsystem.limelightDistance());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return m_shooterSubsystem.checkAtSpeed(
            m_shooterLookupTable.getRpmTable(m_limelightSubsystem.limelightDistance()));
    }
}
