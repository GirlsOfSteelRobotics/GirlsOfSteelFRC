/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.objects;

import edu.wpi.first.wpilibj.Encoder;

public class SmoothEncoder extends Encoder {
    private static final int NUMBER_OF_VALUES = 10;
    private double[] m_values = new double[NUMBER_OF_VALUES];
    private int m_totalDataPoints;

    public SmoothEncoder(int channelA, int channelB, boolean reverse, EncodingType type) {
        super(channelA, channelB, reverse, type);
    }

    public void start() {
        m_totalDataPoints = 0;
    }

    @Override
    public double getRate() {
        for (int i = NUMBER_OF_VALUES - 1; i > 0; i--) { // NOPMD(AvoidArrayLoops)
            m_values[i] = m_values[i - 1];
        }
        m_values[0] = super.getRate();

        if (m_totalDataPoints < NUMBER_OF_VALUES) {
            m_totalDataPoints++;
        }

        double sum = 0;
        for (int i = 0; i < m_totalDataPoints; i++) {
            sum += m_values[i];
        }
        return sum / m_totalDataPoints;
    }

}
