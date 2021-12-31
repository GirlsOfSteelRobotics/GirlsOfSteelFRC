package com.gos.ultimate_ascent.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.ultimate_ascent.subsystems.Climber;
import com.gos.ultimate_ascent.subsystems.Gripper;

/**
 * @author sam
 */
public class TenPointClimb extends CommandGroup {

    public TenPointClimb(Climber climber, Gripper gripper) {

        //Closes all grippers
        addSequential(new CloseBottomGrip(gripper));
        addParallel(new RetractClimberPiston(climber));
        //Starts motors to begin climbing
        addSequential(new StartClimbMotors(climber));

//        addSequential(new OpenGripAtBar(CommandBase.middleGripper));
//        addSequential(new CloseGripPastBar(CommandBase.middleGripper));

        //Stops the climbing movement
        addSequential(new StopClimbMotors(climber));
    }
}
//put in manual grips (open/close grips) (smart dash board)
// check soliniod...some are tru and some are false??
