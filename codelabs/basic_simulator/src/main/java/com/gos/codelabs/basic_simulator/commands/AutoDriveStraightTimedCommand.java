package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoDriveStraightTimedCommand extends CommandBase {

    private final ChassisSubsystem m_chassis;
    private final Timer m_timer;
    private final double m_speed;
    private final double m_time;

    public AutoDriveStraightTimedCommand(ChassisSubsystem chassis, double speed, double time) {
        m_chassis = chassis;
        m_speed = speed;
        m_time = time;
        m_timer = new Timer();

        if (m_speed > 1 || m_speed < -1) {
            throw new IllegalArgumentException("Speed (" + m_speed + ") should be between [-1, 1]");
        }

        if (m_time <= 0) {
            throw new IllegalArgumentException("The time to run (" + m_time + ") should be greater than 0");
        }

        addRequirements(chassis);
    }

    @Override
    public void initialize() {
        // TODO implement
    }

    @Override
    public void execute() {
        // TODO implement
    }

    @Override
    public boolean isFinished() {
        // TODO implement
        return false;
    }
}
