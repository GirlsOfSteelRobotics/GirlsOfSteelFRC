/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.aerial_assist.objects.Camera;
import com.gos.aerial_assist.subsystems.Manipulator;

/**
 * @author
 */
public class AutoShoot extends SequentialCommandGroup {

    public AutoShoot(Camera camera, Manipulator manipulator) {
        double angle = camera.getVerticalAngleOffset();
        addCommands(new SetArmAnglePID(manipulator, angle));
    }
}
