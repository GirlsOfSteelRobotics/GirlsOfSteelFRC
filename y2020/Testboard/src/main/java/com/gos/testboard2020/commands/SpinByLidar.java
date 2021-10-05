package com.gos.testboard2020.commands;

import com.gos.testboard2020.subsystems.Lidar;
import com.gos.testboard2020.subsystems.Motor;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinByLidar extends CommandBase {

    private Motor m_motor;
    private Lidar m_lidar;
    private double goalLidar = 6;

    public SpinByLidar(Motor motor, Lidar lidar) {
        m_motor = motor;
        m_lidar = lidar;

        // Use super.addRequirements here to declare subsystem dependencies
        super.addRequirements(m_motor);
        super.addRequirements(m_lidar);
    }

    // Called just before this Command runs the first time
    public void initialize() {
        System.out.println("SpinByLidar init");
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
        m_motor.motorGoFast();
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        System.out.println("SpinByLidar lidar distance: " + m_lidar.getDistance());
        return (m_lidar.getDistance() <= goalLidar + m_lidar.LIDAR_TOLERANCE && m_lidar.getDistance() >= goalLidar - m_lidar.LIDAR_TOLERANCE);
    }

    // Called once after isFinished returns true
    public void end(boolean interrupted) {
        m_motor.stop();
    }
}
