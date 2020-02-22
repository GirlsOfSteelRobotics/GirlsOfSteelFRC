/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.lib;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * Add your docs here.
 */
public class NavXWrapper implements IGyroWrapper {

    private final AHRS m_NavX; 
    private double m_startingPosition; 

    public NavXWrapper() {
        m_NavX = new AHRS(SPI.Port.kMXP); 
        m_startingPosition = getYaw(); 
    }

    @Override
    public void poll() {
        SmartDashboard.putString("Firmware Version: ", m_NavX.getFirmwareVersion()); 
        SmartDashboard.putNumber("Byte Count: ", m_NavX.getByteCount());
        SmartDashboard.putNumber("Update Count: ", m_NavX.getUpdateCount()); 
        SmartDashboard.putBoolean("Connected ", m_NavX.isConnected()); 
        SmartDashboard.putBoolean("Calibrated ", m_NavX.isCalibrating()); 
        SmartDashboard.putNumber("Yaw ", m_NavX.getYaw()); 
        SmartDashboard.putNumber("Pitch ", m_NavX.getPitch()); 
        SmartDashboard.putNumber("Roll ", m_NavX.getRoll()); 
        SmartDashboard.putNumber("Angle ", m_NavX.getAngle()); 
    }

    @Override
    public double getYaw() {
        return  m_NavX.getPitch() - m_startingPosition; 
    }

    @Override
    public void setYaw(double angle) {
        m_startingPosition = m_NavX.getPitch() - angle;
    }

}
