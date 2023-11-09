package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.subsystems.Chassis;

public class DriveVelocityChassisTuning extends GosCommand {
    private final Chassis m_chassis;

    public DriveVelocityChassisTuning(Chassis chassis) {
        m_chassis = chassis;
        SmartDashboard.putNumber("DVCT,p", 0.0);
        SmartDashboard.putNumber("DVCT,i", 0.0);
        // SmartDashboard.putNumber("setpoint", 0.0);
        SmartDashboard.putNumber("encoder rate right", 0.0);
        SmartDashboard.putNumber("encoder rate left", 0.0);
        addRequirements(m_chassis);
    }

    @Override
    public void initialize() {
        m_chassis.initEncoders();
        m_chassis.initRatePIDs();
    }

    @Override
    public void execute() {
        m_chassis.setRatePIDValues(SmartDashboard.getNumber("DVCT,p", 0.0), SmartDashboard.getNumber("DVCT,i", 0.0), 0.0);
        m_chassis.setRatePIDSetPoint(/*joystick.getY());*/SmartDashboard.getNumber("setpoint", 0.0));
        SmartDashboard.putNumber("encoder rate right", m_chassis.getRightEncoderRate());
        SmartDashboard.putNumber("encoder rate left", m_chassis.getLeftEncoderRate());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.disableRatePIDs();
        m_chassis.endEncoders();
    }


}
