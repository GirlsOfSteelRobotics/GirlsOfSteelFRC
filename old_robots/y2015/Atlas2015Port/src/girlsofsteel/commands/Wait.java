/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Sylvie and Heather
 *
 * This waits for the given number of SECONDSSSS
 */
public class Wait extends CommandBase {

    private final double time;
    private double startTime;

    public Wait(double sec) {
        time = sec;
    }

    @Override
    protected void initialize() {
        startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return Timer.getFPGATimestamp() - startTime > time;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }

}
