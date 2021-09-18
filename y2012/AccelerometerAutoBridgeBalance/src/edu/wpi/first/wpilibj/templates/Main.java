/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Main extends IterativeRobot {

    ADXL345_I2C accel = new ADXL345_I2C(1, ADXL345_I2C.DataFormat_Range.k2G);
    Jaguar left1 = new Jaguar(1);
    Jaguar right1 = new Jaguar(3);
    Jaguar left2 = new Jaguar(2);
    Jaguar right2 = new Jaguar(4);
    Joystick joystickWatson = new Joystick(1);
    RobotDrive drive = new RobotDrive(left1, left2, right2, right1);

    public void robotInit() {
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        double accX = accel.getAcceleration(ADXL345_I2C.Axes.kX);
        double accY = accel.getAcceleration(ADXL345_I2C.Axes.kY);
        double accZ = accel.getAcceleration(ADXL345_I2C.Axes.kZ);
        System.out.println(accX + "\t" + accY + "\t" + accZ);
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 2, "x:" + accX);
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 2, "y:" + accY);
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser4, 2, "z:" + accZ);
        DriverStationLCD.getInstance().updateLCD();
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser5, 2, "Motor Speed: " + left1.get());




        double zvalue = accel.getAcceleration(ADXL345_I2C.Axes.kZ);

        if (joystickWatson.getRawButton(1)) {
            if (zvalue < .98) {
                zvalue = accel.getAcceleration(ADXL345_I2C.Axes.kZ);
                double yvalue = accel.getAcceleration(ADXL345_I2C.Axes.kY);
                if (yvalue > 0) {
                    left1.set(-.6);
                    right1.set(.6);
                    left2.set(-.6);
                    right2.set(.6);
                }else {
                    left1.set(.6);
                    right1.set(-.6);
                    left2.set(.6);
                    right2.set(-.6);
                }
            }
            else{
                left1.set(0);
                right1.set(0);
                left2.set(0);
                right2.set(0);
            }

        }
        else{
            /*left1.set(0);
            right1.set(0);
            left2.set(0);
            right2.set(0);*/
            drive.arcadeDrive(joystickWatson);
        }
    }
}
