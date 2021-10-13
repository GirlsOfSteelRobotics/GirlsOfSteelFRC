package girlsofsteel.subsystems;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.util.SortedVector;
import girlsofsteel.RobotMap;
import girlsofsteel.objects.EncoderGoSPIDController;
import girlsofsteel.objects.MapDouble;
import girlsofsteel.objects.MapDoubleComparator;

public class Shooter extends Subsystem {

    public final int TOP_HOOP = 3;
    public final int MIDDLE_HOOP = 2;
    public final int BOTTOM_HOOP = 1;
    
    public final double TOP_HOOP_HEIGHT = 2.4892;
    public final double MIDDLE_HOOP_HEIGHT = 1.5494;
    public final double BOTTOM_HOOP_HEIGHT = 0.7112;
    
    public final double ROBOT_HEIGHT = 1.3843; //not exact, approximate
    
    private final Jaguar jags = new Jaguar(RobotMap.SHOOTER_JAGS);

    private final double WHEEL_DIAMETER = 0.2032;
    private final double GEAR_RATIO = 1.0;
    private final double PULSES = 100.0;
    private final double ENCODER_UNIT = (Math.PI*WHEEL_DIAMETER*GEAR_RATIO)/PULSES;
    
    private Encoder encoder = new Encoder(RobotMap.ENCODER_SHOOTER_CHANNEL_A,
            RobotMap.ENCODER_SHOOTER_CHANNEL_B,false,
            CounterBase.EncodingType.k4X);
    
    double p = 0.0; //NEED TO TUNE THESE
    double i = 0.0; 
    double d = 0.0; //ALL THREE
    
    EncoderGoSPIDController PID = new EncoderGoSPIDController(p,i,d,encoder,
            new PIDOutput(){
                public void pidWrite(double output){
                    setJags(output);
                }
            },EncoderGoSPIDController.RATE);
    
    protected void initDefaultCommand() {
        
    }
    
    public void setJags(double speed){
        jags.set(speed);
    }
    
    public void initEncoder(){
        encoder.setDistancePerPulse(ENCODER_UNIT);
        encoder.start();
    }
    
    public void stopEncoder(){
        encoder.stop();
    }
    
    public void initPID(){
        PID.enable();
    }
    
    public void setPIDSpeed(double speed){
        PID.setSetPoint(speed);
    }
    
    public void disablePID(){
        PID.disable();
    }

    public void shootAuto(double xDistance, int target){
        //3 constants for the 3 levels of targets
        double yDistance = getYDistance(target);
        double angle = getAngle(xDistance, yDistance);
        
        double velocity;

        velocity = ((xDistance*Math.sqrt(9.8/((2*(xDistance*Math.cos(angle)))-yDistance)))/Math.tan(angle))*(5.5/4.0);
        
//        Joe's constant equation for experiemental stuff.
//        double ratioBetweenWheelToBall = 1;
//        double constantA = 1;
//        double constantB = 1;
//        double constantC = 1;
//        
//        velocity = ratioBetweenWheelToBall*xDistance*Math.sqrt(constantA/((constantB*xDistance)-constantC));
        
        PID.setSetPoint(velocity);
    }
    
    public void shootUsingTable(double velocity){
        double newVelocity = velocity*(5.5/4.0);
        PID.setSetPoint(newVelocity);
    }
    
    private double getYDistance(int target){
        double yDistance = 0;
        if(target == TOP_HOOP){
            yDistance = TOP_HOOP_HEIGHT - ROBOT_HEIGHT;
        }else if(target == MIDDLE_HOOP){
            yDistance = MIDDLE_HOOP_HEIGHT - ROBOT_HEIGHT;
        }else if(target == BOTTOM_HOOP){
            yDistance = BOTTOM_HOOP_HEIGHT - ROBOT_HEIGHT;
        }
        return yDistance;
    }
    
    private double getAngle(double xDistance, double yDistance){
        double angle;
        return angle = MathUtils.atan(yDistance/xDistance);
    }  
    
    SortedVector.Comparator comparator = new MapDoubleComparator();
    
    SortedVector list = new SortedVector(comparator);
    
    public void populate (MapDouble newElement){
        list.addElement(newElement);
    }
    
    /**
     * 
     * @param distance, needs to get here from the camera*-
     * @return 
     */
    public double getBallVelocityFrTable(double distance){
        int index = (int) Math.ceil(list.size()/2.0);
        MapDouble currentValue = (MapDouble) list.elementAt(index);
        MapDouble currentLow = new MapDouble(0.0, 0.0);
        MapDouble currentMax = new MapDouble(0.0, 0.0);
        boolean end = false;
        while (!end){
            currentValue = ((MapDouble) list.elementAt(index));

            if (currentValue.getDistance() == currentLow.getDistance() || currentValue.getDistance() == currentMax.getDistance() ){
                end = true;
            }
            
            
            if (currentValue.getDistance() < distance){
                currentLow = currentValue;
                index = index-1;
            }
            
            else if  (currentValue.getDistance() > distance){
                currentMax = currentValue;
                index = index + 1;
            }
            
            else{
                return currentValue.getVelocity();
            }
        }
        
        double distance1 = currentLow.getDistance();
        double velocity1 = currentLow.getVelocity();
        double distance2 = currentMax.getDistance();
        double velocity2 = currentMax.getVelocity();
        return interpolate(distance, distance1, velocity1, distance2, velocity2);
    }
    
    private double interpolate(double distance, double distance1, double velocity1, double distance2, double velocity2){
        double velocity = 0.0;
        velocity = velocity1 + (velocity2-velocity1)*((distance-distance1)/(distance2-distance1));
        return velocity;
    }
    
}