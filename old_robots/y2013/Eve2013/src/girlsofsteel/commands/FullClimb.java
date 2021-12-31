package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitForChildren;
import girlsofsteel.subsystems.Climber;
import girlsofsteel.subsystems.Gripper;

/**
 * @author sam
 */
public class FullClimb extends CommandGroup {

    @SuppressWarnings("PMD.CloseResource")
    public FullClimb(Climber climber, Gripper bottomGripper) {

        //Closes all grippers
        addSequential(new CloseBottomGrip(bottomGripper));
        addParallel(new RetractClimberPiston(climber));
        //Starts motors to begin climbing
        addSequential(new StartClimbMotors(climber));

        CommandGroup topGripperSequence = new CommandGroup();
        CommandGroup middleGripperSequence = new CommandGroup();
        CommandGroup bottomGripperSequence = new CommandGroup();

        //Should the terminating amount be two or three?
        for (int barCount = 0; barCount < 2; barCount++) {
            //This goes through the sequence of opening all of the grips when
            //at the set of bars, and then closes them right after passing by them
//            topGripperSequence.addSequential(new OpenGripAtBar(CommandBase.topGripper));
//            topGripperSequence.addSequential(new CloseGripPastBar(CommandBase.topGripper));
//            middleGripperSequence.addSequential(new OpenGripAtBar(CommandBase.middleGripper));
//            middleGripperSequence.addSequential(new CloseGripPastBar(CommandBase.middleGripper));
            bottomGripperSequence.addSequential(new OpenGripAtBar(bottomGripper));
            bottomGripperSequence.addSequential(new CloseGripPastBar(bottomGripper));
        }
        addParallel(middleGripperSequence);
        addParallel(bottomGripperSequence);
        addParallel(topGripperSequence);

        addSequential(new WaitForChildren()); //waiting for the parallel little command groups to finish off(they will) and then continues with the main thread.
        //This is so that we can move up the vertical part of the pyramid.
//        addSequential(new OpenGripAtBar(CommandBase.topGripper));
        //Stops the climbing movement
        addSequential(new StopClimbMotors(climber));
    }

}
//put in manual grips (open/close grips) (smart dash board)
// check soliniod...some are tru and some are false??
