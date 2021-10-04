package com.gos.testboard2020.subsystems;

import com.gos.testboard2020.Constants;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Blinkin extends SubsystemBase {
    private Spark leftLightController;
    private Spark rightLightController;

    private static final double CONFETTI = -0.87;
    private static final double RAINBOW_WITH_GLITTER = -0.89;
    private static final double SOLID_GREEN = 0.77;

    public enum LightPattern {
        AUTO_DEFAULT, TELEOP_DEFAULT, HATCH_RELEASE,
    }

    public Blinkin() {
        try {
            rightLightController = new Spark(Constants.BLINKIN_RIGHT_PWM);
            System.out.println("Right Light Controller"); // this never gets made no matter what
        } catch (Exception e) {
            System.out.println("No blink-in in " + Constants.BLINKIN_RIGHT_PWM + ", error: " + e);
            rightLightController = null;
        }

        try {
            leftLightController = new Spark(Constants.BLINKIN_LEFT_PWM);
            System.out.println("Left Light Controller");
        } catch (Exception e) {
            System.out.println("No blink-in in " + Constants.BLINKIN_LEFT_PWM + ", error: " + e);
            leftLightController = null;
        }

    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void setLightPattern(LightPattern pattern) {
        switch (pattern) {
        case AUTO_DEFAULT:
            if (leftLightController != null)
                leftLightController.set(CONFETTI);
            if (rightLightController != null)
                rightLightController.set(CONFETTI);
            break;
        case TELEOP_DEFAULT:
            if (leftLightController != null)
                leftLightController.set(RAINBOW_WITH_GLITTER);
            if (rightLightController != null)
                rightLightController.set(RAINBOW_WITH_GLITTER);
            break;
        case HATCH_RELEASE:
            if (leftLightController != null)
                leftLightController.set(SOLID_GREEN);
            if (rightLightController != null)
                rightLightController.set(SOLID_GREEN);
            break;
        default:
            break;
        }
    }

    public void stop() {
        // leave the lights running, I guess
    }
}
