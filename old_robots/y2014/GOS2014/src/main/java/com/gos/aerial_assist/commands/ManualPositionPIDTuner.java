/**
 * A command to tune the chassis position PID.
 * <p>
 * <p>
 * DIFFERENT P'S ON THE PRACTICE BOT AND COMPETITION BOT
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.aerial_assist.subsystems.Chassis;
import com.gos.aerial_assist.subsystems.Driving;

public class ManualPositionPIDTuner extends GosCommand {
    private static final double OFF_BY = 0.05;
    private static final boolean PID = false;

    private final Chassis m_chassis;
    private double m_setpoint;
    private double m_p;
    private double m_i;
    private double m_d;
    private boolean m_reset;

    public ManualPositionPIDTuner(Chassis chassis, Driving driving) {
        m_chassis = chassis;
        addRequirements(driving);

    }

    @Override
    public void initialize() {
        m_chassis.initEncoders();
        m_chassis.resetEncoders();
        if (PID) {
            m_chassis.initPositionPIDS();
            m_chassis.resetPositionPIDError();
            SmartDashboard.putNumber("Chassis Position setpoint", 0);
            SmartDashboard.putNumber("Position P: ", 0);
            SmartDashboard.putNumber("Position I: ", 0);
            SmartDashboard.putNumber("Position D: ", 0);
        }
        SmartDashboard.putBoolean("Resetencoder", false);
    }

    @Override
    public void execute() {
        if (PID) {
            m_setpoint = SmartDashboard.getNumber("Chassis Position setpoint", 0);
            m_p = SmartDashboard.getNumber("Position P: ", 0);
            m_i = SmartDashboard.getNumber("Position I: ", 0);
            m_d = SmartDashboard.getNumber("Position D: ", 0);
        }

        m_reset = SmartDashboard.getBoolean("Resetencoder", false);

        if (m_reset) {
            m_chassis.resetEncoders();
        }

        SmartDashboard.putNumber("leftencoder chassis", m_chassis.getLeftEncoderDistance());
        SmartDashboard.putNumber("Left encoder rate", m_chassis.getLeftEncoderRate());
        SmartDashboard.putNumber("Left Encoder Get Chassis", m_chassis.getLeftEncoder());
        SmartDashboard.putNumber("Left Encoder Get Raw", m_chassis.getLeftRaw());
        SmartDashboard.putNumber("Right Encoder Get Raw", m_chassis.getRightRaw());
        SmartDashboard.putNumber("Right Encoder Get Chassis", m_chassis.getRightEncoder());
        SmartDashboard.putNumber("rightencoder chassis", m_chassis.getRightEncoderDistance());
        SmartDashboard.putNumber("right encoder rate", m_chassis.getRightEncoderRate());
        SmartDashboard.putNumber("Right encoder rate chassis", m_chassis.getRateRightEncoder());
        SmartDashboard.putNumber("Left encoder rate chassis", m_chassis.getRateLeftEncoder());

        System.out.println(m_chassis.getRightEncoderRate() + "\t" + m_chassis.getLeftEncoderRate());

        System.out.println("Get left chassis raw: " + m_chassis.getLeftRaw());
        System.out.println("Get right chassis raw: " + m_chassis.getRightRaw());

        if (PID && m_p != 0 && m_setpoint != 0) {
            System.out.println("Here ---------------------------------");
            m_chassis.setLeftPositionPIDValues(m_p, m_i, m_d);
            m_chassis.setRightPositionPIDValues(m_p, m_i, m_d);
            m_chassis.setPositionSeparate(m_setpoint, m_setpoint);

        }
    }

    @Override
    public boolean isFinished() {
        boolean finished = (Math.abs((m_chassis.getLeftEncoderDistance() - m_setpoint)) < OFF_BY || Math.abs((m_chassis.getRightEncoderDistance() - m_setpoint)) < OFF_BY) && (m_setpoint != 0);
        System.out.println("Position PID is finished: " + finished);
        return finished;
    }

    @Override
    public void end(boolean interrupted) {
        if (PID) {
            m_chassis.disablePositionPID();
        }
    }


}
