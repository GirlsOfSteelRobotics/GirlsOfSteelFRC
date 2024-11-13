package com.gos.rapidreact.commands;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.CollectorSubsystem;
import com.gos.rapidreact.subsystems.IntakeLimelightSubsystem;
import edu.wpi.first.wpilibj2.command.Command;


public class LimelightGoToCargoCommand extends Command {
    private final ChassisSubsystem m_chassis;
    private final IntakeLimelightSubsystem m_limelight;
    private final CollectorSubsystem m_collector;
    private boolean m_atPosition;


    public LimelightGoToCargoCommand(ChassisSubsystem chassisSubsystem, IntakeLimelightSubsystem intakeLimelightSubsystem, CollectorSubsystem collectorSubsystem) {
        this.m_chassis = chassisSubsystem;
        this.m_limelight = intakeLimelightSubsystem;
        this.m_collector = collectorSubsystem;

        addRequirements(this.m_chassis, this.m_limelight);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (m_limelight.isVisible() && m_limelight.distanceToCargo() < ChassisSubsystem.TO_XY_MAX_DISTANCE.getValue()) {
            m_atPosition = m_chassis.driveAndTurnPID(m_limelight.distanceToCargo(), m_limelight.getAngle());
            m_collector.rollerIn();
        } else {
            m_chassis.setArcadeDrive(0, 0);
        }
    }

    @Override
    public boolean isFinished() {
        return m_atPosition;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.setArcadeDrive(0, 0);
        m_collector.rollerStop();
    }
}
