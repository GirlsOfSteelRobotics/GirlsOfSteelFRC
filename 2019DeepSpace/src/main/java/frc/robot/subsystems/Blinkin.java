package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Blinkin extends Subsystem {
    private Spark leftLightController;
    private Spark left2LightController;
    private Spark rightLightController;

    private static final double CONFETTI = -0.87;
    private static final double RAINBOW_WITH_GLITTER = -0.89;
    private static final double COLOR_WAVES = 0.53;

    public enum LightPattern {
        AUTO_DEFAULT, TELEOP_DEFAULT, END_GAME,
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

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public void setLightPattern(LightPattern pattern) {
        switch (pattern) {
        case AUTO_DEFAULT:
            if (leftLightController != null)
                leftLightController.set(CONFETTI);
            if (left2LightController != null)
                left2LightController.set(CONFETTI);
            if (rightLightController != null)
                rightLightController.set(CONFETTI);
            break;
        case TELEOP_DEFAULT:
            if (leftLightController != null)
                leftLightController.set(RAINBOW_WITH_GLITTER);
            if (left2LightController != null)
                left2LightController.set(RAINBOW_WITH_GLITTER);
            if (rightLightController != null)
                rightLightController.set(RAINBOW_WITH_GLITTER);
            break;
        case END_GAME:
            if (leftLightController != null)
                leftLightController.set(COLOR_WAVES);
            if (left2LightController != null)
                left2LightController.set(COLOR_WAVES);
            if (rightLightController != null)
                rightLightController.set(COLOR_WAVES);
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
