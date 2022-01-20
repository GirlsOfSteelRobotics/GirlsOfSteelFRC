/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.cscore.AxisCamera;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Arm extends SimpleRobot {

    Joystick joy;
    Joystick armstick;
    Joystick stick;
    DriverStationLCD message;
    Compressor compy;
    Solenoid ClawO;
    Solenoid ClawC;
    Solenoid MBDeplo;
    Solenoid MBReverse;
    Solenoid Leftshinkicker;
    Solenoid AntiLeftshinkicker;
    Solenoid Rightshinkicker;
    Solenoid AntiRightshinkicker;
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
    double rightp = 0.0;
    double righti = 0.0;
    double rightd = 0.0;
    double leftp = 0.0;
    double lefti = 0.0;
    double leftd = 0.0;

    /**
     * This function is the constructor, called once
     * everytime the class is instantiated.
     */
    public Arm() {

        message = DriverStationLCD.getInstance();
        joy = new Joystick(1);
        armstick = new Joystick(2);
        stick = new Joystick(3);
        compy = new Compressor(4, 6, 4, 7);
        ClawO = new Solenoid(8, 1);
        ClawC = new Solenoid(8, 2);
        MBDeplo = new Solenoid(8, 3);
        MBReverse = new Solenoid(8, 4);
        Leftshinkicker = new Solenoid(8, 5);
        AntiLeftshinkicker = new Solenoid(8, 6);
        Rightshinkicker = new Solenoid(8, 7);
        AntiRightshinkicker = new Solenoid(8, 8);
        isforwardlimitok = true;
        AxisCamera camera = AxisCamera.getInstance();


        compy.start();

        try {
            Shol1 = new CANJaguar(2, CANJaguar.ControlMode.kVoltage);
            Shol1.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
            Shol1.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
            Shol1.configEncoderCodesPerRev(360);
            Shol1.enableControl();

            Shol2 = new CANJaguar(3, CANJaguar.ControlMode.kVoltage);

            Elbow = new CANJaguar(4, CANJaguar.ControlMode.kVoltage);
            Elbow.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
            Elbow.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
            Elbow.configEncoderCodesPerRev(360);
            Elbow.enableControl();

            //set PID sometime

            FrontLeft = new CANJaguar(5);
            FrontRight = new CANJaguar(6);
            RearLeft = new CANJaguar(7);
            RearRight = new CANJaguar(9);

        } catch (CANTimeoutException ex) {
            System.err.println("The first one in the constutor");

            message.println(DriverStationLCD.Line.kUser6, 1, "ERROR IN CONSTRUTOR");
            message.updateLCD();
            ex.printStackTrace();


        }

        Move = new RobotDrive(FrontLeft, RearLeft, FrontRight, RearRight);


        System.err.println("DOWNLOADED!!!");
        System.out.println("Did the birdie attack you?");
        try {


            //this sets neutral mode for all of the motors

            FrontLeft.setNeutralMode(NeutralMode.kCoast);
            FrontRight.setNeutralMode(NeutralMode.kCoast);
            RearLeft.setNeutralMode(NeutralMode.kCoast);
            RearRight.setNeutralMode(NeutralMode.kCoast);
            Shol1.setNeutralMode(NeutralMode.kBrake);
            Shol2.setNeutralMode(NeutralMode.kCoast);
            Elbow.setNeutralMode(NeutralMode.kBrake);

            //this is setting the position refrances for the sholder and elbow

            Sholval = Shol1.getPosition();
            //this sets the soft stops

            //Shol1.configSoftPositionLimits(0.0, -0.2);

        } catch (CANTimeoutException ex) {
            message.println(DriverStationLCD.Line.kUser6, 1, "ERROR sets nuetral");
            message.updateLCD();
            ex.printStackTrace();
        }
    }

    public void autonomous() {
        //while (isAutonomous()) {
        //getWatchdog().feed();
        //Timer.delay(.01);
        //}
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
        double rest = 77.0; // 0 on joystick
        double slot = 77.0; // button eight
        double bottomracksides = 90.0; //button nine
        double bottomrackmiddle = 89.0; //button ten
        double middleracksides = 78.0; //button eleven
        double middlerackmiddle = 39.0;
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
        while (isOperatorControl()) { //main while loop for teleop control
            getWatchdog().feed();
            Timer.delay(.01);
            val++;
            message.println(DriverStationLCD.Line.kMain6, 1, "this " + Integer.toString(val));
            message.updateLCD();
            try {
                isforwardlimitok = Shol1.getForwardLimitOK();
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }

            currenttime = ((double) System.currentTimeMillis() / 1000.0);
            if (flag)//only hits on first time
            {
                //dont change diff the first time
                flag = false;
            } else {
                diff = currenttime - prevtime;
            }


            //this is for arcade drving

            //Move.arcadeDrive(joy);

            double Xaxis = joy.getAxis(Joystick.AxisType.kX);
            double Yaxis = joy.getAxis(Joystick.AxisType.kY);
            Move.arcadeDrive(deadzone(Yaxis), deadzone(Xaxis));

            Timer.delay(0.01);
            //this is for tank drive

            //Move.tankDrive(joy, stick);

            //this is display clear and claw
            if (armstick.getTrigger()) {
                cleanslate();
            } else if (armstick.getRawButton(2)) {
                ClawO.set(true);
                //message.println(DriverStationLCD.Line.kUser4, 5, "Claw Open ");

            } else if (stick.getRawButton(2)) {
                ClawC.set(true);
                //message.println(DriverStationLCD.Line.kUser4, 5, "Claw Close");

            } else if (joy.getRawButton(3)) {
                MBDeplo.set(true);
                //message.println(DriverStationLCD.Line.kUser4, 5, "Mini Bot Deployed");
            } else if (joy.getRawButton(5)) {
                MBReverse.set(true);
                //message.println(DriverStationLCD.Line.kUser4, 5, "Mini Bot Retracted");
            } else if (joy.getRawButton(2)) {
                Leftshinkicker.set(true);
                Rightshinkicker.set(true);
                //message.println(DriverStationLCD.Line.kUser6, 5, "Liners out");
                message.updateLCD();
            } else if (armstick.getRawButton(9)) {
                double print = matchmaker(slot);
                //print is the value it moved it
                message.println(DriverStationLCD.Line.kUser2, 1, Double.toString(slot));
                message.println(DriverStationLCD.Line.kUser3, 1, "Feeder");

            } else if (armstick.getRawButton(10)) {
                double print = matchmaker(bottomracksides);
                //print is the value it moved it
                message.println(DriverStationLCD.Line.kUser2, 1, Double.toString(bottomracksides));
                message.println(DriverStationLCD.Line.kUser3, 1, "Bottom Rack Sides");

            } else if (armstick.getRawButton(6)) {

                double print = matchmaker(bottomrackmiddle);
                //print is the value it moved it
                message.println(DriverStationLCD.Line.kUser2, 1, Double.toString(bottomrackmiddle));
                message.println(DriverStationLCD.Line.kUser3, 1, "Bottom Rack Middle");

            } else if (armstick.getRawButton(7)) {
                double print = matchmaker(middleracksides);
                //print is the value it moved it
                message.println(DriverStationLCD.Line.kUser2, 1, Double.toString(middleracksides));
                message.println(DriverStationLCD.Line.kUser3, 1, "Middle Rack Sides");


            } else if (armstick.getRawButton(8)) {

                double print = matchmaker(middlerackmiddle);
                //print is the value it moved it
                message.println(DriverStationLCD.Line.kUser2, 1, Double.toString(middlerackmiddle));
                message.println(DriverStationLCD.Line.kUser3, 1, "Middle Rack Middle");

            } else if (stick.getRawButton(6)) {
                Sholp += 1.0;


            } else if (stick.getRawButton(7)) {
                Sholp -= 1.0;

            } else if (stick.getRawButton(8)) {
                Sholi += .001;

            } else if (stick.getRawButton(9)) {
                Sholi -= .001;

            } else if (stick.getRawButton(10)) {
                Shold += .1;

            } else if (stick.getRawButton(11)) {
                Shold -= .1;

            } else {
                ClawO.set(false);
                ClawC.set(false);
                MBDeplo.set(false);
                MBReverse.set(false);
                Leftshinkicker.set(false);
                AntiLeftshinkicker.set(false);
                Rightshinkicker.set(false);
                AntiRightshinkicker.set(false);
            }
            try {
                Shol1.setPID(Sholp, Sholi, Shold);
                message.println(DriverStationLCD.Line.kUser2, 1, "PR" + Double.toString(Shol1.getP()) + "PK" + Double.toString(Sholp) +
                    "D" + Double.toString(Shold));
                message.println(DriverStationLCD.Line.kUser3, 1, "I" + Double.toString(Sholi));
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }

            Timer.delay(0.01);

            try {
                if (armstick.getZ() >= .5) { //shoulder voltage mode
                    slavevoltage(deadzone(armstick.getY()) * 12);
                    message.println(DriverStationLCD.Line.kUser4, 1, "Shol volt");
                } else if (armstick.getZ() <= -.5) { //shoulder position mode
                    slavelinear(armstick.getY(), diff);
                    // message.println(DriverStationLCD.Line.kUser4, 1, "Shol pos" + Double.toString());
                }
                Timer.delay(0.01);
                //multiplier can go up to 24
                double valElbow = -1 * deadzone(stick.getY());
                if (valElbow > 0) {
                    valElbow = valElbow * 12.0;
                } else {
                    valElbow = valElbow * 7.0;

                }
                Elbow.setX(valElbow); //need to play with multiplier for neg vs pos values

                //message.println(DriverStationLCD.Line.kUser4, 1, "YShol " + Double.toString(valShol));
                //message.println(DriverStationLCD.Line.kUser5, 1, "Xelbo " + Double.toString(valElbow));


                //message.println(DriverStationLCD.Line.kUser4, 1, "X " + Double.toString(stick.getX()));
                //message.println(DriverStationLCD.Line.kUser3, 1, "Xdz " + Double.toString(deadzone(joy.getX())));
                //message.println(DriverStationLCD.Line.kUser5, 1, "Y " + Double.toString(armstick.getY()));
                //message.println(DriverStationLCD.Line.kUser5, 1, "Ydz " + Double.toString(deadzone(joy.getY())));
                //message.updateLCD();
            } catch (CANTimeoutException e1) {
                message.println(DriverStationLCD.Line.kUser6, 1, "ERROR ELBOWDRIVE");
                message.updateLCD();
                System.err.println("The one in the deadzone");
                e1.printStackTrace();
            }

            try {
                message.println(DriverStationLCD.Line.kUser5, 1, "Shol " + Double.toString(Shol1.getPosition()));
                //message.println(DriverStationLCD.Line.kUser3, 1, "Elbo " + Double.toString(Elbow.getPosition()));

            } catch (CANTimeoutException ex) {
                message.println(DriverStationLCD.Line.kUser6, 1, "ERROR PRINTVAl");
                message.updateLCD();
                ex.printStackTrace();
            }

            prevtime = ((double) System.currentTimeMillis() / 1000.0);
        } //end while
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
        if (axisvalue >= dzconstant || axisvalue <= dzconstant * -1) {
            retval = axisvalue;
        }
        //this method returns zero if it is within the deadzone,
        //dead zone is defined between positive and negative dzconstant
        return retval;
    }

    /**
     * This function clears the display on the classmate
     */
    public void cleanslate() {
        message.println(DriverStationLCD.Line.kUser2, 1, "                             ");
        message.println(DriverStationLCD.Line.kUser3, 1, "                             ");
        message.println(DriverStationLCD.Line.kUser4, 1, "                             ");
        message.println(DriverStationLCD.Line.kUser5, 1, "                             ");
        message.println(DriverStationLCD.Line.kUser6, 1, "                             ");
        message.updateLCD();
    }

    /**
     * This function sets the shoulder to a set position, and
     * places the shoulder jaguar in position mode if it wasn't previously.
     *
     * @param posval, position to move to
     * @return current position it thinks shoulder is at
     */
    public double matchmaker(double posval) {
        //prsets

        double returnval;
        returnval = 12.0;
        //posval must be greater than returnval

        try {
            if (Shol1.getControlMode().equals(CANJaguar.ControlMode.kPosition)) {
                Shol1.setX(posval);
                double SholVol = Shol1.getOutputVoltage();
                setshol2(SholVol);

                returnval = Shol1.getPosition();
            } else {
                Pdes = 0.0; //change everytime position resets
                Shol1.changeControlMode(WPI_TalonSRX.TalonControlMode.kPosition);
                Shol1.setPID(Sholp, Sholi, Shold);
                Shol1.enableControl();
            }
        } catch (CANTimeoutException ex) {
            message.println(DriverStationLCD.Line.kUser6, 1, "ERROR MATCHMAKER");
            message.updateLCD();
            ex.printStackTrace();
        }

        return returnval;
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
     * Function sets the shoulder to a certain revolutions
     * per minute based on a scaling factor.
     *
     * @param axisval, value from joystick to scale to rpms
     */
    public void slavespeedvoltage(double axisval) {
        double scalefactor = 30.0;
        try {
            if (Shol1.getControlMode().equals(CANJaguar.ControlMode.kSpeed)) {
                if (axisval > 0) {
                    //axisval = axisval * axisval;
                    axisval = axisval * scalefactor;
                } else {
                    //axisval = axisval * axisval * -1.0;
                    axisval = axisval * 11.0;
                }
                Shol1.setX(axisval);
                setshol2(Shol1.getOutputVoltage());
            } else {
                message.println(DriverStationLCD.Line.kMain6, 5, "NOT IN SPEED");
            }
        } catch (CANTimeoutException ex) {
            message.println(DriverStationLCD.Line.kUser6, 1, "ERROR SLAVESPEEDVOLTAGE");
            message.updateLCD();
            ex.printStackTrace();
        }

    }

    /**
     * This function sets the shoulder to a certain position
     * using position control that's based on a step function.
     * <p>
     * If using the y-axis for example the function will not change
     * the position of the arm if it's in the deadzone. If it is not in
     * the deadzone it will increment the position value or decrement the
     * position value based on four possible zones set by the
     * forward1, forward2, backward1, and backward2 values.
     *
     * @param axisval, joystick axis value
     * @return
     */
    public double slaveposvoltage(double axisval) {
        double returnval = 99999.0;
        double offset = -0.05;
        double scalefactor = 10.0;
        double pos = 77.777;
        double forward1 = -.3;
        double forward2 = -.7;
        double backward1 = .3;
        double backward2 = .7;
        double endzone = 0.02; //value to incr or decr
        double middlezone = 0.01; // value to incr or decr


        //change offset and scalefactor
        try {
            if (Shol1.getControlMode().equals(CANJaguar.ControlMode.kPosition)) {
                returnval = Shol1.getPosition();
                if (axisval <= forward2) {
                    //forward top zone
                    returnval = returnval + endzone;
                } else if (axisval > forward2 && axisval <= forward1) {
                    //forward middle zone
                    returnval = returnval + middlezone;
                } else if (axisval >= backward1 && axisval < backward2) {
                    //backward middle zone
                    returnval = returnval - middlezone;
                } else if (axisval >= backward2) {
                    //backward bottom zone
                    returnval = returnval - endzone;
                } else {
                    //dead zone
                }
                Shol1.setX(returnval);
                setshol2(Shol1.getOutputVoltage());
                Timer.delay(0.01);
            } else {
                Pdes = 0.0; //reset anytime encoder gets reset
                Shol1.changeControlMode(WPI_TalonSRX.TalonControlMode.kPosition);
                Shol1.setPID(Sholp, Sholi, Shold);
                Shol1.enableControl();
            }
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        return returnval;
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
        double scalefactor = 12;
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
        xaxis *= 12;
        yaxis *= 12;
        if (joy.getZ() >= .5) { //voltage mode
            try {
                if (RearLeft.getControlMode().equals(CANJaguar.ControlMode.kVoltage)
                    && RearRight.getControlMode().equals(CANJaguar.ControlMode.kVoltage)) {
                } else {
                    RearLeft.changeControlMode(WPI_TalonSRX.TalonControlMode.kVoltage);
                    RearLeft.enableControl();
                    RearRight.changeControlMode(WPI_TalonSRX.TalonControlMode.kVoltage);
                    RearRight.enableControl();
                }
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
        } else if (joy.getZ() <= -.5) { //speed mode
        }
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

                Pdes = 0.0; //reset Pdes anytime encoder resets
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
}
