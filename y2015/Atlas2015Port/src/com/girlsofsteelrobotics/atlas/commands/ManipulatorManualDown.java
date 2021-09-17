/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.girlsofsteelrobotics.atlas.commands;

/**
 *
 * @author Abby
 */
public class ManipulatorManualDown extends CommandBase{
    public ManipulatorManualDown(){
        requires (manipulator);
    }

    protected void initialize() {
        manipulator.disablePID();
    }

    protected void execute() {
        manipulator.moveManipulatorDown();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        manipulator.stopManipulator();
        //manipulator.startPID();
        //manipulator.resetPIDError();
        manipulator.initEncoder();
    }

    protected void interrupted() {
        end();
    }
}
