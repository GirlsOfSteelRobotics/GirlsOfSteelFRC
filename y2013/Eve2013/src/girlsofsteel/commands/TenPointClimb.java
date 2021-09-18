package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author sam
 */
public class TenPointClimb extends CommandGroup {

    public TenPointClimb() {

        //Closes all grippers
        addSequential(new CloseBottomGrip());
        addParallel(new RetractClimberPiston());
        //Starts motors to begin climbing
        addSequential(new StartClimbMotors());

//        addSequential(new OpenGripAtBar(CommandBase.middleGripper));
//        addSequential(new CloseGripPastBar(CommandBase.middleGripper));

        //Stops the climbing movement
        addSequential(new StopClimbMotors());
    }
}
//put in manual grips (open/close grips) (smart dash board)
// check soliniod...some are tru and some are false??