/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.gos.ultimate_ascent.RobotMap;

/**
 * @author Heather
 */
public class Climber extends Subsystem {

    //cLIMBER sPIKES
    private final Relay m_rightClimberSpike = new Relay(RobotMap.RIGHT_CLIMBER_SPIKE);
    private final Relay m_leftClimberSpike = new Relay(RobotMap.LEFT_CLIMBER_SPIKE);


    private final Solenoid m_extendLifterPistonSolenoid = new Solenoid(
        RobotMap.LIFTER_MODULE, RobotMap.EXTEND_LIFTER_PISTON_SOLENOID);
    private final Solenoid m_retractLifterPistonSolenoid = new Solenoid(
        RobotMap.LIFTER_MODULE, RobotMap.RETRACT_LIFTER_PISTON_SOLENOID);


    //Piston methods
    public void extendLifterPiston() {
        m_extendLifterPistonSolenoid.set(true);
        m_retractLifterPistonSolenoid.set(false);
    }

    public void retractLifterPiston() {
        m_retractLifterPistonSolenoid.set(true);
        m_extendLifterPistonSolenoid.set(false);
    }

    //TODO figure out how to check piston

    public boolean isPistonExtended() {
        return m_extendLifterPistonSolenoid.get();
    }

    //Spikes methods
    public void forwardRightClimberSpike() {
        m_rightClimberSpike.set(Relay.Value.kReverse);
    }

    public void reverseRightClimberSpike() {
        m_rightClimberSpike.set(Relay.Value.kForward);
    }

    public void stopRightClimberSpike() {
        m_rightClimberSpike.set(Relay.Value.kOff);
    }

    public void forwardLeftClimberSpike() {
        m_leftClimberSpike.set(Relay.Value.kForward);
    }

    public void reverseLeftClimberSpike() {
        m_leftClimberSpike.set(Relay.Value.kReverse);
    }

    public void stopLeftClimberSpike() {
        m_leftClimberSpike.set(Relay.Value.kOff);
    }

    @Override
    protected void initDefaultCommand() {
    }


}
