/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

/**
 *
 * @author sam
 */
public class CloseAllGrippers extends CommandBase{

    public CloseAllGrippers() {
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
//        topGripper.closeGrip();
//        middleGripper.closeGrip();
        bottomGripper.closeGrip();
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
