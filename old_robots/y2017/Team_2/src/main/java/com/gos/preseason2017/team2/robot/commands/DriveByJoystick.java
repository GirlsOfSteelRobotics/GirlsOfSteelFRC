package com.gos.preseason2017.team2.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.preseason2017.team2.robot.OI;
import com.gos.preseason2017.team2.robot.subsystems.Chassis;

/**
 *
 */
public class DriveByJoystick extends CommandBase {

    private final OI m_oi;
    private final Chassis m_chassis;

    public DriveByJoystick(OI oi, Chassis chassis) {
        m_chassis = chassis;
        m_oi = oi;
        addRequirements(m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        SmartDashboard.putBoolean("Drive by Joystick", true);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_chassis.driveByJoystick(m_oi.getStick());
        //Robot.chassis.printEncoderValues();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_chassis.stop();
    }


}
