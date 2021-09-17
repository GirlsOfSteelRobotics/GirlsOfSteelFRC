/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Sylvie and Cla
 */
public class TestKickerEncoder extends CommandBase {

    double startTime;
    double changeTime;
    double direction;

    public TestKickerEncoder() {
        requires(kicker);
    }

    protected void initialize() {
        kicker.stopJag();
        kicker.initEncoders();
        kicker.resetEncoders();
        startTime = Timer.getFPGATimestamp();
        SmartDashboard.putNumber("Direction", 0.0);
    }

    protected void execute() {
        direction = SmartDashboard.getNumber("Direction");
        if (direction == 1) {
            kicker.setTalon(0.5);
        } else if (direction == -1) {
            kicker.setTalon(-.5);
        }
        else {
            kicker.stopTalon();
        }
        changeTime = Timer.getFPGATimestamp() - startTime;
    }

    protected boolean isFinished() {
        return false; //changeTime > 5;
    }

    protected void end() {
        kicker.stopJag();
    }

    protected void interrupted() {
        end();
    }

}
