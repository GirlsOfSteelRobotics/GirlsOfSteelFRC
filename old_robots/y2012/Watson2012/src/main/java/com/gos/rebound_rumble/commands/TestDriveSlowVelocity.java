package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.subsystems.Chassis;

public class TestDriveSlowVelocity extends CommandBase {

    private final Chassis m_chassis;
    private final Joystick m_driverJoystick;

    private double m_xAxis;
    private double m_yAxis;

    private double m_p;
    private double m_i;

    public TestDriveSlowVelocity(Chassis chassis, Joystick operatorJoystick) {
        m_chassis = chassis;
        this.m_driverJoystick = operatorJoystick;
        addRequirements(m_chassis);
        SmartDashboard.putNumber("Chassis P", 0.0);
        SmartDashboard.putNumber("Chassis I", 0.0);
    }

    @Override
    public void initialize() {
        m_chassis.initEncoders();
        m_chassis.initRatePIDs();
    }

    @Override
    public void execute() {
        m_p = SmartDashboard.getNumber("Chassis P", 0.0);
        m_i = SmartDashboard.getNumber("Chassis I", 0.0);
        m_chassis.setRatePIDValues(m_p, m_i, 0.0);
        m_xAxis = m_driverJoystick.getX();
        m_yAxis = m_driverJoystick.getY();
        m_chassis.driveVelocityLinear(m_xAxis, m_yAxis);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.disableRatePIDs();
        m_chassis.endEncoders();
        m_chassis.stopJags();
    }



}
