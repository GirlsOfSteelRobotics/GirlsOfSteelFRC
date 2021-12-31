/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.cscore.AxisCamera;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class robotcomtrol extends SimpleRobot {
    Joystick joy;
    Joystick armstick;
    Joystick stick;
    DriverStationLCD message;
    Compressor compy;
    Solenoid ClawO;
    Solenoid ClawC;
    Solenoid MBDeplo;
    Solenoid MBReverse;
    CANJaguar Shol1;
    CANJaguar Shol2;
    CANJaguar Elbow;
    CANJaguar FrontLeft;
    CANJaguar FrontRight;
    CANJaguar RearLeft;
    CANJaguar RearRight;
    RobotDrive Move;
    boolean isforwardlimitok;
    double Sholval;
    double Elbowval;
    double Pdes = 0.0;
    double Sholp = 1000.0;
    double Sholi = 0.0;
    double Shold = 0.0;
    double Elbp = -0.01;
    double Elbi = 0.0;
    double Elbd = 0.0;
    AxisCamera Footage;
    /*PIDController SholPID;
     PIDController ElbPID;
     Encoder SholEncoder;
     Encoder ElbEncoder;
     double distancepulse = 1.0/360.0;
     double min = 0.0;
     double max = 1.0;*/


    /**
     * This function is the constructor, called once
     * everytime the class is instantiated.
     */
    public robotcomtrol() {

        message = DriverStationLCD.getInstance();
        joy = new Joystick(1);
        armstick = new Joystick(2);
        stick = new Joystick(3);
        compy = new Compressor(4, 6, 4, 7);
        ClawO = new Solenoid(8, 1);
        ClawC = new Solenoid(8, 2);
        MBDeplo = new Solenoid(8, 3);
        MBReverse = new Solenoid(8, 4);
        isforwardlimitok = true;
        Footage = AxisCamera.getInstance();

        compy.start();

        try {
            Shol1 = new CANJaguar(2, CANJaguar.ControlMode.kVoltage);
            Shol1.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
            Shol1.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
            Shol1.configEncoderCodesPerRev(360);
            Shol1.enableControl();
        } catch (CANTimeoutException ex) {
            System.err.println("The first one in the constutor");

            message.println(DriverStationLCD.Line.kUser6, 1, "ERROR IN JAG2-shol1");
            message.updateLCD();
            ex.printStackTrace();
        }

        try {
            Shol2 = new CANJaguar(3, CANJaguar.ControlMode.kVoltage);
        } catch (CANTimeoutException ex) {
            System.err.println("The first one in the constutor");

            message.println(DriverStationLCD.Line.kUser6, 1, "ERROR IN JAG3-shol2");
            message.updateLCD();
            ex.printStackTrace();
        }

        try {
            Elbow = new CANJaguar(4, CANJaguar.ControlMode.kVoltage);
            Elbow.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
            Elbow.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
            Elbow.configEncoderCodesPerRev(360);
            Elbow.enableControl();
        } catch (CANTimeoutException ex) {
            System.err.println("The first one in the constutor");

            message.println(DriverStationLCD.Line.kUser6, 1, "ERROR IN JAG4-elbow");
            message.updateLCD();
            ex.printStackTrace();
        }

        try {
            FrontLeft = new CANJaguar(5);
            FrontRight = new CANJaguar(6);
            RearLeft = new CANJaguar(7);
            RearRight = new CANJaguar(9);
        } catch (CANTimeoutException ex) {
            System.err.println("The first one in the constutor");

            message.println(DriverStationLCD.Line.kUser6, 1, "ERROR IN JAGS 5-9");
            message.updateLCD();
            ex.printStackTrace();
        }

        /*SholEncoder = new Encoder (slotA, channelA, slotB, channelB, true, CounterBase.EncodingType.k4X);
         *SholEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
         *SholPID = new PIDController (Sholp, Sholi, Shold, SholEncoder, insertjaghere);
         *SholEncoder.setDistancePerPulse(distancepulse);
         *ElbEncoder = new Encoder (slotA, channelA, slotB, channelB, true, CounterBase.EncodingType.k4X);
         *ElbEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
         *ElbPID = new PIDController (Elbp, Elbi, Elbd, ElbEncoder, insertjaghere);
         *ElbEncoder.setDistancePerPulse(distancepulse);
         */

        /*try {
            RearLeft.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
            RearLeft.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
            RearLeft.configEncoderCodesPerRev(360);
            RearLeft.setPID(leftp, lefti, leftd);
            RearLeft.enableControl();
            RearRight.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
            RearRight.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
            RearRight.configEncoderCodesPerRev(360);
            RearRight.setPID(rightp, righti, rightd);
            RearRight.enableControl();
        } catch (CANTimeoutException ex) {
            System.err.println("The first one in the constutor");

            message.println(DriverStationLCD.Line.kUser6, 1, "ERROR IN ENCODERS");
            message.updateLCD();
            ex.printStackTrace();
        }*/

        //Move = new RobotDrive(FrontLeft, RearLeft, FrontRight, RearRight);

        System.err.println("DOWNLOADED!!!");
        System.out.println("Did the birdie attack you?");
        try {
            //this sets neutral mode for all of the motors

            FrontLeft.setNeutralMode(NeutralMode.kBrake);
            FrontRight.setNeutralMode(NeutralMode.kBrake);
            RearLeft.setNeutralMode(NeutralMode.kBrake);
            RearRight.setNeutralMode(NeutralMode.kBrake);
            Shol1.setNeutralMode(NeutralMode.kBrake);
            Shol2.setNeutralMode(NeutralMode.kCoast);
            Elbow.setNeutralMode(NeutralMode.kBrake);

        } catch (CANTimeoutException ex) {
            message.println(DriverStationLCD.Line.kUser6, 1, "ERROR sets nuetral");
            message.updateLCD();
            ex.printStackTrace();
        }

        /*
         * SholPID.enable();
         * ElbPID.enable();
         */

    }

    public void autonomous() {
        double starttime = ((double) System.currentTimeMillis() / 1000.0);
        try {
            RearLeft.changeControlMode(WPI_TalonSRX.TalonControlMode.kVoltage);
            RearLeft.enableControl();
            RearRight.changeControlMode(WPI_TalonSRX.TalonControlMode.kVoltage);
            RearRight.enableControl();
            FrontLeft.changeControlMode(WPI_TalonSRX.TalonControlMode.kVoltage);
            FrontLeft.enableControl();
            FrontRight.changeControlMode(WPI_TalonSRX.TalonControlMode.kVoltage);
            FrontRight.enableControl();
            Shol1.changeControlMode(WPI_TalonSRX.TalonControlMode.kVoltage);
            Shol1.enableControl();
            Shol2.changeControlMode(WPI_TalonSRX.TalonControlMode.kVoltage);
            Shol2.enableControl();
        } catch (CANTimeoutException ex) {

            ex.printStackTrace();
        }
        double drivevoltage = 5;
        double forwardtime = 4.3;
        double armvoltage = -6;
        double armaddedtime = 5;
        double elbowval = -6;
        double elbowaddedtime = 1.6;
        double clawtime = .99;
        double backvoltage = 6;
        double backtime = 1.25;

        ClawC.set(true);

        while (isAutonomous()) {
            getWatchdog().feed();
            Timer.delay(.01);
            double nowtime = ((double) System.currentTimeMillis() / 1000.0);

            if (nowtime < starttime + armaddedtime) {
                //move shol up
                try {
                    Shol1.setX(armvoltage);
                    Shol2.setX(Shol1.getOutputVoltage());
                } catch (CANTimeoutException ex) {
                    ex.printStackTrace();
                }
                System.out.println("SHOL UP");
            } else if (nowtime <= starttime + armaddedtime + elbowaddedtime) {
                //move elbow up
                try {
                    Shol1.setX(0);
                    Shol2.setX(Shol1.getOutputVoltage());
                    Elbow.setX(elbowval);
                } catch (CANTimeoutException ex) {
                    ex.printStackTrace();
                }
                System.out.println("ELBOW");

            } else if (nowtime <= starttime + armaddedtime + elbowaddedtime + forwardtime) {
                //drive forward
                try {
                    Elbow.setX(0);
                    RearLeft.setX(-drivevoltage);
                    RearRight.setX(drivevoltage);
                    FrontLeft.setX(-drivevoltage);
                    FrontRight.setX(drivevoltage);
                    System.out.println("FORWARD");
                } catch (CANTimeoutException ex) {
                    ex.printStackTrace();
                }
            } else if (nowtime <= starttime + armaddedtime + elbowaddedtime + forwardtime + clawtime) {
                try {
                    //open claw
                    RearLeft.setX(0);
                    RearRight.setX(0);
                    FrontLeft.setX(0);
                    FrontRight.setX(0);
                    ClawO.set(true);
                    //move backward
                    System.out.println("CLAW AND SIT");
                } catch (CANTimeoutException ex) {
                    ex.printStackTrace();
                }
            } else if (nowtime <= starttime + armaddedtime + elbowaddedtime + forwardtime + clawtime + backtime) {
                try {
                    RearLeft.setX(backvoltage);
                    RearRight.setX(-backvoltage);
                    FrontLeft.setX(backvoltage);
                    FrontRight.setX(-backvoltage);
                    System.out.println("BACKWARD BACKWARD BACKWARD");
                } catch (CANTimeoutException ex) {
                    ex.printStackTrace();
                }

            } else {
                try {
                    Elbow.setX(0);
                    Shol1.setX(0);
                    Shol2.setX(Shol1.getOutputVoltage());
                    RearLeft.setX(0);
                    RearRight.setX(0);
                    FrontLeft.setX(0);
                    FrontRight.setX(0);
                    System.out.println("REST REST REST");
                } catch (CANTimeoutException ex) {
                    ex.printStackTrace();
                }
                ClawO.set(false);

            }
        }
    }


    /*
     *place on line in order to score on middle peg of either board
     *drive forward 14.5 feet
     *raise arm to second position (above peg)
     * move forward 1.5 feet
     * release claw
     * move backward slowly (one for two feet)
     *
     */

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        try {
            RearLeft.changeControlMode(WPI_TalonSRX.TalonControlMode.kVoltage);
            RearLeft.enableControl();
            RearRight.changeControlMode(WPI_TalonSRX.TalonControlMode.kVoltage);
            RearRight.enableControl();
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        double prevtime = ((double) System.currentTimeMillis() / 1000.0);
        double currenttime = ((double) System.currentTimeMillis() / 1000.0);
        double diff = 0.0;
        boolean flag = true;

        int val = 0;
        try {
            Shol1.changeControlMode(WPI_TalonSRX.TalonControlMode.kVoltage);
            Shol1.enableControl();

        } catch (CANTimeoutException ex) {
            message.println(DriverStationLCD.Line.kUser6, 1, "ERROR PID");
            message.updateLCD();
            ex.printStackTrace();
        }
        while (isOperatorControl()) {//main while loop for teleop control
            getWatchdog().feed();
            Timer.delay(.01);
            val++;
            message.println(DriverStationLCD.Line.kMain6, 1, "this " + Integer.toString(val));
            message.updateLCD();
/*            try {
            isforwardlimitok = Shol1.getForwardLimitOK();
            } catch (CANTimeoutException ex) {
            ex.printStackTrace();
            }
*/

            /*System.out.println("setpoint is: " + SholPID.getSetpoint());
             * double currErr = Math.abs(SholPID.getError());
             * System.out.println("error is: " + SholPID.getError());
             * System.out.println("setpoint is: " + ElbPID.getSetpoint());
             * double currErr = Math.abs(ElbPID.getError());
             * System.out.println("error is: " + ElbPID.getError());
             */

            currenttime = ((double) System.currentTimeMillis() / 1000.0);
            if (flag)//only hits on first time
            {
                //dont change diff the first time
                flag = false;
            } else {

                diff = currenttime - prevtime;
                // message.println(DriverStationLCD.Line.kUser6, 1, Double.toString(diff));
            }

            //this is for arcade drving

            //Move.arcadeDrive(joy);

            double Xaxis = joy.getAxis(Joystick.AxisType.kX);
            double Yaxis = joy.getAxis(Joystick.AxisType.kY);

            if (joy.getZ() >= .5) {//z is down turning slower
                Xaxis = joy.getAxis(Joystick.AxisType.kX) * 0.5;
                Yaxis = joy.getAxis(Joystick.AxisType.kY) * 0.5;
            } else if (joy.getZ() <= -.5) { // z is up
                Xaxis = joy.getAxis(Joystick.AxisType.kX);
            }
            Timer.delay(0.01);

            myArcade(deadzonedrive(-Xaxis), deadzone(-Yaxis));
            //if it is backwards on both -> flip the sign
            //if the axes are opposite -> change the order in which they are passed in

            Timer.delay(0.01);
            //this is for tank drive

            //Move.tankDrive(joy, stick);

            Buttonies();

            /*int raw = rightencoder.getRaw();
            double distance = rightencoder.getDistance();
            int get = rightencoder.get();
            double rate = rightencoder.getRate();
            message.println(DriverStationLCD.Line.kUser2, 1, "RAW" + raw);
            message.println(DriverStationLCD.Line.kUser3, 1, "DIS " + distance);
            message.println(DriverStationLCD.Line.kUser4, 1, "RATE " + rate);
            message.println(DriverStationLCD.Line.kUser6, 1, "GET " + get);
            message.updateLCD();
            */

            Timer.delay(0.01);

            slavevoltage(deadzone(armstick.getY()) * 12);

            Timer.delay(0.01);
            //multiplier can go up to 24
            double valElbow = -.75 * deadzone(stick.getY());
            if (valElbow > 0) {
                valElbow = valElbow * 12.0;
            } else {
                valElbow = valElbow * 12.0;

            }
            try {
                Elbow.setX(valElbow);
                //message.println(DriverStationLCD.Line.kUser4, 1, "YShol " + Double.toString(valShol));
                //message.println(DriverStationLCD.Line.kUser5, 1, "Xelbo " + Double.toString(valElbow));
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            prevtime = ((double) System.currentTimeMillis() / 1000.0);
        }//end while
    }

    /**
     * this function sets a deadzone based on the 'dzconstant'.
     * it takes axis values from the joystick and returns
     * zero when in the deadzone and the joystick value otherwise.
     *
     * @param axisvalue -- value from joystick
     * @return zero if deadzone, joystick val otherwise
     */
    public double deadzone(double axisvalue) {
        double retval = 0.0;
        final double dzconstant = 0.15;
        if (axisvalue > dzconstant) {
            retval = (axisvalue - dzconstant) * (1 / (1 - dzconstant));
        } else if (axisvalue < (-1 * dzconstant)) {
            retval = (axisvalue + dzconstant) * (1 / (1 - dzconstant));
        } else {
            retval = 0;
        }
        //this method returns zero if it is within the deadzone,
        //dead zone is defined between positive and negative dzconstant
        return retval;
    }

    public double deadzonedrive(double axisvalue) {
        double retval = 0.0;
        final double dzconstant = 0.1;
        if (axisvalue > dzconstant) {
            retval = (axisvalue - dzconstant) * (1 / (1 - dzconstant));
        } else if (axisvalue < (-1 * dzconstant)) {
            retval = (axisvalue + dzconstant) * (1 / (1 - dzconstant));
        } else {
            retval = 0;
        }
        //this method returns zero if it is within the deadzone,
        //dead zone is defined between positive and negative dzconstant
        return retval;
    }

    // This function clears the display on the classmate
    public void cleanslate() {
        message.println(DriverStationLCD.Line.kUser2, 1, "                             ");
        message.println(DriverStationLCD.Line.kUser3, 1, "                             ");
        message.println(DriverStationLCD.Line.kUser4, 1, "                             ");
        message.println(DriverStationLCD.Line.kUser5, 1, "                             ");
        message.println(DriverStationLCD.Line.kUser6, 1, "                             ");
        message.updateLCD();
    }

    /**
     * This function sets the shoulder to a specific voltage.
     * It changes the shoulder jag to voltage mode if it
     * wasn't previously.
     *
     * @param voltval, voltage to drive arm at
     */
    public void slavevoltage(double voltval) {
        //all the time just driving
        try {

            if (Shol1.getControlMode().equals(CANJaguar.ControlMode.kVoltage)) {
                Shol1.setX(voltval);
                setshol2(voltval);
            } else {
                Shol1.changeControlMode(WPI_TalonSRX.TalonControlMode.kVoltage);
                Shol1.enableControl();
            }
        } catch (CANTimeoutException ex) {
            message.println(DriverStationLCD.Line.kUser6, 1, "ERROR SLAVE VOLTAGE");
            message.updateLCD();
            ex.printStackTrace();
        }
    }

    /**
     * limit statement to set limits as 1.0 & -1.0
     *
     * @param num axis values
     * @return
     */
    static double limit(double num) {
        if (num > 1.0) {
            return 1.0;
        }
        if (num < -1.0) {
            return -1.0;
        }
        return num;
    }

    /**
     * Function is our own version of arcade drive.
     * The yaxis will be used for going straight and backwards,
     * and the xaxis will be used for turning.
     *
     * @param yaxis, move axis
     * @param xaxis, turn axis
     */
    public void myArcade(double yaxis, double xaxis) {
        boolean squaring = true;
        double scalevolt = 12;
        double scalespeed = 30;

        yaxis = limit(yaxis);
        xaxis = limit(xaxis);

        if (squaring) {
            if (xaxis > 0) {
                xaxis = xaxis * xaxis;
            } else {
                xaxis = xaxis * xaxis * -1.0;
            }
            if (yaxis > 0) {
                yaxis = yaxis * yaxis;
            } else {
                yaxis = yaxis * yaxis * -1.0;
            }
        }

        setMotSpe(yaxis, xaxis, scalevolt);
        message.updateLCD();


    }

    /**
     * This function is used for position control of the shoulder.
     * It changes the position of the shoulder based on a linear
     * function from the joystick. If the joystick is in the
     * deadzone the value does not change. Outside of the deadzone
     * the position will change linearly to decrement or increment
     * the values as necessary.
     *
     * @param yaxis, axis of joystick to scale off of
     * @param dt,    change in time of the while loop that calls this function
     * @return the current position think are at
     */
    public double slavelinear(double yaxis, double dt) {
        double maxrpm = 10;
        double maxrps = maxrpm / 60;
        double deadzone = .3;
        double vdes = 0;
        double returnval = 909090909;

        if (yaxis > deadzone) {
            vdes = (yaxis - deadzone) * (maxrps / (1 - deadzone));


        } else if (yaxis < (-1 * deadzone)) {


            vdes = (yaxis + deadzone) * (maxrps / (1 - deadzone));


        } else {
            vdes = 0;

        }
        try {
            if (Shol1.getControlMode().equals(CANJaguar.ControlMode.kPosition)) {
                Pdes += vdes * dt;
                Shol1.setX(Pdes);
                double volt = Shol1.getOutputVoltage();
                message.println(DriverStationLCD.Line.kUser4, 1, "vdes " + Double.toString(vdes));
                message.println(DriverStationLCD.Line.kUser3, 1, "DT" + Double.toString(dt));
                setshol2(volt);

                message.println(DriverStationLCD.Line.kUser6, 1, Double.toString(Pdes));
            } else {

                Pdes = 0.0;//reset Pdes anytime encoder resets
                Shol1.changeControlMode(WPI_TalonSRX.TalonControlMode.kPosition);
                Shol1.setPID(Sholp, Sholi, Shold);
                Shol1.enableControl();
            }


            returnval = Shol1.getPosition();

        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        return returnval;
    }

    /**
     * Function is used for the hardware limit switch.
     * The switch tells the code if it's ok to move the arm
     * forwards. In this case forwards means moving the arm
     * in the downward direction. This function stops
     * the slave motor of the shoulder from turning in the downward
     * position of the limit switch has been triggered.
     * <p>
     * This is the function that should now be called to change
     * the voltage of Shol2 rather than setting it directly.
     *
     * @param x, value to set Shol2 in setX()
     */
    public void setshol2(double x) {
        if (isforwardlimitok) {
            try {
                Shol2.setX(x);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
        } else {
            if (x > 0) {
                try {
                    Shol2.setX(0);
                } catch (CANTimeoutException ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    Shol2.setX(x);
                } catch (CANTimeoutException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void setMotSpe(double yaxis, double xaxis, double scale) {
        double leftMotorSpeed = 0.0;
        double rightMotorSpeed = 0.0;

        if (yaxis > 0.0) {
            if (xaxis > 0.0) {
                leftMotorSpeed = yaxis - xaxis;
                rightMotorSpeed = Math.max(yaxis, xaxis);
            } else {
                leftMotorSpeed = Math.max(yaxis, -xaxis);
                rightMotorSpeed = yaxis + xaxis;
            }
        } else {
            if (xaxis > 0.0) {
                leftMotorSpeed = -Math.max(-yaxis, xaxis);
                rightMotorSpeed = yaxis + xaxis;
            } else {
                leftMotorSpeed = yaxis - xaxis;
                rightMotorSpeed = -Math.max(-yaxis, -xaxis);
            }
        }

        leftMotorSpeed = leftMotorSpeed * scale;
        rightMotorSpeed = rightMotorSpeed * scale;


        try {
            RearLeft.setX(leftMotorSpeed);
            RearRight.setX(rightMotorSpeed);
            double voltL = RearLeft.getOutputVoltage();
            FrontLeft.setX(voltL);
            double voltR = RearRight.getOutputVoltage();
            FrontRight.setX(voltR);
        } catch (CANTimeoutException ex) {
            System.out.println("set mot speed");
            ex.printStackTrace();

        }

        // message.println(DriverStationLCD.Line.kUser5, 1, "RDS: " + Double.toString(rightMotorSpeed));
        message.updateLCD();
    }

    public void Buttonies() {
        double rest = 0.0; //0 on joystick
        double slot = 0.0; //button eight
        double bottomracksides = 0.0; //button nine
        double bottomrackmiddle = 0.0; //button ten
        double middleracksides = 0.0; //button eleven
        double middlerackmiddle = 0.0;
        //this is display clear and claw
        if (armstick.getTrigger()) {
            cleanslate();
        }
        if (armstick.getRawButton(2)) {
            ClawO.set(true);
            message.println(DriverStationLCD.Line.kUser4, 5, "Claw Open ");

        } else {
            ClawO.set((false));
        }

        if (stick.getRawButton(2)) {
            ClawC.set(true);
            message.println(DriverStationLCD.Line.kUser4, 5, "Claw Close");

        } else {
            ClawC.set((false));
        }

        if (joy.getRawButton(3)) {
            MBDeplo.set(true);
            message.println(DriverStationLCD.Line.kUser4, 5, "Mini Bot Deployed");
        } else {
            MBDeplo.set(false);
        }

        if (joy.getRawButton(5)) {
            MBReverse.set(true);
            message.println(DriverStationLCD.Line.kUser4, 5, "Mini Bot Retracted");
        } else {
            MBReverse.set(false);
        }

        if (armstick.getRawButton(9)) {
            //double print = matchmaker(slot);
            //print is the value it moved it
            //message.println(DriverStationLCD.Line.kUser2, 1, Double.toString(slot));
            message.println(DriverStationLCD.Line.kUser3, 1, "Feeder");

        }
        if (armstick.getRawButton(10)) {
            //double print = matchmaker(bottomracksides);
            //print is the value it moved it
            message.println(DriverStationLCD.Line.kUser2, 1, Double.toString(bottomracksides));
            message.println(DriverStationLCD.Line.kUser3, 1, "Bottom Rack Sides");

        }
        if (armstick.getRawButton(6)) {

            //double print = matchmaker(bottomrackmiddle);
            //print is the value it moved it
            message.println(DriverStationLCD.Line.kUser2, 1, Double.toString(bottomrackmiddle));
            message.println(DriverStationLCD.Line.kUser3, 1, "Bottom Rack Middle");

        }
        if (armstick.getRawButton(7)) {
            //double print = matchmaker(middleracksides);
            //print is the value it moved it
            message.println(DriverStationLCD.Line.kUser2, 1, Double.toString(middleracksides));
            message.println(DriverStationLCD.Line.kUser3, 1, "Middle Rack Sides");


        }
        if (armstick.getRawButton(8)) {

            //double print = matchmaker(middlerackmiddle);
            //print is the value it moved it
            message.println(DriverStationLCD.Line.kUser2, 1, Double.toString(middlerackmiddle));
            message.println(DriverStationLCD.Line.kUser3, 1, "Middle Rack Middle");

        }
        /*this is what you would do to put setpoints in
         * if (JOYSTICKNAMEHERE.getRawButton(BUTTONNUMBERHERE)){
         *      SHOLORELBHEREPID.setSetpoint(SETPOINTNUMBERHERE);
         * }
         */
    }

}
