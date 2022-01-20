/*
  * To change this template, choose Tools | Templates
  * and open the template in the editor.
  */

package com.gos.ultimate_ascent.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.gos.ultimate_ascent.RobotMap;

/**
 * @author Sonia and Alex
 */
public class Feeder extends Subsystem {
    //Shooter piston
    private final Solenoid m_frontPiston;
    private final Solenoid m_backPiston;
    private final Solenoid m_frontBlocker;
    private final Solenoid m_backBlocker;
    private boolean m_isRaised;

    public Feeder() {
        //Shooter piston
        m_frontPiston = new Solenoid(RobotMap.SHOOTER_MODULE, RobotMap.SHOOTER_PISTON_FRONT);
        m_backPiston = new Solenoid(RobotMap.SHOOTER_MODULE, RobotMap.SHOOTER_PISTON_BACK);
        m_frontBlocker = new Solenoid(RobotMap.BLOCKER_MODULE, RobotMap.OPEN_BLOCKER_SOLENOID);
        m_backBlocker = new Solenoid(RobotMap.BLOCKER_MODULE, RobotMap.CLOSE_BLOCKER_SOLENOID);
        m_isRaised = false;
    }

    public boolean getIsRaised() {
        return m_isRaised;
    }

    public void setIsRaised(boolean newRaised) {
        m_isRaised = newRaised;
    }

    //Shooter Piston Methods
    public void pushShooter() {
        m_frontPiston.set(false);
        m_backPiston.set(true);
    }

    public void pullShooter() {
        m_frontPiston.set(true);
        m_backPiston.set(false);
    }

    public void pushBlocker() {
        m_frontBlocker.set(false);
        m_backBlocker.set(true);
    }

    public void pullBlocker() {
        m_frontBlocker.set(true);
        m_backBlocker.set(false);
    }

    @Override
    protected void initDefaultCommand() {
    }
}
