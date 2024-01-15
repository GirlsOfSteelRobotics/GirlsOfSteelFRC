package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Climber;
import com.gos.ultimate_ascent.subsystems.Gripper;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * @author sam
 */
public class FullClimb extends SequentialCommandGroup {

    @SuppressWarnings("PMD.CloseResource")
    public FullClimb(Climber climber, Gripper bottomGripper) {

        //Closes all grippers
        addCommands(new CloseBottomGrip(bottomGripper)
            .alongWith(new RetractClimberPiston(climber)));
        //Starts motors to begin climbing
        addCommands(new StartClimbMotors(climber));

        SequentialCommandGroup topGripperSequence = new SequentialCommandGroup();
        SequentialCommandGroup middleGripperSequence = new SequentialCommandGroup();
        SequentialCommandGroup bottomGripperSequence = new SequentialCommandGroup();

        //Should the terminating amount be two or three?
        for (int barCount = 0; barCount < 2; barCount++) {
            //This goes through the sequence of opening all of the grips when
            //at the set of bars, and then closes them right after passing by them
            //            topGripperSequence.addCommands(new OpenGripAtBar(Command.topGripper));
            //            topGripperSequence.addCommands(new CloseGripPastBar(Command.topGripper));
            //            middleGripperSequence.addCommands(new OpenGripAtBar(Command.middleGripper));
            //            middleGripperSequence.addCommands(new CloseGripPastBar(Command.middleGripper));
            bottomGripperSequence.addCommands(new OpenGripAtBar(bottomGripper));
            bottomGripperSequence.addCommands(new CloseGripPastBar(bottomGripper));
        }
        addCommands(new ParallelCommandGroup(
            middleGripperSequence,
            bottomGripperSequence,
            topGripperSequence));

        //This is so that we can move up the vertical part of the pyramid.
        //        addCommands(new OpenGripAtBar(Command.topGripper));
        //Stops the climbing movement
        addCommands(new StopClimbMotors(climber));
    }

}
//put in manual grips (open/close grips) (smart dash board)
// check soliniod...some are tru and some are false??
