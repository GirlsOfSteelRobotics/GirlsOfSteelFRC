package girlsofsteel.objects;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDSource;

public class WeirdEncoder implements PIDSource {
    
    //do stuff that joe sent to me that I forwarded to you here

    public WeirdEncoder(DigitalInput digitalInput){
        
    }//end constructor

    public void start(){
        
    }
    
    /**
     * This is the method that sends values to the PID controller.
     * @return current position
     */
    public double pidGet() {
        double position=0;
        
        //calculate returnValue
        
        return position;
    }//end pidGet
    
}//end class
