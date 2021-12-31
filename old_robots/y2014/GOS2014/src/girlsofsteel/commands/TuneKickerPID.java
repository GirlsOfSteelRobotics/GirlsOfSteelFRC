/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.commands;

import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Kicker;

/**
 * @author Mackenzie
 */
public class TuneKickerPID extends CommandBase {
    private static final double startP = 0;
    private static final double maxP = 0.3; //max value of p
    private static final double incrementP = 0.05; //how much p goes up by

    private boolean m_done;

    private static final double setPoint = 0; //starting speed
    private static final double maxSetPoint = 5;  //find max chassis speed

    //for one setpoint:
    private final int m_lengthSetPoint = (int) (maxSetPoint - setPoint); //length of arraay
    private final double[] m_devSetPoint = new double[m_lengthSetPoint]; //deviation from setpoint
    private final double[] m_meanDiff = new double[m_lengthSetPoint]; //mean of difference between rate & mean rate
    private final double[] m_devRate = new double[m_lengthSetPoint]; //deviation from mean rate
    //means of the individual set point arrays
    //for each p value
    private final int m_lengthP = (int) ((maxP - startP) / incrementP);
    private final double[] m_meanDevSetPoint = new double[m_lengthP];


    private final double[] m_rates = new double[100];//rate of wheel spins
    private double m_mean; //the average.

    private final Chassis m_chassis;
    private final Kicker m_kicker;

    public TuneKickerPID(Chassis chassis, Kicker kicker) {
        m_chassis = chassis;
        m_kicker = kicker;
        requires(m_chassis);
    }

    @Override
    protected void initialize() {
        m_kicker.initPIDS();
        m_kicker.initEncoders();
    }

    @Override
    protected void execute() {
        for (double a = startP; a < maxP; a += incrementP) {
            //for(double a = startI; < maxI; a += incrementI)
            for (double b = setPoint; b < maxSetPoint; b++) {
                m_chassis.resetPositionPIDError();
                m_chassis.setLeftPositionPIDValues(a, 0, 0);
                //chassis.setLeftPIDValues(p, a, 0);
                m_chassis.setLeftPIDPosition(b);
                System.out.println("Speed: " + m_chassis.getLeftEncoderDistance());
                System.out.println("B:" + b);
                //resets PID, sets value of p to a, sets pid speed to b
                /*
                for each subpoint of p find the
                deviations from the setpoint
                deviation from rate as well
                get mean between difference of rate and mean rate
                */
                for (int c = 0; c < m_lengthSetPoint; c++) {
                    m_devSetPoint[c] = getDeviation(b, m_rates);
                    m_meanDiff[c] = getDifference(getMean(m_rates));
                    m_devRate[c] = getDeviation(0.0, m_rates);
                }
                System.out.println("At the end of one setpoint");
            }

            for (int d = 0; d < m_lengthP; d += incrementP) {
                //for(int d = 0; d < lengthI; d += incrementI)
                //Do it once then break TODO
                if (m_meanDevSetPoint[d] == 0) {
                    m_meanDevSetPoint[d] = getMean(m_devSetPoint);
                    m_meanDiff[d] = getMean(m_meanDiff);
                    //d=lengthP;
                    // d=lengthI
                    break;
                }
            }
            emptyArrays();
            System.out.println("Finished one p.");
        }

        m_done = true;
    }

    @Override
    protected boolean isFinished() {
        return m_done;
    }


    @Override
    protected void end() {
        getBest();
        m_chassis.stopJags();
        m_chassis.disablePositionPID();
        //print the good p and setpoint values

    }

    @Override
    protected void interrupted() {
        end();
    }

    private double getMean(double... array) {

        for (double v : array) {
            m_mean += v;
        }
        m_mean /= array.length;
        return m_mean;
    }

    private double getVariance(double center) {
        double[] numbers = new double[100];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = (m_rates[i] - center) * (m_rates[i] - center);

        }
        return getMean(numbers);
    }

    private double getDeviation(double center, double... array) {
        double average = center;
        if (center == 0) {
            average = getMean(array);
        }
        double variance = getVariance(average);
        return Math.sqrt(variance);
    }

    /*
    mean of the difference between the rate and mean rate
    */
    private double getDifference(double center) {
        double[] differences = new double[100];
        for (int i = 0; i < differences.length; i++) {
            differences[i] = (m_rates[i] - center);
        }
        return getMean(differences);
    }

    public void emptyArrays() {
        for (int i = 0; i < m_lengthSetPoint; i++) {
            m_devRate[i] = 0;
            m_devSetPoint[i] = 0;
            m_meanDiff[i] = 0;

        }
    }

    public void getBest() { // NOPMD(LinguisticNaming)
        double lowestSetPointDev = 100;
        double lowestRateDev = 100;
        double lowestMeanDiff = 100;

        double pLowestSetPointDev = 0;
        double pLowestRateDev = 0;
        double pLowestMeanDiff = 0;

        for (int j = 0; j < m_devSetPoint.length; j++) {
            if (m_devSetPoint[j] < lowestSetPointDev) {
                lowestSetPointDev = m_devSetPoint[j];
            }
            pLowestSetPointDev = startP + (j * incrementP);
            // pLowestSetPointDev = startI + (j * incrementI);
        }
        for (int k = 0; k < m_devRate.length; k++) {
            if (m_devRate[k] < lowestRateDev) {
                lowestRateDev = m_devRate[k];
            }
            pLowestRateDev = startP + (k * incrementP);
            // pLowestRateDev = startI + (k * incrementI);
        }
        for (int m = 0; m < m_meanDiff.length; m++) {
            if (m_meanDiff[m] < lowestMeanDiff) {
                lowestMeanDiff = m_meanDiff[m];
            }
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
