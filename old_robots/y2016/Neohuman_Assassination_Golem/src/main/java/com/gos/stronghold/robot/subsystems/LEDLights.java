package com.gos.stronghold.robot.subsystems;


import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LEDLights extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    /**
     * public void blueLight(){
     * serialPort.writeString("b");
     * }
     * public void greenLight(){
     * serialPort.writeString("g");
     * }
     * <p>
     * public void redLight(){
     * serialPort.writeString("r");
     * }
     * <p>
     * public void whiteLight(){
     * serialPort.writeString("w");
     * }
     * <p>
     * public void autoLights(){
     * serialPort.writeString("a");
     * }
     * <p>
     * public void dotLights(){
     * serialPort.writeString("p");
     * }
     * <p>
     * public void initLights(){
     * switch (DriverStation.getInstance().getAlliance()) {
     * case Red:
     * Robot.ledlights.redLight();
     * break;
     * case Blue:
     * Robot.ledlights.blueLight();
     * break;
     * case Invalid:
     * Robot.ledlights.autoLights();
     * default:
     * break;
     * }
     * }
     **/
    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

}
