/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.util.MathUtils;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
//  import edu.wpi.first.wpilibj.DriverStation;
//  import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.Joystick;
//  import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.addons.CANJaguar;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
//    DriverStation myDS = DriverStation.getInstance();
//    Dashboard myDash = myDS.getDashboardPackerLow();

    // drive wheels
    CANJaguar driveFL = new CANJaguar(3);
    CANJaguar driveRL = new CANJaguar(5);
    CANJaguar driveFR = new CANJaguar(2);
    CANJaguar driveRR = new CANJaguar(4);

    // possesion wheels
    CANJaguar possesL = new CANJaguar(6);
    CANJaguar possesR = new CANJaguar(7);

    // the kicker
    CANJaguar kicker = new CANJaguar(9, CANJaguar.ControlMode.kPercentVoltage);

    RobotDrive myDrive = new RobotDrive(driveFL, driveRL, driveFR, driveRR);

    // trigger control
    Solenoid resetTrigger = new Solenoid(8, 1);
    Solenoid fireTrigger = new Solenoid(8, 2);

    // control off-board compressor
//    Compressor myCompressor = new Compressor(1, 1);

    volatile double force = 0;
    volatile boolean fire = false;

    class DriverThread extends Thread {
        DriverThread(long notUsed) {
            this.setPriority(MAX_PRIORITY);
        }

        public void run() {
            while (true) {
                Watchdog.getInstance().feed();  // beat down the dog
                Timer.delay(0.01);
            }
        }
    }

    class KickerThread extends Thread {
        KickerThread(long notUsed) {
            this.setPriority(MIN_PRIORITY);
        }

        public void run() {
            while (!isSystemActive()) {
                Timer.delay(0.05);
            }

            Timer.delay(0.05);

            resetTrigger.set(true);
            Timer.delay(0.05);
            resetTrigger.set(false);

            possesL.set(-0.3);
            possesR.set(0.6);

            while (true) {
                while (kicker.getReverseLimitOK()) {
                    kicker.set(-1);  // reverse => grab
                    Timer.delay(0.05);
                }

                for (int i = 0; i < 70; i++) {
                    kicker.set(1);  // forward => draw
                    Timer.delay(0.05);
                }

                kicker.set(0);  // stop

                fire = false;
                while (!fire) {
                    kicker.set(0);  // stop
                    Timer.delay(0.05);
                }

                fireTrigger.set(true);
                Timer.delay(0.05);
                fireTrigger.set(false);

                Timer.delay(0.25);

                resetTrigger.set(true);
                Timer.delay(0.05);
                resetTrigger.set(false);

                Timer.delay(0.5);  // allow time for firing to not be on limit
            }
        }
    }

    class SmoothStick extends Joystick {
        SmoothStick(final int port) {
            super(port);
        }

        public double getX(Hand hand) {
            double x = super.getX(hand);

            if (Math.abs(x) < 0.10) x = 0;
            if (x > 0.90) x = 1;
            else if (x < -0.90) x = -1;
            return x;
        }

        public double getY(Hand hand) {
            double y = super.getY(hand);

            if (Math.abs(y) < 0.10) y = 0;
            if (y > 0.90) y = 1;
            else if (y < -0.90) y = -1;
            return y;
        }

        public double getTwist() {
            double twist = super.getTwist();

            if (Math.abs(twist) < 0.25) twist = 0;
            return MathUtils.pow(twist, 3);
        }
    }

    DriverThread myDriver = new DriverThread(0);
    KickerThread myKicker = new KickerThread(0);

    Joystick drivingJoystick = new SmoothStick(2);
    Joystick kickingJoystick = new SmoothStick(1);


    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        driveFL.configNeutralMode(CANJaguar.NeutralMode.kCoast);
        driveRL.configNeutralMode(CANJaguar.NeutralMode.kCoast);
        driveFR.configNeutralMode(CANJaguar.NeutralMode.kCoast);
        driveRR.configNeutralMode(CANJaguar.NeutralMode.kCoast);
        possesL.configNeutralMode(CANJaguar.NeutralMode.kBrake);
        possesR.configNeutralMode(CANJaguar.NeutralMode.kBrake);
        kicker.configNeutralMode(CANJaguar.NeutralMode.kBrake);

        myDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        myDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);

        drivingJoystick.setAxisChannel(Joystick.AxisType.kTwist, 4);
        drivingJoystick.setAxisChannel(Joystick.AxisType.kThrottle, 5);
        kickingJoystick.setAxisChannel(Joystick.AxisType.kTwist, 4);
        kickingJoystick.setAxisChannel(Joystick.AxisType.kThrottle, 5);

        myDriver.start();
        myKicker.start();

//        myCompressor.start();
    }

    Timer autonomousTimer = new Timer();
    boolean driveAutonomous = true;

    public void autonomousInit() {
        autonomousTimer.reset();
        autonomousTimer.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        double autonomousTimerValue = autonomousTimer.get() / 1000000;

        if(autonomousTimerValue > 0.4) {
            driveAutonomous = false;
        }

//        if(autonomousTimerValue > 9 && autonomousTimerValue < 12) {
//            force = 1;
//            fire = true;
//        }

//        if (driveAutonomous) myDrive.holonomicDrive(0.25, 0, 0);
//        else if (autonomousTimerValue < 5) myDrive.holonomicDrive(0, 0, 0);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        double magnitude = drivingJoystick.getMagnitude();
        double direction = drivingJoystick.getDirectionDegrees();
        double twist = drivingJoystick.getTwist();

        if (magnitude == 0 && twist == 0) {
            magnitude = kickingJoystick.getMagnitude() / 2;
            direction = kickingJoystick.getDirectionDegrees();
            twist = kickingJoystick.getTwist() / 2;
        }

        if (drivingJoystick.getButton(Joystick.ButtonType.kTrigger)
         || kickingJoystick.getButton(Joystick.ButtonType.kTrigger)) {
            force = kickingJoystick.getThrottle();
            fire = true;
        }

        myDrive.holonomicDrive(magnitude, direction, twist);
    }
}
