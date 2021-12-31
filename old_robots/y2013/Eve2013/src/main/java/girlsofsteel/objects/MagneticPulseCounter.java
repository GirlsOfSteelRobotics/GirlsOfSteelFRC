/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.objects;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * @author user
 */
public class MagneticPulseCounter implements Runnable {

    private static final long timeLength = 50; //Times checked per second (10)
    private static final int relevant = 20; //How much data is kept

    private final DigitalInput m_digiPut;
    private int m_pulses;
    private static final boolean m_done = false;
    private int[] m_pulsesBetweenTime;
    private int m_counter = -1;

    public MagneticPulseCounter(int channel) {
        m_digiPut = new DigitalInput(channel);
    }

    @Override
    public void run() {
        m_pulsesBetweenTime = new int[relevant];
        new Thread() { // NOPMD
            @Override
            public void run() {
                while (!m_done) {
                    //False = received pulse
                    while (m_digiPut.get() && !m_done) {
                        Thread.yield();
                    }
                    m_pulses++;
                }
            }
        }.start();

        while (!m_done) {
            try {
                Thread.sleep(timeLength);
                m_counter++;
                m_pulsesBetweenTime[m_counter % relevant] = m_pulses;
                m_pulses = 0;
            } catch (InterruptedException ex) {
            }
        }
    }

    public double getRate() {
        double rate;
        int sum = 0;

        for (int i = 0; i < relevant; i++) {
            sum += m_pulsesBetweenTime[i];
        }

        rate = (sum / (double) (relevant * timeLength)) * 1000;
        return rate;
    }
}
