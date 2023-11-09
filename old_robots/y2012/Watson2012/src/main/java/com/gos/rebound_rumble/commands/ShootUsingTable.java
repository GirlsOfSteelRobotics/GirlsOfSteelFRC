package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.Joystick;
import com.gos.rebound_rumble.OI;
import com.gos.rebound_rumble.objects.Camera;
import com.gos.rebound_rumble.subsystems.Shooter;

public class ShootUsingTable extends GosCommandBase {

    private final Shooter m_shooter;
    private final OI m_oi;
    private final Joystick m_operatorJoystick;

    private final boolean m_bank; //bank does not do anything -> tuned for banking // NOPMD
    private double m_cameraDistance;

    public ShootUsingTable(Shooter shooter, OI oi, boolean bank) { //bank = false for autonomous
        //bank = true for everything else (should be fairly straight to the hoop
        m_shooter = shooter;
        m_oi = oi;
        m_operatorJoystick = oi.getOperatorJoystick();
        addRequirements(m_shooter);
        this.m_bank = bank;
    }

    @Override
    public void initialize() {
        m_shooter.initEncoder();
        m_shooter.initPID();
        m_cameraDistance = Camera.getXDistance()/* - 38*(0.0254/1.0)*/;
        //why subtract the fender? we add the fender into the calculations for
        //shooter data table
    }

    @Override
    public void execute() {
        //        if(bank){
        //            shooter.autoShootBank(cameraDistance);
        //        }else{
        m_shooter.autoShoot(m_cameraDistance);
        //        }
        if (Math.abs(m_operatorJoystick.getThrottle()) >= 0.3
            || Math.abs(m_operatorJoystick.getTwist()) >= 0.3) {
            m_shooter.topRollersForward();
        }
        System.out.println("Encoder Values:" + m_shooter.getEncoderRate());
    }

    @Override
    public boolean isFinished() {
        return !Camera.isConnected(); //return opposite so when it is connected
        //it returns false so it does NOT finish
    }

    @Override
    public void end(boolean interrupted) {
        if (!m_oi.areTopRollersOverriden()) {
            m_shooter.topRollersOff();
        }
        m_shooter.disablePID();
        m_shooter.stopEncoder();
    }


}
