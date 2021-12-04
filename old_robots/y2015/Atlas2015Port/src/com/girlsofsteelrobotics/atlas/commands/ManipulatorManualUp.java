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
public class ManipulatorManualUp extends CommandBase {
    public ManipulatorManualUp(){
        requires (manipulator);
    }

    @Override
    protected void initialize() {
        manipulator.disablePID();
    }

    @Override
    protected void execute() {
        manipulator.moveManipulatorUp();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        manipulator.stopManipulator();
//        manipulator.resetPIDError();
//        manipulator.startPID();
        manipulator.initEncoder();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
