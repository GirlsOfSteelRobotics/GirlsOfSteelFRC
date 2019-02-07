package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Blinkin extends Subsystem {
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
            leftLightController = new Spark(RobotMap.BLINKIN_LEFT_PWM);
        } catch (Exception e) {
            System.out.println("No blink-in in 0, error: " + e);
            leftLightController = null;
        }

        try {
            rightLightController = new Spark(RobotMap.BLINKIN_LEFT_PWM);
        } catch (Exception e) {
            System.out.println("No blink-in in 1, error: " + e);
            rightLightController = null;
        }
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public void setLightPattern(LightPattern pattern) {
        switch (pattern) {
        case AUTO_DEFAULT:
            if (leftLightController != null) leftLightController.set(CONFETTI);
            if (rightLightController != null) rightLightController.set(CONFETTI);
            break;
        case TELEOP_DEFAULT:
            if (leftLightController != null) leftLightController.set(RAINBOW_WITH_GLITTER);
            if (leftLightController != null) rightLightController.set(RAINBOW_WITH_GLITTER);
            break;
        case HATCH_RELEASE:
            if (leftLightController != null)leftLightController.set(SOLID_GREEN);
            if (leftLightController != null) rightLightController.set(SOLID_GREEN);
            break;
        default:
            break;
        }
    }

    public void setOutputValue() {

    }

    public void stop() {

    }
}
