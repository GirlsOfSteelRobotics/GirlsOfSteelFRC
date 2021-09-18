/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author the programmers
 */
public class KickerUsingLimitSwitch extends CommandBase {

    private int loadingOrShooting;
    boolean isLoaded;
    boolean testingLimit = false;
    boolean testingMotor = false;
    boolean smartDashboard;
    private double startTime;
    private double changeInTime;

    public KickerUsingLimitSwitch(int position, boolean usingSD) //0 = loading; 1 = shooting
    {
        requires(kicker);
        loadingOrShooting = position;
        smartDashboard = usingSD;
    }

    protected void initialize() {
        if (!testingLimit) {
            if (smartDashboard) {
                SmartDashboard.putNumber("Position", -1);
            }
            if (loadingOrShooting == 1 && kicker.getLimitSwitch()) //the shooter is already loaded
            {
                isLoaded = true;
            }
            startTime = System.currentTimeMillis();
        }
        if (testingMotor) {
            SmartDashboard.putNumber("Turn Kicker Jag", 0.0);
        }
    }

    protected void execute() {
        if (!testingLimit) {
            changeInTime = System.currentTimeMillis() - startTime;
            if (smartDashboard) {
                loadingOrShooting = (int) SmartDashboard.getNumber("Position");
            }
            if (loadingOrShooting == 0) //loading
            {
                if (!kicker.getLimitSwitch()) {
                    //kicker.setJag(1.0);
                    kicker.setTalon(1.0);
                    System.out.println("In loading, sent 1 to the jag");
                } else {
                    System.out.println("Not sending a signal");
                }
            } else if (loadingOrShooting == 1) //Comment this out if using smart dashboard
            {
                if (kicker.getLimitSwitch()) {
                    //kicker.setJag(1.0);
                    kicker.setTalon(1.0);
                }
            }
        }
        if (testingMotor) {
            double motorSpeed = SmartDashboard.getNumber("Turn Kicker Jag");
            System.out.println("Sending: " + motorSpeed);
            //kicker.setJag(motorSpeed);
            kicker.setTalon(motorSpeed);
        }

        SmartDashboard.putBoolean("Limit Switch", kicker.getLimitSwitch());

    }

    protected boolean isFinished() {
        System.out.println("Is the limit hit: " + kicker.getLimitSwitch());
        if (!testingLimit) {
            if(changeInTime > 5000) {
                return true; //If more than 5 seconds have passed, stop trying! It probably means the battery is burned out... OR motor is burned out...
            }
            if (loadingOrShooting == 0) {
                return kicker.getLimitSwitch();
            } else if (loadingOrShooting == 1) {
                System.out.println("IS loaded " + isLoaded);
                //comment this out if using SmartDashboard
                if (!isLoaded && !smartDashboard) //trying to shoot but the shooter is not loaded
                {
                    return true;
                }
                return kicker.getLimitSwitch() == false;
            }
        }
        return false;
    }

    protected void end() {
        //kicker.stopJag();
        kicker.stopTalon();
    }

    protected void interrupted() {
        end();
    }

}
