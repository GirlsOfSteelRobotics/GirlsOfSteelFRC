package com.gos.power_up.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.subsystems.Blobs;
import com.gos.power_up.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class DriveByVision extends CommandBase {
    private static final double SPEED_PERCENT = 0.2;

    private final Chassis m_chassis;
    private final Blobs m_blobs;
    private final WPI_TalonSRX m_leftTalon;
    private final WPI_TalonSRX m_rightTalon;

    private double m_dist;

    public DriveByVision(Chassis chassis, Blobs blobs) {
        // Use requires() here to declare subsystem dependencies
        m_chassis = chassis;
        m_blobs = blobs;
        m_leftTalon = m_chassis.getLeftTalon();
        m_rightTalon = m_chassis.getRightTalon();
        addRequirements(m_blobs);
    }


    @Override
    public void initialize() {
        m_dist = m_blobs.distanceBetweenBlobs();
        if (m_blobs.distanceBetweenBlobs() == -1) {
            System.out.println("DriveByVision initialize: line not in sight!!");
            end();
        }
    }


    @Override
    public void execute() {
        m_dist = m_blobs.distanceBetweenBlobs();
        System.out.print("Distance = " + m_dist + " ");
        if (m_dist == -1) {
            System.out.println("DriveByVision: Can't see line!");
        } else if (m_dist < Blobs.GOAL_DISTANCE) {  //too far --> go forward
            System.out.println("DriveByVision: driving forward");
            m_leftTalon.set(ControlMode.PercentOutput, SPEED_PERCENT);
            m_rightTalon.set(ControlMode.PercentOutput, -SPEED_PERCENT);
        } else if (m_dist > Blobs.GOAL_DISTANCE) {  //too close --> go backward
            System.out.println("DriveByVision: driving backard");
            m_leftTalon.set(ControlMode.PercentOutput, -SPEED_PERCENT);
            m_rightTalon.set(ControlMode.PercentOutput, SPEED_PERCENT);
        }
    }


    @Override
    public boolean isFinished() {
        return m_dist == -1 || Math.abs(m_dist - Blobs.GOAL_DISTANCE) < Blobs.ERROR_THRESHOLD;
    }


    @Override
    public void end(boolean interrupted) {
        m_chassis.stop();
    }


}
