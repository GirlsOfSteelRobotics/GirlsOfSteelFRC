/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.objects;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * @author user
 */
public class MagneticPulseCounter implements Runnable {

    private static final long TIME_LENGTH = 50; //Times checked per second (10)
    private static final int RELEVANT = 20; //How much data is kept

    private final DigitalInput m_digiPut;
    private int m_pulses;
    private static final boolean DONE = false;
    private int[] m_pulsesBetweenTime;
    private int m_counter = -1;

    public MagneticPulseCounter(int channel) {
        m_digiPut = new DigitalInput(channel);
    }

    @Override
    public void run() {
        m_pulsesBetweenTime = new int[RELEVANT];
        new Thread() { // NOPMD
            @Override
            public void run() {
                while (!DONE) {
                    //False = received pulse
                    while (m_digiPut.get() && !DONE) {
                        Thread.yield();
                    }
                    m_pulses++;
                }
            }
        }.start();

        while (!DONE) {
            try {
                Thread.sleep(TIME_LENGTH);
                m_counter++;
                m_pulsesBetweenTime[m_counter % RELEVANT] = m_pulses;
                m_pulses = 0;
            } catch (InterruptedException ex) {
                ex.printStackTrace(); // NOPMD
            }
        }
    }

    public double getRate() {
        double rate;
        int sum = 0;

        for (int i = 0; i < RELEVANT; i++) {
            sum += m_pulsesBetweenTime[i];
        }

        rate = (sum / (double) (RELEVANT * TIME_LENGTH)) * 1000;
        return rate;
    }
}
