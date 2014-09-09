/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.girlsofsteelrobotics.atlas.tests;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.commands.CommandBase;

/**
 *
 * @author user
 */
public class TestJags extends CommandBase {

    public TestJags() {
        requires(chassis);
    }
    
    protected void initialize() {
        chassis.initEncoders();
        }

    protected void execute() {
        chassis.setJags(1.0);
        SmartDashboard.putNumber("Left Encoder", chassis.getLeftEncoder());
        SmartDashboard.putNumber("Right Encoder", chassis.getRightEncoder());
    }

    protected boolean isFinished() {
        return false;
                }

    protected void end() {
        chassis.setJags(0.0);
    }

    protected void interrupted() {
        end();
    }
    
}
