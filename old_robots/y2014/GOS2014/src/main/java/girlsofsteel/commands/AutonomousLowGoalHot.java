package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.objects.Camera;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Collector;
import girlsofsteel.subsystems.Driving;
import girlsofsteel.subsystems.Manipulator;

/**
 * @author Heather
 */
public class AutonomousLowGoalHot extends CommandGroup {

    /*
     WORKS DO NOT CHANGE (3/28/14)
     */
    public AutonomousLowGoalHot(Chassis chassis, Driving driving, Camera camera, Collector collector, Manipulator manipulator) {
//        addSequential(new IsGoalHot());
//        boolean isHot = CommandBase.camera.isGoalHot();//CommandBase.camera.isHot;

        addSequential(new Wait(0.01));
        //addSequential(new IsGoalHot());
        addParallel(new CollectorWheelForwardAutoVer(collector, camera));
        addSequential(new Wait(0.01));
        addParallel(new SetArmAnglePID(manipulator, -18));
        addSequential(new MoveToPositionLSPB(chassis, driving, 4.6));//4.6 //SET UP: At the tape of the red/white zone

        addSequential(new CollectorWheelReverseAutoVer(collector, camera));
        addSequential(new CollectorUpALittle(collector));
        addSequential(new CollectorWheelReverse(collector));
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
