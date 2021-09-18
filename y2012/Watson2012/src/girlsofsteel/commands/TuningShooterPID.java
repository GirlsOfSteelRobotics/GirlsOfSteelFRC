package girlsofsteel.commands;

public class TuningShooterPID extends CommandBase {
    
    boolean run = false;
    
    //for tuning the p value
    double step = 0.025;
    double max = 0.3;
    
//    //for tuning the i value -> using the p value that we like
//    double step = 0.002;
//    double max = 0.1;
    
    int meanLength = (int) ((max-step)/step);
    
    int startSetPoint = 5;
    int maxSetPoint = 30;
    
    int dataLength = maxSetPoint - startSetPoint;
    
    int count = 0;
    int number = 0;
    double[] deviations = new double[dataLength];
    double[] differences = new double[dataLength];
    double[] devSetPoints = new double[dataLength];//deviation from set point
    //setPoint 5 = index 0
    //setPoint 6 = index 1
    //setPoint 30 = index 25
    //setPoint - 5 = index
    double deviationMean = 0;
    double differencesMean = 0;
    double devSetPointMean = 0;
    double[] deviationMeans = new double[meanLength];
    double[] differencesMeans = new double[meanLength];
    double[] devSetPointMeans = new double[meanLength];
    //p 0.025 = index 0
    //p 0.05 = index 1
    //p 0.075 = index 2
    //p 0.275 = index 10
    //(p-0.025)/0.025 = index -> p = 0.025(index)+0.025
    
    double error = shooter.VELOCITY_ERROR_RANGE;
    
    public TuningShooterPID(){
        requires(shooter);
    }
    
    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
    }

    protected void execute(){
        for(double n=step; n<max; n+=step){//starts at step, goes to max - step
            for(int setPoint = startSetPoint; setPoint<=maxSetPoint; setPoint++){
                //starts at startSetPoint, goes to maxSetPoint
                shooter.resetPIDError();
//                shooter.setPIDValues(n, 0.0, 0.0); //for tuning the p
                shooter.setPIDValues(0.025, n, 0.0); //for tuning the i
                shooter.setPIDSpeed((double)setPoint);
                try {
                    wait(1500);//wait 1.5 seconds so the speed catches on
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                //collect and store the data into the deviations and differences
                //arrays for a single set point 
                for(int i=0; i<dataLength; i++){
                    if(deviations[i] == 0){//find the next empty slot in the array
                        deviations[i] = calculateDeviationData(setPoint);
                        //collect 100 data points (rate from encoder)
                        //and it returns the calculated deviation of the 100 points
                        //calculates the rate mean & the difference between the
                        //set point and the mean rate
                        differences[i] = setPointToRateMean;
                        //this gets the difference calculated above
                        devSetPoints[i] = deviationFromSetPoint(setPoint);
                        //deviation equation, but instead of the mean it is
                        //the set point
                        count++;//adds 1 to the counter -> so you know how many
                        //total data points are in the array (if it doesn't fill
                        //it all for some reason
                        //not the index number! (1+index number)
                        break;//once you add the data to the empty slot -> stop
                        //finding empty slots
                    }
                }
                System.out.println("P=" + n + " & Set Point=" + setPoint
                        + " : Diff=" + differences[count-1] + " Dev:"
                        + deviations[count-1]);
//                System.out.println("I=" + n + " & Set Point=" + setPoint
//                        + " : Diff=" + differences[count-1] + " Dev:"
//                        + deviations[count-1]);
            }
            deviationMean = calculateMean(deviations);//calculates the mean for
            //all deviations for the set points for a given p value
            differencesMean = calculateMean(differences);//same, but for the
            //differences between the set point and the mean rate
            devSetPointMean = calculateMean(devSetPoints);
            for(int i=0; i<meanLength; i++){
                if(deviationMeans[i] == 0){//find the next empty slot in the means
                    //arrays
                    deviationMeans[i] = deviationMean; //store the deviation for
                    //1 p value in a slot
                    differencesMeans[i] = differencesMean;//same, but for the 
                    //differences between the set point and mean rate
                    devSetPointMeans[i] = devSetPointMean;//same, but for the
                    //deviation from the set point
                    number = i;
                    System.out.println("P=" + n + "Dev Mean:" + deviationMean + "="
                            + deviationMeans[number] + " Diff Mean:" + differencesMean + "="
                            + differencesMeans[number] + " Dev Set Point:" + devSetPointMean
                            + "=" + devSetPointMeans[number]);
//                    System.out.println("I=" + n + "Dev Mean:" + deviationMean + "="
//                            + deviationMeans[number] + " Diff Mean:" + differencesMean + "="
//                            + differencesMeans[number] + " Dev Set Point:" + devSetPointMean
//                            + "=" + devSetPointMeans[number]);
                    break;
                }
            }
            refreshData();//empty all arrays and numbers that have to do with a
            //single p value and lots of set points
        }
        run = true;
    }
    
    protected boolean isFinished(){
        return run;
    }
    
    protected void end(){
        shooter.disablePID();
        shooter.stopEncoder();
        shooter.stopJags();
        int indexDevLow = getIndexofLowest(deviationMeans);
        int indexDiffLow = getIndexofLowest(differencesMeans);
        System.out.println("At val=" + ((indexDevLow*step)+step) + "Lowest Deviation Mean:" +
                deviationMeans[indexDevLow] + " Difference Mean:"
                + differencesMeans[indexDevLow] + " Deviation Set Point:" + 
                devSetPointMeans[indexDevLow]);
        System.out.println("At val=" + ((indexDiffLow*step)+step) + "Lowest Difference Mean:"
                + differencesMeans[indexDiffLow] + " Deviation Mean:" + 
                deviationMeans[indexDiffLow] + " Deviation Set Point:" +
                devSetPointMeans[indexDiffLow]);
        double bestVal = getBestVal();
        System.out.println("'Best val' (within range & lowest deviation):" + bestVal);
        int indexDevSetPointLow = getIndexofLowest(devSetPointMeans);
        System.out.println("At val=" + ((indexDevSetPointLow*step)+step) + "Lowest "
                + "Deviation Set Point:" + devSetPointMeans[indexDevSetPointLow]
                + " Difference Mean:" + differencesMeans[indexDevSetPointLow] +
                " Deviation Mean:" + deviationMeans[indexDevSetPointLow]);
    }
    
    protected void interrupted() {
        end();
    }
    
    int counter = 0;
    double totalRates = 0;
    double[] rates = new double[100];
    double[] variences = new double[rates.length];
    double rateSum;
    double differenceSum;
    double rateMean;
    double differenceMean;
    double standardDeviation;
    double setPointToRateMean;
    
    //adds the encoder rates of the shooter wheel to an array -> it only has 100
    //spaces so once those 100 are filled up, it starts filling up spots starting
    //from the beginning (0)
    public void addRatesToArray(double rate){
        rates[counter] = rate;
        counter++;
        if(counter >= rates.length){
            counter = 0;
        }
    }
            
    //adds all the rates in the array then divides by the number of items in the array
    public double calculateRateMean(){
        totalRates = 0;
        rateSum = 0;
        rateMean = 0;
        for(int j=0; j<rates.length; j++){
            if(rates[j] != 0){
                rateSum += rates[j];
                totalRates++;//counter to see how many of spots in the array
                //have information in them -> if not, it is not used in any
                //calculations
            }
        }
        rateMean = rateSum/totalRates;
        System.out.println("Rate Sum:" + rateSum + " & Rate Mean:" + rateMean);
        return rateMean;
    }
    
    public double getRateMean(){
        return rateMean;
    }
    
    //how close to the mean all the rates are -> we want this to be as small as 3possible
    public double calculateStandardDeviation(){
        differenceMean = 0;
        differenceSum = 0;
        standardDeviation = 0;
        for(int j=0; j<totalRates; j++){
            variences[j] = (rates[j]-rateMean)*(rates[j]-rateMean); //calculates and stores the
            //square of the difference between the rate and the rate mean
        }
        for(int j=0; j<totalRates; j++){
            differenceSum += variences[j];//adds all the differences together
        }
        differenceMean = differenceSum/totalRates;
        standardDeviation = Math.sqrt(differenceSum)/totalRates;
        return standardDeviation;
    }
    
    public double getDifferenceSum(){
        return differenceSum;
    }
    
    public double calculateDeviationData(double setPoint){
        for(int j=0; j<100; j++){
            if(shooter.getEncoderRate()<setPoint+1 && shooter.getEncoderRate()>setPoint-1){
                break;
            }
        }
        for(int j=0; j<100; j++){
            addRatesToArray(shooter.getEncoderRate());
        }
        calculateRateMean();
        setPointToRateMean = setPoint - rateMean;
        return calculateStandardDeviation();
    }

    protected double calculateMean(double[] array) {
        double sum = 0;
        double mean = 0;
        for (int i = 0; i < count; i++) {
            sum += array[i];
        }
        mean = sum / (double) count;
        return mean;
    }
    
    protected void refreshData() {
        count = 0;
        for (int i = 0; i < dataLength; i++) {
            deviations[i] = 0;
            differences[i] = 0;
            devSetPoints[i] = 0;
        }
        deviationMean = 0;
        differencesMean = 0;
        devSetPointMean = 0;
        counter = 0;
        totalRates = 0;
        for(int i=0; i<rates.length; i++){
            rates[i] = 0.0;
            variences[i] = 0.0;
        }
        rateSum = 0;
        differenceSum = 0;
        rateMean = 0;
        differenceMean = 0;
        standardDeviation = 0;
        setPointToRateMean = 0;
    }
    
    protected int getIndexofLowest(double[] array){
        int index = 0;
        double lowest = 999999999;
        for(int i=0; i<array.length; i++){
            if(lowest>array[i]){
                lowest = array[i];
                index = i;
            }
        }
        return index;
    }
    
    protected double getBestVal(){
        double p;
        double lowestDev = 999999999;
        double indexDev = 0;
        for(int i=0; i<count-1; i++){
            if(deviationMeans[i] !=0 && Math.abs(differencesMeans[i]) <= error){
                if(lowestDev > deviationMeans[i]){
                    lowestDev = deviationMeans[i];
                    indexDev = i;
                }
            }
        }
        p = (indexDev*step)+step;
        return p;
    }
    
    protected double deviationFromSetPoint(double setPoint){
        double deviationSetPoint = 0;
        for(int i=0; i<totalRates; i++){
            deviationSetPoint = Math.sqrt((rates[i]-setPoint)*(rates[i]-setPoint))/totalRates;
        }
        return deviationSetPoint;
    }
    
}