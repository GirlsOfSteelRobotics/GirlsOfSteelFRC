/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Driving;
import girlsofsteel.subsystems.Manipulator;

/**
 * @author Parent
 */
public class AutonomousHighGoal extends CommandGroup {
    //Magic numbers need to be added!
    public AutonomousHighGoal(Chassis chassis, Driving driving, Manipulator manipulator) {
        addSequential(new MoveToPosition(chassis, driving));
        addParallel(new SetArmAnglePID(manipulator, 85)); //Magic angle, needs to be corrected
        addSequential(new ShootHigh()); //ShootHigh command is not finished
    }
}
