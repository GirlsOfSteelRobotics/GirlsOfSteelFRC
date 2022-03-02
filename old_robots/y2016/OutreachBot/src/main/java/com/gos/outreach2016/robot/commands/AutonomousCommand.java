package com.gos.outreach2016.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.outreach2016.robot.subsystems.AccessoryMotors;
import com.gos.outreach2016.robot.subsystems.AccessoryMotors.Direction;
import com.gos.outreach2016.robot.subsystems.DriveSystem;

/**
 *
 */
public class AutonomousCommand extends CommandBase {

    private final DriveSystem m_driveSystem;
    private final AccessoryMotors m_accessoryMotors;
    private final Timer m_timer;

    public AutonomousCommand(DriveSystem driveSystem, AccessoryMotors accessoryMotors) {
        m_driveSystem = driveSystem;
        m_accessoryMotors = accessoryMotors;
        m_timer = new Timer();
        addRequirements(accessoryMotors);
        addRequirements(driveSystem);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_driveSystem.resetDistance();
        m_timer.reset();
        m_timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_accessoryMotors.startLeft(Direction.kFwd);
        m_accessoryMotors.startRight(Direction.kRev);
        m_driveSystem.forward();
        SmartDashboard.putNumber("Encoder Distance", m_driveSystem.getEncoderDistance());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return m_timer.hasElapsed(1.5);
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_accessoryMotors.stopLeft();
        m_accessoryMotors.stopRight();
        m_driveSystem.stop();
        m_timer.stop();
    }


}
