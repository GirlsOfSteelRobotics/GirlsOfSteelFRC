/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.subsystems.Kicker;

/**
 * @author Sylvie and Cla
 */
public class TestKickerEncoder extends CommandBase {

    private final Kicker m_kicker;
    private double m_direction;

    public TestKickerEncoder(Kicker kicker) {
        m_kicker = kicker;
        requires(m_kicker);
    }

    @Override
    protected void initialize() {
        m_kicker.stopJag();
        m_kicker.initEncoders();
        m_kicker.resetEncoders();
        SmartDashboard.putNumber("Direction", 0.0);
    }

    @Override
    protected void execute() {
        m_direction = SmartDashboard.getNumber("Direction", 0);
        if (m_direction == 1) {
            m_kicker.setTalon(0.5);
        } else if (m_direction == -1) {
            m_kicker.setTalon(-.5);
        } else {
            m_kicker.stopTalon();
        }
    }

    @Override
    protected boolean isFinished() {
        return false; //changeTime > 5;
    }

    @Override
    protected void end() {
        m_kicker.stopJag();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
