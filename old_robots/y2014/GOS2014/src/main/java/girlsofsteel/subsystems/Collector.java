/*
 * CHANGE TO NOT USE AN ENCODER
 */

package girlsofsteel.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import girlsofsteel.Configuration;
import girlsofsteel.RobotMap;

/**
 * This class is for the subsystem Collector.
 *
 * @author Heather, Sonia, Sophia
 */
public class Collector extends Subsystem {

    /**
     * This spike moves the wheel on the collector arm.
     *
     * @author Sonia, Sophia
     */
    private final Relay m_collectorWheelSpike;

    /**
     * this is the jag that moves the collector up and down to collect or release the ball.
     *
     * @author Sonia, Sophia
     */
    private final Jaguar m_collectorJag;


    /**
     * This method initializes the variable for this class.
     *
     * @author Sonia, Sophia
     */
    public Collector() {
        m_collectorWheelSpike = new Relay(RobotMap.COLLECTOR_WHEEL_SPIKE);
        m_collectorJag = new Jaguar(RobotMap.COLLECTOR_JAG);
    }


    public void moveCollectorToZJoystick(double zVertical) {
        m_collectorJag.set(zVertical);
    }

    /**
     * This method sets the wheel spike to the forward setting
     *
     * @author Sonia, Sophia
     */
    public void collectorWheelFoward() {
        m_collectorWheelSpike.set(Configuration.collectorWheelForwardSpeed);
    } //Set the wheel spike to the forward setting

    /**
     * This method sets the wheel spike to the reverse setting
     *
     * @author Sonia, Sophia
     */
    public void collectorWheelReverse() {
        m_collectorWheelSpike.set(Configuration.collectorWheelBackwardSpeed);
    }//set the wheel spike to the reverse setting

    /**
     * This method stops the wheel from spinning
     *
     * @author Sonia, Sophia
     */
    public void stopCollectorWheel() {
        m_collectorWheelSpike.set(Relay.Value.kOff);
    } //stops the wheel from spinning

    public void moveCollectorUpOrDown(double collectorJagSpeed) {
        m_collectorJag.set(collectorJagSpeed);
    }//Sets/returns the speed of the collector jag as it moves to engage the ball

    public void stopCollector() {
        m_collectorJag.set(0.0);
    }//Stops the collector jag

    public double getCollectorSpeed() {
        return m_collectorJag.getSpeed();
    }

    /**
     * There is nothing in this method
     *
     * @author Sonia, Sophia
     */
    @Override
    protected void initDefaultCommand() {
    }
}
