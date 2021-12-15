/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.objects;

import edu.wpi.first.wpilibj.Encoder;

public class SmoothEncoder extends Encoder {
    private final int NUMBER_OF_VALUES = 10;
    private double[] values = new double[NUMBER_OF_VALUES];
    private int totalDataPoints = 0;

    public SmoothEncoder(int channelA, int channelB, boolean reverse, EncodingType type){
        super (channelA, channelB, reverse, type);
    }

    public void start() {
        totalDataPoints = 0;
    }

    @Override
    public double getRate() {
        for (int i = NUMBER_OF_VALUES-1; i > 0; i --){ // NOPMD(AvoidArrayLoops)
            values[i] = values[i-1];
        }
        values[0]=super.getRate();

        if (totalDataPoints < NUMBER_OF_VALUES){
            totalDataPoints ++;
        }

        double sum = 0;
        for(int i = 0; i < totalDataPoints; i ++){
            sum += values[i];
        }
        return sum/totalDataPoints;
    }

}
