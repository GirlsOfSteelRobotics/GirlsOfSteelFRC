/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

/**
 *
 * @author sam
 */
public class OpenAllGrippers extends CommandBase{

    public OpenAllGrippers() {
//        requires(topGripper);
//        requires(middleGripper);
        requires(bottomGripper);
    }

    //Command for closing the bottom gripper around the pyramid


    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
//        topGripper.openGrip();
//        middleGripper.openGrip();
        bottomGripper.openGrip();
    }

    @Override
    protected boolean isFinished() {
            return true;

    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
