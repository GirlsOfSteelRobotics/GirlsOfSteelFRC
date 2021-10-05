package com.gos.testboard2020.commands;

import com.gos.testboard2020.subsystems.Lidar;
import com.gos.testboard2020.subsystems.Motor;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinByLidar extends CommandBase {
    private static final double GOAL_LIDAR = 6;

    private final Motor m_motor;
    private final Lidar m_lidar;

    public SpinByLidar(Motor motor, Lidar lidar) {
        m_motor = motor;
        m_lidar = lidar;

        // Use super.addRequirements here to declare subsystem dependencies
        super.addRequirements(m_motor);
        super.addRequirements(m_lidar);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        System.out.println("SpinByLidar init");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_motor.motorGoFast();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        System.out.println("SpinByLidar lidar distance: " + m_lidar.getDistance());
        return m_lidar.getDistance() <= GOAL_LIDAR + Lidar.LIDAR_TOLERANCE && m_lidar.getDistance() >= GOAL_LIDAR - Lidar.LIDAR_TOLERANCE;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_motor.stop();
    }
}
