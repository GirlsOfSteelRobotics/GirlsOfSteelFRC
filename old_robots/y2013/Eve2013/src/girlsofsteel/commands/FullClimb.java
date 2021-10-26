package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitForChildren;

/**
 *
 * @author sam
 */
public class FullClimb extends CommandGroup {

    public FullClimb()
    {

        //Closes all grippers
        addSequential(new CloseBottomGrip());
        addParallel(new RetractClimberPiston());
        //Starts motors to begin climbing
        addSequential(new StartClimbMotors());

        CommandGroup topGripperSequence=new CommandGroup();
        CommandGroup middleGripperSequence=new CommandGroup();
        CommandGroup bottomGripperSequence=new CommandGroup();

        //Should the terminating amount be two or three?
        for(int barCount = 0; barCount < 2; barCount++)
        {
            //This goes through the sequence of opening all of the grips when
            //at the set of bars, and then closes them right after passing by them
//            topGripperSequence.addSequential(new OpenGripAtBar(CommandBase.topGripper));
//            topGripperSequence.addSequential(new CloseGripPastBar(CommandBase.topGripper));
//            middleGripperSequence.addSequential(new OpenGripAtBar(CommandBase.middleGripper));
//            middleGripperSequence.addSequential(new CloseGripPastBar(CommandBase.middleGripper));
            bottomGripperSequence.addSequential(new OpenGripAtBar(CommandBase.bottomGripper));
            bottomGripperSequence.addSequential(new CloseGripPastBar(CommandBase.bottomGripper));
        }
        addParallel(middleGripperSequence);
        addParallel(bottomGripperSequence);
        addParallel(topGripperSequence);

        addSequential(new WaitForChildren()); //waiting for the parallel little command groups to finish off(they will) and then continues with the main thread.
        //This is so that we can move up the vertical part of the pyramid.
//        addSequential(new OpenGripAtBar(CommandBase.topGripper));
        //Stops the climbing movement
        addSequential(new StopClimbMotors());
    }

}
//put in manual grips (open/close grips) (smart dash board)
// check soliniod...some are tru and some are false??
