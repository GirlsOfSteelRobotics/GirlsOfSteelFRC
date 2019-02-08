package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Blinkin extends Subsystem {
    private Spark leftLightController;
    private Spark rightLightController;

    private static final double CONFETTI = -0.87;
    private static final double RED = -0.85;
    private static final double SOLID_GREEN = 0.77;

    public enum LightPattern {
        AUTO_DEFAULT, TELEOP_DEFAULT, HATCH_RELEASE,
    }

    public Blinkin() {
        try {
            rightLightController = new Spark(RobotMap.LED_LEFT_PORT);
            System.out.println("Right Light Controller"); // this never gets made no matter what
        } catch (Exception e) {
            System.out.println("No blink-in in " + RobotMap.LED_LEFT_PORT + ", error: " + e);
            rightLightController = null; 
        }

        try {
            leftLightController = new Spark(RobotMap.LED_RIGHT_PORT);
            System.out.println("Left Light Controller");
        } catch (Exception e) {
            System.out.println("No blink-in in " + RobotMap.LED_RIGHT_PORT + ", error: " + e);
            leftLightController = null;
        }
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public void setLightPattern(LightPattern pattern) {
        System.out.println("Setting patttern to: " + pattern);
        switch (pattern) {
        case AUTO_DEFAULT:
            leftLightController.set(CONFETTI);
            rightLightController.set(CONFETTI);
            break;
        case TELEOP_DEFAULT:
            leftLightController.set(RED);
            rightLightController.set(RED);
            break;
        case HATCH_RELEASE:
            leftLightController.set(SOLID_GREEN);
            rightLightController.set(SOLID_GREEN);
            System.out.println("reset color");
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
