/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.commands;

/**
 *
 * @author Mackenzie
 */
public class TuneChassisPID extends CommandBase {
    private double startP = 0;
    private double maxP = 0.3; //max value of p
    private double incrementP = 0.05; //how much p goes up by
    
    private boolean done = false;
    
    private double setPoint = 0; //starting speed
    private double maxSetPoint = 5;  //find max chassis speed
    
    //for one setpoint:
    private int lengthSetPoint = (int)(maxSetPoint-setPoint); //length of arraay
    private double[] devSetPoint = new double[lengthSetPoint]; //deviation from setpoint
    private double[] meanDiff = new double[lengthSetPoint]; //mean of difference between rate & mean rate
    private double[] devRate = new double [lengthSetPoint]; //deviation from mean rate
    //means of the individual set point arrays
    //for each p value
    private int lengthP = (int)( (maxP-startP) / incrementP); 
    private double[] meanDevSetPoint = new double[lengthP];
    private double[] overallMeanDiff = new double[lengthP];
    private double[] meanDevRate = new double[lengthP];
            
    private double startI = 0;
    private double maxI = 0.5; //max 
    private double incrementI = 0.05;
    
            
    private double[] rates = new double[100];//rate of wheel spins
    private double mean = 0; //the average. 
    public TuneChassisPID (){
       requires(chassis); 
    }
            
    protected void initialize() {
        chassis.initPositionPIDS();
        chassis.initEncoders();
    }

    protected void execute() {
        for(double a = startP; a < maxP; a += incrementP) {
            //for(double a = startI; < maxI; a += incrementI)
            for(double b = setPoint; b < maxSetPoint; b ++) {
                chassis.resetPositionPIDError();
                chassis.setLeftPositionPIDValues(a, 0, 0);
                //chassis.setLeftPIDValues(p, a, 0);
                chassis.setLeftPIDPosition(b);
                System.out.println("Speed: " + chassis.getLeftEncoderDistance());
                System.out.println("B:" + b);
                //resets PID, sets value of p to a, sets pid speed to b
                /* 
                for each subpoint of p find the 
                deviations from the setpoint
                deviation from rate as well
                get mean between difference of rate and mean rate 
                */
                for(int c = 0; c < lengthSetPoint; c++) {
                    devSetPoint[c] = getDeviation(b, rates);
                    meanDiff[c] = getDifference(getMean(rates));
                    devRate[c] = getDeviation(0.0, rates);
                }
                System.out.println("At the end of one setpoint");
            }
            
            for(int d = 0; d < lengthP; d += incrementP) {
                //for(int d = 0; d < lengthI; d += incrementI)
                //Do it once then break TODO
                if (meanDevSetPoint[d]==0) {
                meanDevSetPoint[d] = getMean(devSetPoint);
                meanDiff[d] = getMean(meanDiff);
                meanDevRate[d] = getMean(devRate);
                //d=lengthP;
                // d=lengthI
                break;
                }
            }
            emptyArrays();
            System.out.println("Finished one p.");
        }
        
        
       done = true;
    }

    protected boolean isFinished() {
        return done;
    }
   

    protected void end() {
        getBest();
        chassis.stopJags();
        chassis.stopEncoders();
        chassis.disablePositionPID();
        //print the good p and setpoint values
        
    }

    protected void interrupted() {
        end();
    }
    
    private double getMean(double[] array) {
        
        for(int i = 0; i < array.length; i++) {
           mean += array[i];
       } 
       mean /= array.length;
       return mean;
    }
    private double getVariance(double center) {
        double[] numbers = new double[100];
        for(int i = 0; i < numbers.length; i++) {
            numbers[i]= (rates[i]-center)*(rates[i]-center);
      
        }
        double variance = getMean(numbers);
        return variance;
    }
    private double getDeviation(double center, double[] array) {
        double average = center;
        if (center == 0)
            average = getMean(array);
        double variance = getVariance(average);
        return Math.sqrt(variance);
    }
    /*
    mean of the difference between the rate and mean rate
    */
    private double getDifference(double center) {
        double[] differences = new double [100];
            for (int i = 0; i < differences.length; i++) {
                differences [i] = (rates[i]-center);
            }
            return getMean(differences);
    }
    
    public void emptyArrays() {
        for(int i = 0; i < lengthSetPoint; i++) {
            devRate[i] = 0;
            devSetPoint[i] = 0;
            meanDiff[i] = 0;
                    
        }
    }
    public void getBest() {
        double lowestSetPointDev = 100;
        double lowestRateDev = 100;
        double lowestMeanDiff = 100;
        
        double pLowestSetPointDev = 0;
        double pLowestRateDev = 0;
        double pLowestMeanDiff = 0;
        
        for(int j = 0; j < devSetPoint.length; j++) {
            if (devSetPoint[j] < lowestSetPointDev) 
               lowestSetPointDev = devSetPoint[j];
            pLowestSetPointDev = startP + (j * incrementP);
            // pLowestSetPointDev = startI + (j * incrementI);
        }
        for (int k = 0; k < devRate.length; k++) {
            if (devRate[k] < lowestRateDev)
                lowestRateDev = devRate[k];
            pLowestRateDev = startP + (k * incrementP);
            // pLowestRateDev = startI + (k * incrementI);
        }
        for (int m = 0; m < meanDiff.length; m++) {
            if (meanDiff[m] < lowestMeanDiff)
                lowestMeanDiff = meanDiff[m];
            pLowestMeanDiff = startP + (m * incrementP);
            // pLowestMeanDiff = startP + (m * incrementP);
        }
        
        System.out.println("Lowest p for Deviation from SetPoint: " + pLowestSetPointDev);
        System.out.println("Lowest p for Deviation from Rate: " + pLowestRateDev);
        System.out.println("Lowest p for Mean of Difference between Mean Rate and current Rate: " + pLowestMeanDiff);
        // System.out.println("Lowest i for Deviation from SetPoint: " + iLowestSetPointDev);
        // System.out.println("Lowest i for Deviation from Rate: " + pLowestRateDev);
        // System.out.println("Lowest i for Mean of Difference between Mean Rate and current Rate: " + pLowestMeanDiff);
        
        
    }
}
