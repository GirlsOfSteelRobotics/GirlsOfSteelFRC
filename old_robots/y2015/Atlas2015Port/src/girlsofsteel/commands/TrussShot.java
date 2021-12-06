/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Sylvie
 */
public class TrussShot extends CommandGroup {

    public TrussShot() {
        addSequential(new setArmAnglePID(50)); //angle of 50 was experimentally determined
        //Robot must be between red-white zone tape and about a meter towards the truss
        addParallel(new DisengageCollector());
        addSequential(new KickerUsingLimitSwitch(0, false));
        addSequential(new KickerUsingLimitSwitch(1, false));
        addSequential(new StopCollector()); //never 'stops', must be interuppted
    }
}
