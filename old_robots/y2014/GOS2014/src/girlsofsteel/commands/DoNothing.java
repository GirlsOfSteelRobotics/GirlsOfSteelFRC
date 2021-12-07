/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

/**
 *
 * @author ClarVie
 */
public class DoNothing extends CommandBase {

    public DoNothing() {
        requires(driving);
    }

    @Override
    protected void initialize() {
        //smanipulator.disablePID();
    }

    @Override
    protected void execute() {
        //manipulator.stopJag();
//        chassis.stopJags();
//        collector.stopCollector();
//        collector.stopCollectorWheel();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        //manipulator.stopJag();
//        chassis.stopJags();
//        collector.stopCollector();
//        collector.stopCollectorWheel();
        //manipulator.startPID();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
