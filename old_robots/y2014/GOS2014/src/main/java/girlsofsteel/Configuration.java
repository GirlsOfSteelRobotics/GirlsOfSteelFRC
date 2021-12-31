/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel;

import edu.wpi.first.wpilibj.Relay;

/**
 * @author Sylvie
 * <p>
 * Note: The default for all values is the COMPETITION robot
 */
public class Configuration {

    private static final int PRACTICE_ROBOT = 2;
    private static final int COMPETITION_ROBOT = 1;

    private static final int ACTIVE_ROBOT = COMPETITION_ROBOT;

    public static final boolean rightEncoderReverse;
    public static final boolean leftEncoderReverse;
    public static final double leftPositionP;
    public static final double rightPositionP;
    public static final double desiredAnglePivotArmSign; //Just a multiplier to flip the sign
    public static final double disengageCollectorSpeed;
    public static final double engageCollectorSpeed;
    //Old competition arm p
    public static final double manipulatorPivotP;
    public static final int leftSetpointSign = -1; //competition robot
    public static final int rightSetpointSign = 1; //competition robot
    public static final boolean leftPIDReverseEncoder = true; //competition bot
    public static final boolean rightPIDReverseEncoder = false; //competition bot
    public static final Relay.Value collectorWheelForwardSpeed;
    public static final Relay.Value collectorWheelBackwardSpeed;
    public static final int pivotEncoderZeroValue;
    public static final int signOfChassisPositionPIDSetpoint;

    static {
        if (ACTIVE_ROBOT == PRACTICE_ROBOT) {
            //Encoders are right- false, left- true
            pivotEncoderZeroValue = 86;
            rightEncoderReverse = false;
            leftEncoderReverse = true;
            leftPositionP = .5; //These are still unknown
            rightPositionP = .5; //These are still unknown
            signOfChassisPositionPIDSetpoint = 1;
            desiredAnglePivotArmSign = 1.0;
            disengageCollectorSpeed = 1.0;
            engageCollectorSpeed = -1.0;
            manipulatorPivotP = 0.1; //as of 3/17/14
            collectorWheelBackwardSpeed = Relay.Value.kForward; //Found 3/17 that it's swtiched
            collectorWheelForwardSpeed = Relay.Value.kReverse;
        } else if (ACTIVE_ROBOT == COMPETITION_ROBOT) {
            rightEncoderReverse = false;
            leftEncoderReverse = true;
            pivotEncoderZeroValue = 82;
            leftPositionP = .45;
            rightPositionP = .45;
            signOfChassisPositionPIDSetpoint = 1;
            desiredAnglePivotArmSign = 1.0; //Just a multiplier to flip the sign
            disengageCollectorSpeed = 1.0;
            engageCollectorSpeed = -1.0;
            manipulatorPivotP = -0.12;
            collectorWheelForwardSpeed = Relay.Value.kReverse;
            collectorWheelBackwardSpeed = Relay.Value.kForward;
        }
        //printAllValues(robot);
    }

    public static void printAllValues(int robot) {
        System.out.println("Robot Number: " + robot);
        System.out.println("Left Encoder Reverse: " + leftEncoderReverse);
        System.out.println("Right Encoder Reverse: " + rightEncoderReverse);
        System.out.println("Left Position P: " + leftPositionP);
        System.out.println("Right Position P: " + rightPositionP);
        System.out.println("Desired Angle Sign: " + desiredAnglePivotArmSign);
        System.out.println("Disengage Collector Speed: " + disengageCollectorSpeed);
        System.out.println("Engage Collector Speed: " + engageCollectorSpeed);
        System.out.println("Manipulator Pivot P: " + manipulatorPivotP);
    }

}
