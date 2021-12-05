/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

/**
 *
 * @author sam
 */
public class TipRobotOver extends CommandBase{

    public TipRobotOver() {
        requires (climber);
    }

    //will extend the piston to tip the robot over onto the pyramid


    @Override
    protected void initialize() {


    }

    @Override
    protected void execute() {
        climber.extendLifterPiston();
    }

    @Override
    protected boolean isFinished() {
        return climber.isPistonExtended();
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
    }

}
