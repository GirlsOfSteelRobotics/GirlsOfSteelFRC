package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lib.DeadbandHelper;
import frc.robot.subsystems.ChassisSubsystem;

public class DriveStraightDistanceCustomControlCommand extends CommandBase {

    private final ChassisSubsystem m_chassis;
    private final double m_distance;
    private final double m_allowableError; // NOPMD
    private double m_goalDistance;
    private final DeadbandHelper m_deadbandHelper;

    public DriveStraightDistanceCustomControlCommand(ChassisSubsystem chassis, double goalDistance) {
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
        m_chassis.driveDistanceCustomControl(m_goalDistance);
    }

    @Override
    public boolean isFinished() {
        boolean isFinished = false; // TODO implement
        return m_deadbandHelper.setIsGood(isFinished);
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.setSpeedAndSteer(0, 0);
    }
}
