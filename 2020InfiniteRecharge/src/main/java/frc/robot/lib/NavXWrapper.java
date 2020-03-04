/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.lib;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * Add your docs here.
 */
public class NavXWrapper implements IGyroWrapper {

    private final AHRS m_navX;
    private double m_startingPosition; 

    public NavXWrapper() {
        m_navX = new AHRS(SPI.Port.kMXP);
        m_startingPosition = getYaw(); 
    }

    @Override
    public void poll() {
        SmartDashboard.putNumber("RawAngle ", m_navX.getAngle());
        SmartDashboard.putNumber("Yaw ", m_navX.getYaw());
        SmartDashboard.putNumber("Pitch ", m_navX.getPitch());
        SmartDashboard.putNumber("Roll ", m_navX.getRoll());
    }

    @Override
    public double getYaw() {
        return  m_navX.getYaw() - m_startingPosition;
    }

    @Override
    public void setYaw(double angle) {
        m_startingPosition = m_navX.getYaw() - angle;
    }

}
