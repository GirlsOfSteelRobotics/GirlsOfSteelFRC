package girlsofsteel.testbot.subsystems;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import girlsofsteel.testbot.RobotMap;

public class Chassis extends Subsystem {

    private static final double TICKS_PER_ROTATION = 250.0;

    private final Jaguar rightFront = new Jaguar(RobotMap.rightFrontJag);
    private final Jaguar rightBack = new Jaguar(RobotMap.rightBackJag);

    private Encoder rightEncoder = new Encoder(RobotMap.rightEncoderAChannel,
            RobotMap.rightEncoderBChannel, false, CounterBase.EncodingType.k4X);

    public Chassis() {
        rightEncoder.start();
    }

    public void initDefaultCommand() {
    }

    /**
     * Sets the speed of the right side wheels.
     * @param speed the speed of the wheel, from -1 to 1
     */
    public void setRight(double speed){
        rightFront.set(-speed);
        rightBack.set(-speed);
    }

    /**
     * Returns the rotation of the right wheels.  Each forward rotation
     * is 1.
     * @return the rotation of the right wheels.
     * @deprecated untested
     */
    public double getRotationsOfRightWheels() {
        return rightEncoder.getDistance() / TICKS_PER_ROTATION;
    }

    /**
     * Returns the rate of the right wheels in rotations per second.
     * @return the rate of the right wheels
     * @deprecated untested
     */
    public double getRateOfRightWheels() {
        return rightEncoder.getRate();
    }
}
