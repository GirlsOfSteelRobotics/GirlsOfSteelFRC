/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.subsystems.Collector;

/**
 *
 * @author Sylvie and Jisue
 */
public class CollectorDownWheelOut extends CommandGroup {

    public CollectorDownWheelOut(Collector collector)
    {
        addParallel(new EngageCollector(collector));
        addParallel(new CollectorWheelReverse(collector));
    }
}
