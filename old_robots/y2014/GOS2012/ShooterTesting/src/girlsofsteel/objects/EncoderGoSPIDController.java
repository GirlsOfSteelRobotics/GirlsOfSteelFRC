package girlsofsteel.objects;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    private double error=0.0;
    private double previousError=0.0;
    private double errorSum=0.0;
    //boolean to switch the PID controller on/off -> condition for the while loop
    private boolean PIDEnabled = true;
    //calculate time & position
    private double previousTime;
    private double currentTime;
    private double previousPosition;
    private double currentPosition;
    //used for rate
    private double rate=0.0;
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
    
    public synchronized void setPID(double p, double i, double d){
        Kp = p;
        Ki = i;
        Kd = d;
        errorSum = 0;
    }
    
    //used to set a new setPoint (desired value) -> must to start up PID
    public synchronized void setSetPoint(double setPoint) {
        this.setPoint = setPoint;
    }
    
    //used to start & run the PID
    public void enable(){
        currentTime = System.currentTimeMillis()/1000.0; //initialization
        currentPosition = encoder.getDistance(); //initialization
        new Thread(this).start(); //doesn't block the normal code & functions
    }
    
    //stops the PID
    public synchronized void disable(){
        PIDEnabled = false; //changes the while condition for run
    }
    
    public void run(){
        double output;
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
                errorSum += error;
                output = Kp * error + Ki * errorSum + Kd * (error - previousError) / (currentTime - previousTime);
            }
       
            jags.pidWrite(output);
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2,
                    1, "Error " + error + " Kp: " + Kp*error);
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3,
                    1, "Ki: " + Ki*errorSum);
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser4,
                    1, "Output " + output + " Kd: " + 
                    Kd*(error-previousError)/(currentTime - previousTime));
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser5,
                    1, "Ki*: " + Ki);
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser5,
                    1,"errorSum: " + errorSum);
            DriverStationLCD.getInstance().updateLCD();
            Timer.delay(0.01); //we don't want to run this faster than the encoder reads values
        }
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
    
    private void printRate(){
        SmartDashboard.putDouble("Encoder Rate",rate);
    }
    
}