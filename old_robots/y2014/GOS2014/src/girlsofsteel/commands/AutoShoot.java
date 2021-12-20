/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.objects.Camera;
import girlsofsteel.subsystems.Manipulator;

/**
 *
 * @author
 */
public class AutoShoot extends CommandGroup {

    public AutoShoot(Camera camera, Manipulator manipulator) {
        double angle = camera.getVerticalAngleOffset();
        addSequential(new SetArmAnglePID(manipulator, angle));
    }
}
