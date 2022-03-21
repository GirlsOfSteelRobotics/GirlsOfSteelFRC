package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ShooterLimelightSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;


public class AutoConveyorAndShooterCommand extends CommandBase {
    private final ShooterLimelightSubsystem m_shooterLimelight;
    private final ShooterSubsystem m_shooter;
    private final VerticalConveyorSubsystem m_verticalConveyor;

    public AutoConveyorAndShooterCommand(ShooterLimelightSubsystem shooterLimelightSubsystem, ShooterSubsystem shooterSubsystem, VerticalConveyorSubsystem verticalConveyorSubsystem) {
        this.m_shooterLimelight = shooterLimelightSubsystem;
        this.m_shooter = shooterSubsystem;
        this.m_verticalConveyor = verticalConveyorSubsystem;

        // probably don't want this to override vertical conveyor movement
        addRequirements(this.m_shooterLimelight, this.m_shooter);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (m_shooterLimelight.atAcceptableDistance() && m_shooterLimelight.atAcceptableAngle() && m_shooter.isShooterAtSpeed()) {
            m_shooter.rpmForDistance(m_shooterLimelight.getDistanceToHub());
            m_shooter.forwardRoller();
            m_verticalConveyor.forwardFeedMotor();
        }

        else {
            if (m_verticalConveyor.getUpperIndexSensor() && m_verticalConveyor.getLowerIndexSensor()) {
                m_verticalConveyor.stopVerticalConveyorMotor();
            }
            m_verticalConveyor.stopFeedMotor();
            m_shooter.stopRoller();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
