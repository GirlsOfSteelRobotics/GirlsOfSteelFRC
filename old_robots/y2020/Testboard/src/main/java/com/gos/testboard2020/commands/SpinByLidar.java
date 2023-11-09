package com.gos.testboard2020.commands;

import com.gos.lib.sensors.LidarLite;
import com.gos.testboard2020.subsystems.Motor;
import edu.wpi.first.wpilibj2.command.Command;

public class SpinByLidar extends Command {
    private static final double GOAL_LIDAR = 6;
    private static final double LIDAR_TOLERANCE = 1.0;

    private final Motor m_motor;
    private final LidarLite m_lidar;

    public SpinByLidar(Motor motor, LidarLite lidar) {
        m_motor = motor;
        m_lidar = lidar;

        // Use super.addRequirements here to declare subsystem dependencies
        super.addRequirements(m_motor);
    }


    @Override
    public void initialize() {
        System.out.println("SpinByLidar init");
    }


    @Override
    public void execute() {
        m_motor.motorGoFast();
    }


    @Override
    public boolean isFinished() {
        System.out.println("SpinByLidar lidar distance: " + m_lidar.getDistance());
        return m_lidar.getDistance() <= GOAL_LIDAR + LIDAR_TOLERANCE && m_lidar.getDistance() >= GOAL_LIDAR - LIDAR_TOLERANCE;
    }


    @Override
    public void end(boolean interrupted) {
        m_motor.stop();
    }
}
