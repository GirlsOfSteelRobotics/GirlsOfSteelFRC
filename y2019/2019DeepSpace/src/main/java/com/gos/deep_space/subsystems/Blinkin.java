package com.gos.deep_space.subsystems;

import com.gos.deep_space.RobotMap;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Blinkin extends Subsystem {
    private Spark leftLightController;
    private Spark left2LightController;
    private Spark rightLightController;

    private static final double CONFETTI = -0.87;
    private static final double RAINBOW_WITH_GLITTER = -0.89;
    private static final double COLOR_WAVES = 0.53;
    private static final double GREEN = .77;
    private static final double BLUE = .87;
    private static final double RED = .61;
    private static final double PURPLE = .91;

    public enum LightPattern {
        AUTO_DEFAULT, TELEOP_DEFAULT, FORTY_CLIMB, THIRTY_CLIMB, CLIMBER_THIRD, CLIMBER_SECOND,
    }

    public Blinkin() {
        try {
            rightLightController = new Spark(RobotMap.BLINKIN_RIGHT_PWM);
            System.out.println("Right Light Controller"); // this never gets made no matter what
        } catch (Exception e) {
            System.out.println("No blink-in in " + RobotMap.BLINKIN_RIGHT_PWM + ", error: " + e);
            rightLightController = null;
        }

        try {
            leftLightController = new Spark(RobotMap.BLINKIN_LEFT_PWM);
            System.out.println("Left Light Controller");
        } catch (Exception e) {
            System.out.println("No blink-in in " + RobotMap.BLINKIN_LEFT_PWM + ", error: " + e);
            leftLightController = null;
        }

        try {
            left2LightController = new Spark(RobotMap.BLINKIN_LEFT2_PWN);
            System.out.println("Left 2 Light Controller");
        } catch (Exception e) {
            System.out.println("No blink in in " + RobotMap.BLINKIN_LEFT2_PWN + ", error: " + e);
            left2LightController = null;
        }
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void setLightPattern(LightPattern pattern) {
        switch (pattern) {
        case AUTO_DEFAULT:
            if (leftLightController != null) {
                leftLightController.set(CONFETTI);
            }
            if (left2LightController != null) {
                left2LightController.set(CONFETTI);
            }
            if (rightLightController != null) {
                rightLightController.set(CONFETTI);
            }
            break;
        case TELEOP_DEFAULT:
            if (leftLightController != null) {
                leftLightController.set(RAINBOW_WITH_GLITTER);
            }
            if (left2LightController != null) {
                left2LightController.set(RAINBOW_WITH_GLITTER);
            }
            if (rightLightController != null) {
                rightLightController.set(RAINBOW_WITH_GLITTER);
            }
            break;
        case FORTY_CLIMB:
            if (leftLightController != null) {
                leftLightController.set(BLUE);
            }
            if (left2LightController != null) {
                left2LightController.set(BLUE);
            }
            if (rightLightController != null) {
                rightLightController.set(BLUE);
            }
            break;
        case THIRTY_CLIMB:
            if (leftLightController != null) {
                leftLightController.set(RED);
            }
            if (left2LightController != null) {
                left2LightController.set(RED);
            }
            if (rightLightController != null) {
                rightLightController.set(RED);
            }
        case CLIMBER_THIRD:
            if (leftLightController != null) {
                leftLightController.set(GREEN);
            }
            if (left2LightController != null) {
                left2LightController.set(GREEN);
            }
            if (rightLightController != null) {
                rightLightController.set(GREEN);
            }
        case CLIMBER_SECOND:
            if (leftLightController != null) {
                leftLightController.set(PURPLE);
            }
            if (left2LightController != null) {
                left2LightController.set(GREEN);
            }
            if (rightLightController != null) {
                rightLightController.set(GREEN);
            }
        default:
            break;
        }
    }
}
