package com.gos.stronghold.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.stronghold.robot.subsystems.Chassis;

/**
 *
 */
public class AutoNavBoard extends CommandBase {

    private final Chassis m_chassis;
    private final double m_inches;

    public AutoNavBoard(Chassis chassis, double distance) {
        m_chassis = chassis;
        addRequirements(m_chassis);
        m_inches = distance;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_chassis.resetEncoderDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_chassis.driveSpeed(-.4);
        m_chassis.printEncoderValues();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return m_chassis.getEncoderDistance() >= m_inches;
    }


}
