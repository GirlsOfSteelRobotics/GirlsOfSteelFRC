/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.commands;

/**
 *
 * @author Abby
 */
public class ManipulatorManualUp extends CommandBase {
    public ManipulatorManualUp(){
        requires (manipulator);
    }

    protected void initialize() {
        manipulator.disablePID();
    }

    protected void execute() {
        manipulator.moveManipulatorUp();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        manipulator.stopManipulator();
//        manipulator.resetPIDError();
//        manipulator.startPID();
        manipulator.initEncoder();
    }

    protected void interrupted() {
        end();
    }
    
}
