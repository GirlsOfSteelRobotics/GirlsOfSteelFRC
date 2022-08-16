package com.gos.preseason2017.team1.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.preseason2017.team1.robot.subsystems.Arm;

/**
 *
 */
public class ArmDown extends CommandBase {

    private final Arm m_arm;

    public ArmDown(Arm arm) {
        m_arm = arm;
        addRequirements(m_arm);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_arm.armDown();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {

    }


}
