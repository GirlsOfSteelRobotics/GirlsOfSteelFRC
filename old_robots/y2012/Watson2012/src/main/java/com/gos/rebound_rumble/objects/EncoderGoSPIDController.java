package com.gos.rebound_rumble.objects;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;

@SuppressWarnings({"PMD.AvoidSynchronizedAtMethodLevel", "PMD.DoNotUseThreads", "PMD.TooManyFields"})
public class EncoderGoSPIDController implements Runnable {

    //constructor
    private double m_kp;
    private double m_ki;
    private double m_kd;
    private final Encoder m_encoder;
    private final PIDOutput m_jags;
    private final int m_type;
    //for setSetPoint()
    private double m_setPoint;
    //used for calculating error -> for PID (P,I,&D)
    private double m_error;
    private double m_previousError;
    private double m_errorSum;
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
    private double m_outputThreshold;
    private double m_rate;
    //two "int type" that the GoSPIDController can be
    public static final int RATE = 1;
    public static final int POSITION = 2;

    public EncoderGoSPIDController(double kp, double ki, double kd, Encoder encoder,
                                   PIDOutput jags, int type) {
        this.m_kp = kp;
        this.m_ki = ki;
        this.m_kd = kd;
        this.m_encoder = encoder;
        this.m_jags = jags;
        this.m_type = type;
    }

    public EncoderGoSPIDController(double kp, double ki, double kd, Encoder encoder,
                                   PIDOutput jags, int type, double integralThreshold) {
        this.m_kp = kp;
        this.m_ki = ki;
        this.m_kd = kd;
        this.m_encoder = encoder;
        this.m_jags = jags;
        this.m_type = type;
        this.m_integralThreshold = integralThreshold;
    }

    public synchronized void setPID(double p, double i, double d) {
        m_kp = p;
        m_ki = i;
        m_kd = d;
        m_errorSum = 0;
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
        m_currentPosition = m_encoder.getDistance(); //initialization
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
        double output = 0.0;
        while (m_pidEnabled) { //must be set to run -> through setSetPoint
            //conditions to run -> only when the error is more than desire

            synchronized (this) {//add for thread safety of variables
                m_previousTime = m_currentTime;
                m_previousPosition = m_currentPosition;
                m_currentTime = System.currentTimeMillis() / 1000.0;
                m_currentPosition = m_encoder.getDistance();
                calculateRate();
                m_previousError = m_error;
                calculateError();
                if (m_errorSum <= m_integralThreshold) {
                    m_errorSum += m_error;
                } else {
                    m_errorSum = m_integralThreshold;
                }
                double newOutput = m_kp * m_error + m_ki * m_errorSum * (m_currentTime - m_previousTime)
                    + m_kd * (m_error - m_previousError) / (m_currentTime - m_previousTime);
                if (newOutput - output < -m_outputThreshold || newOutput - output > m_outputThreshold) {
                    output = newOutput;
                }
            }

            m_jags.pidWrite(output);
            Timer.delay(0.01); //we don't want to run this faster than the encoder reads values
        }
        m_jags.pidWrite(0.0);
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
            currentValue = m_rate;
        } else if (m_type == POSITION) {
            currentValue = m_encoder.getDistance();
        } else {
            System.out.println("Error: The encoder is not set to rate (1) or position (2)");
        }
        m_error = m_setPoint - currentValue;
    }

    public double getRate() {
        return m_rate;
    }

    public double getSetPoint() {
        return m_setPoint;
    }

}
