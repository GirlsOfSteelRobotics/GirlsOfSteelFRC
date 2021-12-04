/*
 * This is a class we made for the special speed control sensor.
 */
package girlsofsteel.objects;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 *
 * @author Sylvie
 */
public class MagneticSpeedSensor implements PIDSource {

    private MagneticPulseCounter pulseCounter;

    //Units: Pulses per second
    public MagneticSpeedSensor(int channel) {
        pulseCounter = new MagneticPulseCounter(channel);
        new Thread(pulseCounter).start();
    }

    public double get() {
        return pulseCounter.getRate();
    }

    public double pidGet() {
        return get();
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        throw new UnsupportedOperationException();
    }
}
