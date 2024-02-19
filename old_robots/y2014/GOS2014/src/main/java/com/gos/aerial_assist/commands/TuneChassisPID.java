/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import com.gos.aerial_assist.subsystems.Chassis;

/**
 * @author Mackenzie
 */
public class TuneChassisPID extends GosCommandBaseBase {
    private static final double START_P = 0;
    private static final double MAX_P = 0.3; //max value of p
    private static final double INCREMENT_P = 0.05; //how much p goes up by

    private boolean m_done;

    private static final double SET_POINT = 0; //starting speed
    private static final double MAX_SET_POINT = 5;  //find max chassis speed

    //for one setpoint:
    private static final int LENGTH_SET_POINT = (int) (MAX_SET_POINT - SET_POINT); //length of arraay
    private final double[] m_devSetPoint = new double[LENGTH_SET_POINT]; //deviation from setpoint
    private final double[] m_meanDiff = new double[LENGTH_SET_POINT]; //mean of difference between rate & mean rate
    private final double[] m_devRate = new double[LENGTH_SET_POINT]; //deviation from mean rate
    //means of the individual set point arrays
    //for each p value
    private static final int LENGTH_P = (int) ((MAX_P - START_P) / INCREMENT_P);
    private final double[] m_meanDevSetPoint = new double[LENGTH_P];


    private final double[] m_rates = new double[100]; //rate of wheel spins
    private double m_mean; //the average.
    private final Chassis m_chassis;

    public TuneChassisPID(Chassis chassis) {
        m_chassis = chassis;
        addRequirements(m_chassis);
    }

    @Override
    public void initialize() {
        m_chassis.initPositionPIDS();
        m_chassis.initEncoders();
    }

    @Override
    public void execute() {
        for (double a = START_P; a < MAX_P; a += INCREMENT_P) {
            //for(double a = startI; < maxI; a += incrementI)
            for (double b = SET_POINT; b < MAX_SET_POINT; b++) {
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
                for (int c = 0; c < LENGTH_SET_POINT; c++) {
                    m_devSetPoint[c] = getDeviation(b, m_rates);
                    m_meanDiff[c] = getDifference(getMean(m_rates));
                    m_devRate[c] = getDeviation(0.0, m_rates);
                }
                System.out.println("At the end of one setpoint");
            }

            for (int d = 0; d < LENGTH_P; d += INCREMENT_P) {
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
    public boolean isFinished() {
        return m_done;
    }


    @Override
    public void end(boolean interrupted) {
        printBest();
        m_chassis.stopJags();
        m_chassis.disablePositionPID();
        //print the good p and setpoint values

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
        for (int i = 0; i < LENGTH_SET_POINT; i++) {
            m_devRate[i] = 0;
            m_devSetPoint[i] = 0;
            m_meanDiff[i] = 0;

        }
    }

    public void printBest() {
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
            pLowestSetPointDev = START_P + (j * INCREMENT_P);
            // pLowestSetPointDev = startI + (j * incrementI);
        }
        for (int k = 0; k < m_devRate.length; k++) {
            if (m_devRate[k] < lowestRateDev) {
                lowestRateDev = m_devRate[k];
            }
            pLowestRateDev = START_P + (k * INCREMENT_P);
            // pLowestRateDev = startI + (k * incrementI);
        }
        for (int m = 0; m < m_meanDiff.length; m++) {
            if (m_meanDiff[m] < lowestMeanDiff) {
                lowestMeanDiff = m_meanDiff[m];
            }
            pLowestMeanDiff = START_P + (m * INCREMENT_P);
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
