/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.gos.ultimate_ascent.RobotMap;

/**
 * @author Heather
 */
public class Gripper extends Subsystem {
    private final DigitalInput m_openGripperSwitch;
    private final DigitalInput m_closeGripperSwitch;
    private final Solenoid m_openSolenoid;
    private final Solenoid m_closeSolenoid;

    public Gripper(DigitalInput openGripperSwitch, DigitalInput closeGripperSwitch,
                   int openSolenoidPort, int closeSolenoidPort) {
        this.m_openGripperSwitch = openGripperSwitch;
        this.m_closeGripperSwitch = closeGripperSwitch;

        m_openSolenoid = new Solenoid(RobotMap.CLIMBER_MODULE, PneumaticsModuleType.CTREPCM, openSolenoidPort);
        m_closeSolenoid = new Solenoid(RobotMap.CLIMBER_MODULE, PneumaticsModuleType.CTREPCM, closeSolenoidPort);
    }

    //Moves the pneumatic piston slider out
    public void closeGrip() {
        m_openSolenoid.set(true);
        m_closeSolenoid.set(false);
    }

    //Getter method -> Tells if the piston slider is out (true) or in (false)
    public boolean gripperClosed() {
        return m_closeSolenoid.get();
    }


    //Moves the pneumatic piston slider in
    public void openGrip() {

        m_openSolenoid.set(false);
        m_closeSolenoid.set(true);
    }
    //Limit Switch Method

    //at bar means hitting open limit switch...PLEASE CHECK THIS!!!!!!!!
    public boolean atBar() {
        return !m_openGripperSwitch.get();
    } //Tells you that you have gone up a bar (the switch hitting a corner)

    //past bar means not hitting closed limit switch...PLEASE CHECK THIS!!!!!!!!
    public boolean pastBar() {
        return !m_closeGripperSwitch.get();
    }
    //This will count how many bar you hit, until you get to three at which point it returns true.

    @Override
    protected void initDefaultCommand() {
    }
}
