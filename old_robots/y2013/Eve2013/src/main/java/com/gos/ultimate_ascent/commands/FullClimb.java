package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Climber;
import com.gos.ultimate_ascent.subsystems.Gripper;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.command.WaitForChildren;

/**
 * @author sam
 */
public class FullClimb extends SequentialCommandGroup {

    @SuppressWarnings("PMD.CloseResource")
    public FullClimb(Climber climber, Gripper bottomGripper) {

        //Closes all grippers
        addCommands(new CloseBottomGrip(bottomGripper));
        addParallel(new RetractClimberPiston(climber));
        //Starts motors to begin climbing
        addCommands(new StartClimbMotors(climber));

        CommandGroup topGripperSequence = new CommandGroup();
        CommandGroup middleGripperSequence = new CommandGroup();
        CommandGroup bottomGripperSequence = new CommandGroup();

        //Should the terminating amount be two or three?
        for (int barCount = 0; barCount < 2; barCount++) {
            //This goes through the sequence of opening all of the grips when
            //at the set of bars, and then closes them right after passing by them
            //            topGripperSequence.addCommands(new OpenGripAtBar(CommandBase.topGripper));
            //            topGripperSequence.addCommands(new CloseGripPastBar(CommandBase.topGripper));
            //            middleGripperSequence.addCommands(new OpenGripAtBar(CommandBase.middleGripper));
            //            middleGripperSequence.addCommands(new CloseGripPastBar(CommandBase.middleGripper));
            bottomGripperSequence.addCommands(new OpenGripAtBar(bottomGripper));
            bottomGripperSequence.addCommands(new CloseGripPastBar(bottomGripper));
        }
        addParallel(middleGripperSequence);
        addParallel(bottomGripperSequence);
        addParallel(topGripperSequence);

        addCommands(new WaitForChildren()); //waiting for the parallel little command groups to finish off(they will) and then continues with the main thread.
        //This is so that we can move up the vertical part of the pyramid.
        //        addCommands(new OpenGripAtBar(CommandBase.topGripper));
        //Stops the climbing movement
        addCommands(new StopClimbMotors(climber));
    }

}
//put in manual grips (open/close grips) (smart dash board)
// check soliniod...some are tru and some are false??
