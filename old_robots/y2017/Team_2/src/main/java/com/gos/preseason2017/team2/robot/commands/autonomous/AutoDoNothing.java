package com.gos.preseason2017.team2.robot.commands.autonomous;


import edu.wpi.first.wpilibj2.command.Command;
import com.gos.preseason2017.team2.robot.subsystems.Chassis;


/**
 *
 */
public class AutoDoNothing extends Command {


    public AutoDoNothing(Chassis chassis) {
        addRequirements(chassis);
    }


    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }


    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
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
