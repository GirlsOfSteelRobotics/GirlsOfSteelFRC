/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel;

import edu.wpi.first.wpilibj.Relay;
import girlsofsteel.commands.CommandBase;

/**
 *
 * @author Sylvie
 *
 * Note: The default for all values is the COMPETITION robot
 *
 */
public class Configuration {

    public static final int PRACTICE_ROBOT = 2;
    public static final int COMPETITION_ROBOT = 1;

    public static boolean rightEncoderReverse = false;
    public static boolean leftEncoderReverse = true;
    public static double leftPositionP = 1.3;
    public static double rightPositionP = 1.3;
    public static double desiredAnglePivotArmSign = 1.0; //Just a multiplier to flip the sign
    public static double disengageCollectorSpeed = 1;
    public static double engageCollectorSpeed = -1;
    //Old competition arm p
    public static double manipulatorPivotP = -0.12;
    public static int leftSetpointSign = -1; //competition robot
    public static int rightSetpointSign = 1; //competition robot
    public static boolean leftPIDReverseEncoder = true; //competition bot
    public static boolean rightPIDReverseEncoder = false; //competition bot
    public static Relay.Value collectorWheelForwardSpeed = Relay.Value.kForward;
    public static Relay.Value collectorWheelBackwardSpeed = Relay.Value.kReverse;
    public static int pivotEncoderZeroValue = 92;
    public static int signOfChassisPositionPIDSetpoint = 1;

    public static void configureForRobot(int robot) {
        if (robot == PRACTICE_ROBOT) {
            //Encoders are right- false, left- true
            pivotEncoderZeroValue = 86;
            rightEncoderReverse = false;
            CommandBase.chassis.setRightEncoderReverseDirection(rightEncoderReverse);
            leftEncoderReverse = true;
            CommandBase.chassis.setLeftEncoderReverseDirection(leftEncoderReverse);
            leftPositionP = .5; //These are still unknown
            rightPositionP = .5; //These are still unknown
            CommandBase.chassis.setLeftPositionPIDValues(leftPositionP, 0.0, 0.0);
            CommandBase.chassis.setRightPositionPIDValues(rightPositionP, 0.0, 0.0);
            signOfChassisPositionPIDSetpoint = 1;
            desiredAnglePivotArmSign = 1.0;
            disengageCollectorSpeed = 1.0;
            engageCollectorSpeed = -1.0;
            manipulatorPivotP = 0.1; //as of 3/17/14
            CommandBase.manipulator.setPID(manipulatorPivotP, 0.0, 0.0);
            collectorWheelBackwardSpeed = Relay.Value.kForward; //Found 3/17 that it's swtiched
            collectorWheelForwardSpeed = Relay.Value.kReverse;
        } else if (robot == COMPETITION_ROBOT) {
            rightEncoderReverse = false;
            leftEncoderReverse = true;
            pivotEncoderZeroValue = 82;
            leftPositionP = .45;
            rightPositionP = .45;
            CommandBase.chassis.setLeftPositionPIDValues(leftPositionP, 0.0, 0.0);
            CommandBase.chassis.setRightPositionPIDValues(rightPositionP, 0.0, 0.0);
            desiredAnglePivotArmSign = 1.0; //Just a multiplier to flip the sign
            disengageCollectorSpeed = 1.0;
            engageCollectorSpeed = -1.0;
            manipulatorPivotP = -0.12;
            CommandBase.manipulator.setPID(manipulatorPivotP, 0.0, 0.0);
            collectorWheelForwardSpeed = Relay.Value.kReverse;
            collectorWheelBackwardSpeed = Relay.Value.kForward;
        }
        //printAllValues(robot);
    }

    private static void printAllValues(int robot) {
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
