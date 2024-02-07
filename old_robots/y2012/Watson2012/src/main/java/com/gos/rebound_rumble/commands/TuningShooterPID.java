package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Shooter;

@SuppressWarnings({"PMD.TooManyMethods", "PMD.TooManyFields"})
public class TuningShooterPID extends GosCommandBase {

    private boolean m_run;

    //for tuning the p value
    private static final double STEP = 0.025;
    private static final double MAX = 0.3;

    //    //for tuning the i value -> using the p value that we like
    //    double step = 0.002;
    //    double max = 0.1;

    private static final int MEAN_LENGTH = (int) ((MAX - STEP) / STEP);

    private static final int START_SET_POINT = 5;
    private static final int MAX_SET_POINT = 30;

    private static final int DATA_LENGTH = MAX_SET_POINT - START_SET_POINT;
    private static final double ERROR = Shooter.VELOCITY_ERROR_RANGE;

    private int m_count;
    private int m_number;
    private double[] m_deviations = new double[DATA_LENGTH];
    private double[] m_differences = new double[DATA_LENGTH];
    private double[] m_devSetPoints = new double[DATA_LENGTH]; //deviation from set point
    //setPoint 5 = index 0
    //setPoint 6 = index 1
    //setPoint 30 = index 25
    //setPoint - 5 = index
    private double m_deviationMean;
    private double m_differencesMean;
    private double m_devSetPointMean;
    private double[] m_deviationMeans = new double[MEAN_LENGTH];
    private double[] m_differencesMeans = new double[MEAN_LENGTH];
    private double[] m_devSetPointMeans = new double[MEAN_LENGTH];
    //p 0.025 = index 0
    //p 0.05 = index 1
    //p 0.075 = index 2
    //p 0.275 = index 10
    //(p-0.025)/0.025 = index -> p = 0.025(index)+0.025

    private int m_counter;
    private double m_totalRates;
    private double[] m_rates = new double[100];
    private double[] m_variences = new double[m_rates.length];
    private double m_rateSum;
    private double m_differenceSum;
    private double m_rateMean;
    private double m_standardDeviation;
    private double m_setPointToRateMean;


    private final Shooter m_shooter;

    public TuningShooterPID(Shooter shooter) {
        m_shooter = shooter;
    }

    @Override
    public void initialize() {
        m_shooter.initEncoder();
        m_shooter.initPID();
    }

    @SuppressWarnings("PMD")
    @Override
    public void execute() {
        for (double n = STEP; n < MAX; n += STEP) { //starts at step, goes to max - step
            for (int setPoint = START_SET_POINT; setPoint <= MAX_SET_POINT; setPoint++) {
                //starts at startSetPoint, goes to maxSetPoint
                m_shooter.resetPIDError();
                //                shooter.setPIDValues(n, 0.0, 0.0); //for tuning the p
                m_shooter.setPIDValues(0.025, n, 0.0); //for tuning the i
                m_shooter.setPIDSpeed(setPoint);
                try {
                    wait(1500); //wait 1.5 seconds so the speed catches on
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                //collect and store the data into the deviations and differences
                //arrays for a single set point
                for (int i = 0; i < DATA_LENGTH; i++) {
                    if (m_deviations[i] == 0) { //find the next empty slot in the array
                        m_deviations[i] = calculateDeviationData(setPoint);
                        //collect 100 data points (rate from encoder)
                        //and it returns the calculated deviation of the 100 points
                        //calculates the rate mean & the difference between the
                        //set point and the mean rate
                        m_differences[i] = m_setPointToRateMean;
                        //this gets the difference calculated above
                        m_devSetPoints[i] = deviationFromSetPoint(setPoint);
                        //deviation equation, but instead of the mean it is
                        //the set point
                        m_count++; //adds 1 to the counter -> so you know how many
                        //total data points are in the array (if it doesn't fill
                        //it all for some reason
                        //not the index number! (1+index number)
                        break; //once you add the data to the empty slot -> stop
                        //finding empty slots
                    }
                }
                System.out.println("P=" + n + " & Set Point=" + setPoint
                    + " : Diff=" + m_differences[m_count - 1] + " Dev:"
                    + m_deviations[m_count - 1]);
                //                System.out.println("I=" + n + " & Set Point=" + setPoint
                //                        + " : Diff=" + differences[count-1] + " Dev:"
                //                        + deviations[count-1]);
            }
            m_deviationMean = calculateMean(m_deviations); //calculates the mean for
            //all deviations for the set points for a given p value
            m_differencesMean = calculateMean(m_differences); //same, but for the
            //differences between the set point and the mean rate
            m_devSetPointMean = calculateMean(m_devSetPoints);
            for (int i = 0; i < MEAN_LENGTH; i++) {
                if (m_deviationMeans[i] == 0) { //find the next empty slot in the means
                    //arrays
                    m_deviationMeans[i] = m_deviationMean; //store the deviation for
                    //1 p value in a slot
                    m_differencesMeans[i] = m_differencesMean; //same, but for the
                    //differences between the set point and mean rate
                    m_devSetPointMeans[i] = m_devSetPointMean; //same, but for the
                    //deviation from the set point
                    m_number = i;
                    System.out.println("P=" + n + "Dev Mean:" + m_deviationMean + "="
                        + m_deviationMeans[m_number] + " Diff Mean:" + m_differencesMean + "="
                        + m_differencesMeans[m_number] + " Dev Set Point:" + m_devSetPointMean
                        + "=" + m_devSetPointMeans[m_number]);
                    //                    System.out.println("I=" + n + "Dev Mean:" + deviationMean + "="
                    //                            + deviationMeans[number] + " Diff Mean:" + differencesMean + "="
                    //                            + differencesMeans[number] + " Dev Set Point:" + devSetPointMean
                    //                            + "=" + devSetPointMeans[number]);
                    break;
                }
            }
            refreshData(); //empty all arrays and numbers that have to do with a
            //single p value and lots of set points
        }
        m_run = true;
    }

    @Override
    public boolean isFinished() {
        return m_run;
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.disablePID();
        m_shooter.stopEncoder();
        m_shooter.stopJags();
        int indexDevLow = getIndexofLowest(m_deviationMeans);
        int indexDiffLow = getIndexofLowest(m_differencesMeans);
        System.out.println("At val=" + ((indexDevLow * STEP) + STEP) + "Lowest Deviation Mean:"
            + m_deviationMeans[indexDevLow] + " Difference Mean:"
            + m_differencesMeans[indexDevLow] + " Deviation Set Point:"
            + m_devSetPointMeans[indexDevLow]);
        System.out.println("At val=" + ((indexDiffLow * STEP) + STEP) + "Lowest Difference Mean:"
            + m_differencesMeans[indexDiffLow] + " Deviation Mean:"
            + m_deviationMeans[indexDiffLow] + " Deviation Set Point:"
            + m_devSetPointMeans[indexDiffLow]);
        double bestVal = getBestVal();
        System.out.println("'Best val' (within range & lowest deviation):" + bestVal);
        int indexDevSetPointLow = getIndexofLowest(m_devSetPointMeans);
        System.out.println("At val=" + ((indexDevSetPointLow * STEP) + STEP) + "Lowest "
            + "Deviation Set Point:" + m_devSetPointMeans[indexDevSetPointLow]
            + " Difference Mean:" + m_differencesMeans[indexDevSetPointLow]
            + " Deviation Mean:" + m_deviationMeans[indexDevSetPointLow]);
    }




    //adds the encoder rates of the shooter wheel to an array -> it only has 100
    //spaces so once those 100 are filled up, it starts filling up spots starting
    //from the beginning (0)
    public void addRatesToArray(double rate) {
        m_rates[m_counter] = rate;
        m_counter++;
        if (m_counter >= m_rates.length) {
            m_counter = 0;
        }
    }

    //adds all the rates in the array then divides by the number of items in the array
    public double calculateRateMean() {
        m_totalRates = 0;
        m_rateSum = 0;
        m_rateMean = 0;
        for (double rate : m_rates) {
            if (rate != 0) {
                m_rateSum += rate;
                m_totalRates++; //counter to see how many of spots in the array
                //have information in them -> if not, it is not used in any
                //calculations
            }
        }
        m_rateMean = m_rateSum / m_totalRates;
        System.out.println("Rate Sum:" + m_rateSum + " & Rate Mean:" + m_rateMean);
        return m_rateMean;
    }

    public double getRateMean() {
        return m_rateMean;
    }

    //how close to the mean all the rates are -> we want this to be as small as 3possible
    public double calculateStandardDeviation() {
        m_differenceSum = 0;
        m_standardDeviation = 0;
        for (int j = 0; j < m_totalRates; j++) {
            m_variences[j] = (m_rates[j] - m_rateMean) * (m_rates[j] - m_rateMean); //calculates and stores the
            //square of the difference between the rate and the rate mean
        }
        for (int j = 0; j < m_totalRates; j++) {
            m_differenceSum += m_variences[j]; //adds all the differences together
        }
        m_standardDeviation = Math.sqrt(m_differenceSum) / m_totalRates;
        return m_standardDeviation;
    }

    public double getDifferenceSum() {
        return m_differenceSum;
    }

    public double calculateDeviationData(double setPoint) {
        for (int j = 0; j < 100; j++) {
            if (m_shooter.getEncoderRate() < setPoint + 1 && m_shooter.getEncoderRate() > setPoint - 1) {
                break;
            }
        }
        for (int j = 0; j < 100; j++) {
            addRatesToArray(m_shooter.getEncoderRate());
        }
        calculateRateMean();
        m_setPointToRateMean = setPoint - m_rateMean;
        return calculateStandardDeviation();
    }

    protected double calculateMean(double... array) {
        double sum = 0;
        double mean;
        for (int i = 0; i < m_count; i++) {
            sum += array[i];
        }
        mean = sum / (double) m_count;
        return mean;
    }

    protected void refreshData() {
        m_count = 0;
        for (int i = 0; i < DATA_LENGTH; i++) {
            m_deviations[i] = 0;
            m_differences[i] = 0;
            m_devSetPoints[i] = 0;
        }
        m_deviationMean = 0;
        m_differencesMean = 0;
        m_devSetPointMean = 0;
        m_counter = 0;
        m_totalRates = 0;
        for (int i = 0; i < m_rates.length; i++) {
            m_rates[i] = 0.0;
            m_variences[i] = 0.0;
        }
        m_rateSum = 0;
        m_differenceSum = 0;
        m_rateMean = 0;
        m_standardDeviation = 0;
        m_setPointToRateMean = 0;
    }

    protected int getIndexofLowest(double... array) {
        int index = 0;
        double lowest = 999999999;
        for (int i = 0; i < array.length; i++) {
            if (lowest > array[i]) {
                lowest = array[i];
                index = i;
            }
        }
        return index;
    }

    @SuppressWarnings("PMD.CollapsibleIfStatements")
    protected double getBestVal() {
        double p;
        double lowestDev = 999999999;
        double indexDev = 0;
        for (int i = 0; i < m_count - 1; i++) {
            if (m_deviationMeans[i] != 0 && Math.abs(m_differencesMeans[i]) <= ERROR) {
                if (lowestDev > m_deviationMeans[i]) {
                    lowestDev = m_deviationMeans[i];
                    indexDev = i;
                }
            }
        }
        p = (indexDev * STEP) + STEP;
        return p;
    }

    protected double deviationFromSetPoint(double setPoint) {
        double deviationSetPoint = 0;
        for (int i = 0; i < m_totalRates; i++) {
            deviationSetPoint = Math.sqrt((m_rates[i] - setPoint) * (m_rates[i] - setPoint)) / m_totalRates;
        }
        return deviationSetPoint;
    }

}
