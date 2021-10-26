package com.girlsofsteelrobotics.atlas.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Heather
 */
public class AutonomousLowGoalHot extends CommandGroup {

    /*
     WORKS DO NOT CHANGE (3/28/14)
     */
    public AutonomousLowGoalHot() {
//        addSequential(new IsGoalHot());
//        boolean isHot = CommandBase.camera.isGoalHot();//CommandBase.camera.isHot;

        addSequential(new Wait(0.01));
        //addSequential(new IsGoalHot());
        addParallel(new CollectorWheelForwardAutoVer());
        addSequential(new Wait(0.01));
        addParallel(new setArmAnglePID(-18));
        addSequential(new MoveToPositionLSPB(4.6));//4.6 //SET UP: At the tape of the red/white zone

        addSequential(new CollectorWheelReverseAutoVer());
        addSequential(new CollectorUpALittle());
        addSequential(new CollectorWheelReverse());
////        if (isHot) {
////            System.out.println("HOT!!");
////            addSequential(new CollectorWheelReverse());
////        } else {
////            System.out.println("Waiting...");
////            addSequential(new Wait(3)); //waits for 3 seconds
////            addSequential(new CollectorWheelReverse());
////        }
    }
}
