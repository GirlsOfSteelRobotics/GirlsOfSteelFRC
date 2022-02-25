package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ChassisSubsystem;

//I IMAGINE THIS SOLELY BEING USED TO JUST TEST THAT OUR LOGIC WORKS, NOTHING ELSE
public class GoToHubDistanceCommand extends CommandBase {
    private final ChassisSubsystem m_chassis;
    private final double m_distanceGoal;
    private boolean m_atPosition;

    public GoToHubDistanceCommand(ChassisSubsystem chassisSubsystem, double distanceGoal) {
        this.m_chassis = chassisSubsystem;
        m_distanceGoal = distanceGoal;

        addRequirements(this.m_chassis);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_atPosition = m_chassis.distancePID(m_distanceGoal);
    }

    @Override
    public boolean isFinished() {
        return m_atPosition;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.setArcadeDrive(0, 0);
    }
}
