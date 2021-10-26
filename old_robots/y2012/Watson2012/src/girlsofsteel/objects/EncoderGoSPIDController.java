package girlsofsteel.objects;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;

public class EncoderGoSPIDController implements Runnable {

    //constructor
    private double Kp = 0.0;
    private double Ki = 0.0;
    private double Kd = 0.0;
    private Encoder encoder;
    private PIDOutput jags;
    private int type;
    //for setSetPoint()
    private double setPoint;
    //used for calculating error -> for PID (P,I,&D)
    private double error = 0.0;
    private double previousError = 0.0;
    private double errorSum = 0.0;
    //boolean to switch the PID controller on/off -> condition for the while loop
    private boolean PIDEnabled = true;
    //calculate time & position
    private double previousTime;
    private double currentTime;
    private double previousPosition;
    private double currentPosition;
    //thresholds:
    private double integralThreshold = 999999999; //This is high so that no
    //function uses it until it's set using the second constructor used for rate
    private double outputThreshold = 0.0;
    private double rate = 0.0;
    //two "int type" that the GoSPIDController can be
    public static final int RATE = 1;
    public static final int POSITION = 2;

    public EncoderGoSPIDController(double Kp,double Ki,double Kd,Encoder encoder,
            PIDOutput jags, int type){
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
        this.encoder = encoder;
        this.jags = jags;
        this.type = type;
    }

    public EncoderGoSPIDController(double Kp,double Ki,double Kd,Encoder encoder,
            PIDOutput jags, int type, double integralThreshold){
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
        this.encoder = encoder;
        this.jags = jags;
        this.type = type;
        this.integralThreshold = integralThreshold;
    }

    public synchronized void setPID(double p, double i, double d){
        Kp = p;
        Ki = i;
        Kd = d;
        errorSum = 0;
    }

    public synchronized void setOutputThreshold(double outputThreshold){
        this.outputThreshold = outputThreshold;
    }

    //used to set a new setPoint (desired value) -> must to start up PID
    public synchronized void setSetPoint(double setPoint) {
        this.setPoint = setPoint;
    }

    //used to start & run the PID
    public void enable(){
        currentTime = System.currentTimeMillis()/1000.0; //initialization
        error = 0.0;
        previousError = 0.0;
        errorSum = 0.0;
        encoder.reset();
        currentPosition = encoder.getDistance(); //initialization
        PIDEnabled = true;
        new Thread(this).start(); //doesn't block the normal code & functions
    }

    //stops the PID
    public synchronized void disable(){
        PIDEnabled = false; //changes the while condition for run
    }

    public void resetError(){
        error = 0.0;
        previousError = 0.0;
        errorSum = 0.0;
    }

    public void run(){
        double output = 0.0;
        while(PIDEnabled){ //must be set to run -> through setSetPoint
            //conditions to run -> only when the error is more than desire

            synchronized (this) {//add for thread safety of variables
                previousTime = currentTime;
                previousPosition = currentPosition;
                currentTime = System.currentTimeMillis() / 1000.0;
                currentPosition = encoder.getDistance();
                calculateRate();
                previousError = error;
                calculateError();
                if (errorSum <= integralThreshold) {
                    errorSum += error;
                } else {
                    errorSum = integralThreshold;
                }
                double newOutput = Kp * error + Ki * errorSum * (currentTime - previousTime)
                        + Kd * (error - previousError) / (currentTime - previousTime);
                if(newOutput - output < -outputThreshold || newOutput - output > outputThreshold){
                    output = newOutput;
                }
            }

            jags.pidWrite(output);
            Timer.delay(0.01); //we don't want to run this faster than the encoder reads values
        }
        jags.pidWrite(0.0);
    }

    private synchronized void calculateRate(){
        if(currentTime != previousTime){
            rate = (currentPosition - previousPosition)/(currentTime - previousTime);
        }else{
            rate = 0;
        }
    }

    private synchronized void calculateError(){
        double currentValue = 0;
        if(type == RATE){
            currentValue = rate;
        }else if(type == POSITION){
            currentValue = encoder.getDistance();
        }else{
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6,
                1, "Error: The encoder is not set to rate (1) or position (2)");
        }
        error = setPoint - currentValue;
    }

    public double getRate(){
        return rate;
    }

    public double getSetPoint(){
        return setPoint;
    }

}
