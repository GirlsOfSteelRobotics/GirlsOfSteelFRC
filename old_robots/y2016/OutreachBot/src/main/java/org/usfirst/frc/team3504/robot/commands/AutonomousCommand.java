package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3504.robot.subsystems.AccessoryMotors;
import org.usfirst.frc.team3504.robot.subsystems.AccessoryMotors.Direction;
import org.usfirst.frc.team3504.robot.subsystems.DriveSystem;

/**
 *
 */
public class AutonomousCommand extends Command {

    private final DriveSystem m_driveSystem;
    private final AccessoryMotors m_accessoryMotors;

    public AutonomousCommand(DriveSystem driveSystem, AccessoryMotors accessoryMotors) {
        m_driveSystem = driveSystem;
        m_accessoryMotors = accessoryMotors;
        requires(accessoryMotors);
        requires(driveSystem);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_driveSystem.resetDistance();
        setTimeout(1.5);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_accessoryMotors.startLeft(Direction.kFwd);
        m_accessoryMotors.startRight(Direction.kRev);
        m_driveSystem.forward();
        SmartDashboard.putNumber("Encoder Distance", m_driveSystem.getEncoderDistance());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_accessoryMotors.stopLeft();
        m_accessoryMotors.stopRight();
        m_driveSystem.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
