package com.gos.deep_space.subsystems;

import com.gos.deep_space.RobotMap;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Blinkin extends Subsystem {
    private static final double CONFETTI = -0.87;
    private static final double RAINBOW_WITH_GLITTER = -0.89;
    private static final double GREEN = .77;
    private static final double BLUE = .87;
    private static final double RED = .61;
    private static final double PURPLE = .91;

    private Spark m_leftLightController;
    private Spark m_left2LightController;
    private Spark m_rightLightController;


    public enum LightPattern {
        AUTO_DEFAULT, TELEOP_DEFAULT, FORTY_CLIMB, THIRTY_CLIMB, CLIMBER_THIRD, CLIMBER_SECOND,
    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public Blinkin() {
        try {
            m_rightLightController = new Spark(RobotMap.BLINKIN_RIGHT_PWM);
            System.out.println("Right Light Controller"); // this never gets made no matter what
        } catch (Exception e) {
            System.out.println("No blink-in in " + RobotMap.BLINKIN_RIGHT_PWM + ", error: " + e);
        }

        try {
            m_leftLightController = new Spark(RobotMap.BLINKIN_LEFT_PWM);
            System.out.println("Left Light Controller");
        } catch (Exception e) {
            System.out.println("No blink-in in " + RobotMap.BLINKIN_LEFT_PWM + ", error: " + e);
        }

        try {
            m_left2LightController = new Spark(RobotMap.BLINKIN_LEFT2_PWN);
            System.out.println("Left 2 Light Controller");
        } catch (Exception e) {
            System.out.println("No blink in in " + RobotMap.BLINKIN_LEFT2_PWN + ", error: " + e);
        }
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    @SuppressWarnings("PMD.CyclomaticComplexity")
    public void setLightPattern(LightPattern pattern) {
        switch (pattern) {
        case AUTO_DEFAULT:
            if (m_leftLightController != null) {
                m_leftLightController.set(CONFETTI);
            }
            if (m_left2LightController != null) {
                m_left2LightController.set(CONFETTI);
            }
            if (m_rightLightController != null) {
                m_rightLightController.set(CONFETTI);
            }
            break;
        case TELEOP_DEFAULT:
            if (m_leftLightController != null) {
                m_leftLightController.set(RAINBOW_WITH_GLITTER);
            }
            if (m_left2LightController != null) {
                m_left2LightController.set(RAINBOW_WITH_GLITTER);
            }
            if (m_rightLightController != null) {
                m_rightLightController.set(RAINBOW_WITH_GLITTER);
            }
            break;
        case FORTY_CLIMB:
            if (m_leftLightController != null) {
                m_leftLightController.set(BLUE);
            }
            if (m_left2LightController != null) {
                m_left2LightController.set(BLUE);
            }
            if (m_rightLightController != null) {
                m_rightLightController.set(BLUE);
            }
            break;
        case THIRTY_CLIMB:
            if (m_leftLightController != null) {
                m_leftLightController.set(RED);
            }
            if (m_left2LightController != null) {
                m_left2LightController.set(RED);
            }
            if (m_rightLightController != null) {
                m_rightLightController.set(RED);
            }
            break;
        case CLIMBER_THIRD:
            if (m_leftLightController != null) {
                m_leftLightController.set(GREEN);
            }
            if (m_left2LightController != null) {
                m_left2LightController.set(GREEN);
            }
            if (m_rightLightController != null) {
                m_rightLightController.set(GREEN);
            }
            break;
        case CLIMBER_SECOND:
            if (m_leftLightController != null) {
                m_leftLightController.set(PURPLE);
            }
            if (m_left2LightController != null) {
                m_left2LightController.set(GREEN);
            }
            if (m_rightLightController != null) {
                m_rightLightController.set(GREEN);
            }
            break;
        default:
            break;
        }
    }
}
