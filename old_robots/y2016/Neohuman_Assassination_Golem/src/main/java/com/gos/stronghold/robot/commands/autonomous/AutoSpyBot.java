package com.gos.stronghold.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.stronghold.robot.commands.Drive;
import com.gos.stronghold.robot.commands.PivotDown;
import com.gos.stronghold.robot.commands.PivotUp;
import com.gos.stronghold.robot.commands.ReleaseBall;
import com.gos.stronghold.robot.subsystems.Chassis;
import com.gos.stronghold.robot.subsystems.Claw;
import com.gos.stronghold.robot.subsystems.Pivot;
import com.gos.stronghold.robot.subsystems.Shooter;

/**
 *
 */
public class AutoSpyBot extends SequentialCommandGroup {

    public AutoSpyBot(Chassis chassis, Pivot pivot, Claw claw, Shooter shooter) {
        addCommands(new Drive(chassis, 132, 0));  //Drive x inches until across from goal. Goal should be on right side (possibly) of robot.
        addCommands(new Drive(chassis, 10, 0));  //TO DO: Change Drive. This line should turn the robot 90 degrees.
        addCommands(new Drive(chassis, 160, 0)); //Robot drives halfway across field
        addCommands(new Drive(chassis, 10, 0));   //TO DO: Turn the robot. If we turn the robot more than 90 degrees the first time, this line may be irrelevant.
        addCommands(new Drive(chassis, 60, 0));  //drive completely to goal.
        addCommands(new PivotDown(pivot)); //The pivot goes down.
        addCommands(new ReleaseBall(claw, shooter)); //The claws open and the ball is released.
        addCommands(new PivotUp(pivot)); //The pivot goes back up.
    }
}
