package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lib.DeadbandHelper;
import frc.robot.subsystems.ChassisSubsystem;

public class DriveStraightDistanceSmartMotionControlCommand extends CommandBase {

    private final ChassisSubsystem m_chassis;
    private final double m_distance;
    private final double m_allowableError;
    private double m_goalDistance;
    private final DeadbandHelper m_deadbandHelper;

    public DriveStraightDistanceSmartMotionControlCommand(ChassisSubsystem chassis, double goalDistance) {
        m_chassis = chassis;
        m_distance = goalDistance;
        m_allowableError = ChassisSubsystem.DEFAULT_ALLOWABLE_POSITION_ERROR;
        m_deadbandHelper = new DeadbandHelper(50);

        addRequirements(chassis);
    }

    @Override
    public void initialize() {
        m_goalDistance = m_chassis.getAverageDistance() + m_distance;
    }

    @Override
    public void execute() {
        m_chassis.driveDistanceSmartMotionControl(m_goalDistance, m_goalDistance);
    }

    @Override
    public boolean isFinished() {
        double error = m_chassis.getAverageDistance() - m_goalDistance;
        return m_deadbandHelper.setIsGood(Math.abs(error) < m_allowableError);
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.setSpeedAndSteer(0, 0);
    }
}
