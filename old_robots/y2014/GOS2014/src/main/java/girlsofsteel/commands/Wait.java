/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Timer;

/**
 * @author Sylvie and Heather
 * <p>
 * This waits for the given number of SECONDSSSS
 */
public class Wait extends CommandBase {

    private final double m_time;
    private double m_startTime;

    public Wait(double sec) {
        m_time = sec;
    }

    @Override
    protected void initialize() {
        m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return Timer.getFPGATimestamp() - m_startTime > m_time;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }

}
