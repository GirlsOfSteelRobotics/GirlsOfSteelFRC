package com.gos.aerial_assist.objects;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;

import java.util.function.DoubleConsumer;

@SuppressWarnings("PMD")
public class EncoderGoSPidController implements Runnable {

    //two "int type" that the GoSPIDController can be
    public static final int RATE = 1;
    public static final int POSITION = 2;

    //constructor
    private volatile double m_kp;
    private volatile double m_ki;
    private volatile double m_kd;

    private final int m_zeroEncoderValue;
    private double m_output;

    private final Encoder m_encoder;
    private final DoubleConsumer m_jags;
    private final int m_type;
    //for setSetPoint()
    private double m_setPoint;
    //used for calculating error -> for PID (P,I,&D)
    private double m_error = 0.0;
    private double m_previousError = 0.0;
    private double m_errorSum = 0.0;
    //boolean to switch the PID controller on/off -> condition for the while loop
    private boolean m_pidEnabled = true;
    //calculate time & position
    private double m_previousTime;
    private double m_currentTime;
    private double m_previousPosition;
    private double m_currentPosition;
    //thresholds:
    private double m_integralThreshold = 999999999; //This is high so that no
    //function uses it until it's set using the second constructor used for rate
    private double m_outputThreshold = 0.0;
    private double m_rate = 0.0;

    private boolean m_reverseEncoder = false;
    private boolean m_modEncoder = false;

    public EncoderGoSPidController(double kp, double ki, double kd, Encoder encoder,
                                   DoubleConsumer jags, int type) {
        this(kp, ki, kd, encoder, jags, type, 0);
    }

    public EncoderGoSPidController(double kp, double ki, double kd, Encoder encoder,
                                   DoubleConsumer jags, int type, int zeroEncoderValue) {
        this(kp, ki, kd, encoder, jags, type, zeroEncoderValue, false, false);
    }

    public EncoderGoSPidController(double kp, double ki, double kd, Encoder encoder,
                                   DoubleConsumer jags, int type, int zeroEncoderValue, boolean reverseEncoder, boolean modEncoder) {
        this(kp, ki, kd, encoder, jags, type, zeroEncoderValue, reverseEncoder, modEncoder, 999999999);
    }

    public EncoderGoSPidController(double kp, double ki, double kd, Encoder encoder,
                                   DoubleConsumer jags, int type, boolean reverseEncoder, boolean modEncoder) {
        this(kp, ki, kd, encoder, jags, type, 0, reverseEncoder, modEncoder);
    }

    public EncoderGoSPidController(double kp, double ki, double kd, Encoder encoder,
                                   DoubleConsumer jags, int type, int zeroEncoderValue, boolean reverseEncoder, boolean modEncoder, double integralThreshold) {
        m_kp = kp;
        m_ki = ki;
        m_kd = kd;
        m_encoder = encoder;
        m_jags = jags;
        m_type = type;
        m_zeroEncoderValue = zeroEncoderValue;
        m_reverseEncoder = reverseEncoder;
        m_modEncoder = modEncoder;
        m_integralThreshold = integralThreshold;
    }

    public synchronized void setPID(double p, double i, double d) {
        m_kp = p;
        m_ki = i;
        m_kd = d;
        //errorSum = 0;
    }

    public synchronized void setOutputThreshold(double outputThreshold) {
        this.m_outputThreshold = outputThreshold;
    }

    //used to set a new setPoint (desired value) -> must to start up PID
    public synchronized void setSetPoint(double setPoint) {
        this.m_setPoint = setPoint;
    }

    //used to start & run the PID
    public void enable() {
        m_currentTime = System.currentTimeMillis() / 1000.0; //initialization
        m_error = 0.0;
        m_previousError = 0.0;
        m_errorSum = 0.0;
        m_encoder.reset();
        m_currentPosition = getSignedDistance(); //encoder.getDistance(); //initialization TODO
        m_pidEnabled = true;
        new Thread(this).start(); //doesn't block the normal code & functions
    }

    //stops the PID
    public synchronized void disable() {
        m_pidEnabled = false; //changes the while condition for run
    }

    public void resetError() {
        m_error = 0.0;
        m_previousError = 0.0;
        m_errorSum = 0.0;
    }

    @Override
    public void run() {
        m_output = 0.0;
        while (m_pidEnabled) { //must be set to run -> through setSetPoint
            //conditions to run -> only when the error is more than desire

            synchronized (this) { //add for thread safety of variables
                m_previousTime = m_currentTime;
                m_previousPosition = m_currentPosition;
                m_currentTime = System.currentTimeMillis() / 1000.0;
                m_currentPosition = getSignedDistance(); //encoder.getDistance();
                calculateRate();
                m_previousError = m_error;
                calculateError();
                if (Math.abs(m_errorSum) <= m_integralThreshold) {
                    m_errorSum += m_error;
                } else {
                    if (m_errorSum < 0) {
                        m_errorSum = -m_integralThreshold;
                    } else {
                        m_errorSum = m_integralThreshold;
                    }
                }
                double newOutput = m_kp * m_error + m_ki * m_errorSum * (m_currentTime - m_previousTime)
                    + m_kd * (m_error - m_previousError) / (m_currentTime - m_previousTime);
                if (newOutput - m_output < -m_outputThreshold || newOutput - m_output > m_outputThreshold) {
                    m_output = newOutput;
                }

            }
            //System.out.println("PID output: " + output + "\tP value: " + Kp + "\tError: " + error + "\tEncoder Value: " + currentPosition);
            m_jags.accept(m_output);
            Timer.delay(0.01); //we don't want to run this faster than the encoder reads values
        }
        m_jags.accept(0.0);
    }

    private synchronized void calculateRate() {
        if (m_currentTime != m_previousTime) {
            m_rate = (m_currentPosition - m_previousPosition) / (m_currentTime - m_previousTime);
        } else {
            m_rate = 0;
        }
    }

    private synchronized void calculateError() {
        double currentValue = 0;
        if (m_type == RATE) {
            currentValue = m_rate; //TODO
        } else if (m_type == POSITION) {
            currentValue = getSignedDistance(); //encoder.getDistance();
        } else {
            /* The DriverStationLCD call was removed in 2015 WPIlib because the
             * LCD area of the driver station interface was removed.
             * Should be changed to SmartDashboard instead.
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6,
                    1, "Error: The encoder is not set to rate (1) or position (2)");
            */
        }
        m_error = m_setPoint - currentValue;
    }

    public double getRate() {
        return m_rate;
    }

    public double getSetPoint() {
        return m_setPoint;
    }

    public double getError() {
        return m_error;
    }

    public double getErrorSum() {
        return m_errorSum;
    }

    public double getOutputThreshold() {
        return m_outputThreshold;
    }

    public double getOutput() {
        return m_output;
    }

    public double getSignedDistance() {
        double newEncoderVal;
        if (m_reverseEncoder) {
            newEncoderVal = -m_encoder.getDistance() + m_zeroEncoderValue;
        } else {
            newEncoderVal = m_encoder.getDistance() + m_zeroEncoderValue;
        }
        if (m_modEncoder) {
            double moddedVal = newEncoderVal % 360; //For Degrees!
            return moddedVal;
        }
        return newEncoderVal;
    }

}
