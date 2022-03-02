package com.gos.ultimate_ascent.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.ultimate_ascent.subsystems.Climber;
import com.gos.ultimate_ascent.subsystems.Gripper;

/**
 * @author sam
 */
public class TenPointClimb extends SequentialCommandGroup {

    public TenPointClimb(Climber climber, Gripper gripper) {

        //Closes all grippers
        addCommands(new CloseBottomGrip(gripper).alongWith(new RetractClimberPiston(climber)));
        //Starts motors to begin climbing
        addCommands(new StartClimbMotors(climber));

        //        addCommands(new OpenGripAtBar(CommandBase.middleGripper));
        //        addCommands(new CloseGripPastBar(CommandBase.middleGripper));

        //Stops the climbing movement
        addCommands(new StopClimbMotors(climber));
    }
}
//put in manual grips (open/close grips) (smart dash board)
// check soliniod...some are tru and some are false??
