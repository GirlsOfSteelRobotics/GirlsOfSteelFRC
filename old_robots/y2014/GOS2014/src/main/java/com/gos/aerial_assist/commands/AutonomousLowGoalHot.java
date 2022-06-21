package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.aerial_assist.objects.Camera;
import com.gos.aerial_assist.subsystems.Chassis;
import com.gos.aerial_assist.subsystems.Collector;
import com.gos.aerial_assist.subsystems.Driving;
import com.gos.aerial_assist.subsystems.Manipulator;

/**
 * @author Heather
 */
public class AutonomousLowGoalHot extends SequentialCommandGroup {

    /*
     WORKS DO NOT CHANGE (3/28/14)
     */
    public AutonomousLowGoalHot(Chassis chassis, Driving driving, Camera camera, Collector collector, Manipulator manipulator) {
        //        addCommands(new IsGoalHot());
        //        boolean isHot = CommandBase.camera.isGoalHot(); //CommandBase.camera.isHot;

        addCommands(new Wait(0.01));
        //addCommands(new IsGoalHot());
        addCommands(new CollectorWheelForwardAutoVer(collector, camera).withTimeout(0.01));
        addCommands(new ParallelCommandGroup(
            new SetArmAnglePID(manipulator, -18),
            new MoveToPositionLSPB(chassis, driving, 4.6))); //4.6 //SET UP: At the tape of the red/white zone

        addCommands(new CollectorWheelReverseAutoVer(collector, camera));
        addCommands(new CollectorUpALittle(collector));
        addCommands(new CollectorWheelReverse(collector));
        ////        if (isHot) {
        ////            System.out.println("HOT!!");
        ////            addCommands(new CollectorWheelReverse());
        ////        } else {
        ////            System.out.println("Waiting...");
        ////            addCommands(new Wait(3)); //waits for 3 seconds
        ////            addCommands(new CollectorWheelReverse());
        ////        }
    }
}
