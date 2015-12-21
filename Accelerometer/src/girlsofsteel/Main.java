/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package girlsofsteel;

import edu.wpi.first.wpilibj.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Main extends IterativeRobot {
    ADXL345_I2C accel;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        
         ADXL345_I2C.DataFormat_Range range = ADXL345_I2C.DataFormat_Range.k2G;
         accel = new ADXL345_I2C(1, range);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }

    /**
     * This function is called periodically during operator control
     */
    int counter = 0;
    public void teleopPeriodic() {
        System.out.println(Accelerometer());
         DriverStationLCD.getInstance().println(DriverStationLCD.Line.kMain6, 2, "c:" + counter++);
         DriverStationLCD.getInstance().updateLCD();

    }
    //int channel = 1;


    public double Accelerometer() {

        double accX = accel.getAcceleration(ADXL345_I2C.Axes.kX);
        double accY = accel.getAcceleration(ADXL345_I2C.Axes.kY);
        double accZ = accel.getAcceleration(ADXL345_I2C.Axes.kZ);
        System.out.println(accX + "\t" + accY + "\t" + accZ);
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 2, "x:" + accX);
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 2, "y:" + accY);
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser4, 2, "z:" + accZ);
        return 0;
    }
}
