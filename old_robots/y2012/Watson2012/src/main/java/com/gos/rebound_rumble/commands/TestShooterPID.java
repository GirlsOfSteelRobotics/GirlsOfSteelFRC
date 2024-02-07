package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.subsystems.Shooter;

public class TestShooterPID extends GosCommandBaseBase {

    private final Shooter m_shooter;

    public TestShooterPID(Shooter shooter) {
        m_shooter = shooter;
        addRequirements(m_shooter);
        SmartDashboard.putNumber("Shooter Setpoint", 0.0);
        SmartDashboard.putNumber("Shooter,p", 0.0);
        SmartDashboard.putNumber("Shooter,i", 0.0);
        //        SmartDashboard.putNumber("Shooter,d", 0.0);
        //        SmartDashboard.putBoolean("Auto Shoot?", false);
        //        SmartDashboard.putBoolean("Bank?", false);
        SmartDashboard.putNumber("Shooter Encoder", m_shooter.getEncoderRate());
    }

    @Override
    public void initialize() {
        m_shooter.initEncoder();
        m_shooter.initPID();
    }

    @Override
    public void execute() {
        //        autoShoot = SmartDashboard.getBoolean("Auto Shoot?", false);
        //        bank = SmartDashboard.getBoolean("Bank?", false);
        //        SmartDashboard.putNumber("Shooter Encoder", shooter.getEncoderRate());
        //        if(!autoShoot){
        double kp = SmartDashboard.getNumber("Shooter,p", 0.0);
        double ki = SmartDashboard.getNumber("Shooter,i", 0.0);
        //            d = SmartDashboard.getNumber("Shooter,d",0.0);
        m_shooter.setPIDValues(kp, ki, 0.0);
        double setpoint = SmartDashboard.getNumber("Shooter Setpoint", 0.0);
        m_shooter.setPIDSpeed(setpoint);
        //        }else{
        //            if(bank){
        //                shooter.autoShootBank();
        //            }else{
        //                shooter.autoShoot();
        //            }
        //        }
        SmartDashboard.putNumber("Shooter Encoder", m_shooter.getEncoderRate());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.disablePID();
        m_shooter.stopEncoder();
        m_shooter.stopJags();
    }



}
