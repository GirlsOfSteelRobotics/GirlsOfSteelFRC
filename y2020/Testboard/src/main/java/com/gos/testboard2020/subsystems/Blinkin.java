package com.gos.testboard2020.subsystems;

import com.gos.testboard2020.Constants;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Blinkin extends SubsystemBase {
    private static final double CONFETTI = -0.87;
    private static final double RAINBOW_WITH_GLITTER = -0.89;
    private static final double SOLID_GREEN = 0.77;

    private Spark m_leftLightController;
    private Spark m_rightLightController;

    public enum LightPattern {
        AUTO_DEFAULT, TELEOP_DEFAULT, HATCH_RELEASE,
    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public Blinkin() {
        try {
            m_rightLightController = new Spark(Constants.BLINKIN_RIGHT_PWM);
            System.out.println("Right Light Controller"); // this never gets made no matter what
        } catch (Exception e) {
            System.out.println("No blink-in in " + Constants.BLINKIN_RIGHT_PWM + ", error: " + e);
            m_rightLightController = null;
        }

        try {
            m_leftLightController = new Spark(Constants.BLINKIN_LEFT_PWM);
            System.out.println("Left Light Controller");
        } catch (Exception e) {
            System.out.println("No blink-in in " + Constants.BLINKIN_LEFT_PWM + ", error: " + e);
            m_leftLightController = null;
        }

    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
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
            if (m_rightLightController != null) {
                m_rightLightController.set(CONFETTI);
            }
            break;
        case TELEOP_DEFAULT:
            if (m_leftLightController != null) {
                m_leftLightController.set(RAINBOW_WITH_GLITTER);
            }
            if (m_rightLightController != null) {
                m_rightLightController.set(RAINBOW_WITH_GLITTER);
            }
            break;
        case HATCH_RELEASE:
            if (m_leftLightController != null) {
                m_leftLightController.set(SOLID_GREEN);
            }
            if (m_rightLightController != null) {
                m_rightLightController.set(SOLID_GREEN);
            }
            break;
        default:
            break;
        }
    }

    public void stop() {
        // leave the lights running, I guess
    }
}
