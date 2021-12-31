package com.gos.rebound_rumble.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.gos.rebound_rumble.RobotMap;

public class Bridge extends Subsystem {
    private static final double JAG_SPEED = 1.0;

    private final Jaguar m_bridgeArmJag = new Jaguar(RobotMap.BRIDGE_ARM_JAG);
    //    public Relay bridgeArmSpike = new Relay(RobotMap.BRIDGE_ARM_SPIKE);
    private final DigitalInput m_upLimitSwitch = new DigitalInput(RobotMap.BRIDGE_UP_LIMIT_SWITCH);
    private final DigitalInput m_downLimitSwitch = new DigitalInput(RobotMap.BRIDGE_DOWN_LIMIT_SWITCH);
    private boolean m_goingUp;

    public boolean isFullyUp() {
        return m_upLimitSwitch.get();
    }

    public boolean hasHitBridge() {
        return m_downLimitSwitch.get();
    }

    public void downBridgeArm() {
        m_bridgeArmJag.set(JAG_SPEED);
        //        bridgeArmSpike.set(Relay.Value.kForward);
        m_goingUp = false;
    }

    public void upBridgeArm() {
        m_bridgeArmJag.set(-JAG_SPEED);
        //        bridgeArmSpike.set(Relay.Value.kReverse);
        m_goingUp = true;
    }

    public void stopBridgeArm() {
        m_bridgeArmJag.set(0.0);
        //        bridgeArmSpike.set(Relay.Value.kOff);
    }

    public Bridge() {
        new Thread() { // NOPMD
            @Override
            public void run() {
                while (true) {
                    safetyCheck();
                    Timer.delay(.01);
                }
            }
        }.start();
    }

    //the safety check is important -> stops the bridge arm when it is running
    //constantly into the limit switches
    private void safetyCheck() {
        if (isFullyUp() && m_goingUp) {
            m_bridgeArmJag.set(0.0);
            //            bridgeArmSpike.set(Relay.Value.kOff);
        }
        if (hasHitBridge() && !m_goingUp) {
            m_bridgeArmJag.set(0.0);
            //            bridgeArmSpike.set(Relay.Value.kOff);
        }
    }

    @Override
    protected void initDefaultCommand() {
    }
}
