/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.objects;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 *
 * @author user
 */
public class MagneticPulseCounter implements Runnable {

    private final DigitalInput digiPut;
    private int pulses;
    private final boolean done = false;
    private int[] pulsesBetweenTime;
    private int counter = -1;
    private final static long timeLength = 50; //Times checked per second (10)
    private final static int relevant = 20; //How much data is kept

    public MagneticPulseCounter(int channel) {
        digiPut = new DigitalInput(channel);
    }

    @Override
    public void run() {
        pulsesBetweenTime = new int[relevant];
        new Thread() {
            @Override
            public void run() {
                while (!done) {
                    //False = received pulse
                    while (digiPut.get() && !done) {
                        Thread.yield();
                    }
                    pulses++;
                }
            }
        }.start();

        while (!done) {
            try {
                Thread.sleep(timeLength);
                counter++;
                pulsesBetweenTime[counter % relevant] = pulses;
                pulses = 0;
            } catch (InterruptedException ex) {
            }
        }
    }

    public double getRate() {
        double rate = 0.0;
        int sum = 0;

        for (int i = 0; i < relevant; i++) {
            sum += pulsesBetweenTime[i];
        }

        rate = (sum / (double) (relevant*timeLength))*1000;
        return rate;
    }
}
