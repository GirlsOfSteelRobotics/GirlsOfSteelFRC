package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.subsystems.Lifter;

import edu.wpi.first.wpilibj.command.Command;

/*
 *
 */
public class AutoLifterDownToBottom extends Command {

    public AutoLifterDownToBottom() {
        requires(Robot.lifter);
    }

    @Override
    protected void initialize() {
        Robot.lifter.setPosition(Lifter.DISTANCE_ZERO_TOTES);
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return (Robot.lifter.isAtPosition() || Robot.lifter.isAtBottom());
    }

    @Override
    protected void end() {
        Robot.lifter.stop();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
