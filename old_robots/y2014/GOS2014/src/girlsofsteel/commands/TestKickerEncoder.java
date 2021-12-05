/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Sylvie and Cla
 */
public class TestKickerEncoder extends CommandBase {

    private double startTime;
    private double changeTime;
    private double direction;

    public TestKickerEncoder() {
        requires(kicker);
    }

    @Override
    protected void initialize() {
        kicker.stopJag();
        kicker.initEncoders();
        kicker.resetEncoders();
        startTime = Timer.getFPGATimestamp();
        SmartDashboard.putNumber("Direction", 0.0);
    }

    @Override
    protected void execute() {
        direction = SmartDashboard.getNumber("Direction", 0);
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

    @Override
    protected boolean isFinished() {
        return false; //changeTime > 5;
    }

    @Override
    protected void end() {
        kicker.stopJag();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
