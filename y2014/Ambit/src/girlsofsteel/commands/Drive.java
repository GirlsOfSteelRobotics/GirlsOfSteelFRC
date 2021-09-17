/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Lori
 */
public class Drive extends CommandBase {

    Joystick joystick;
    double x;
    double y;
    //these are the axes for translation; otherwise known as direction of the wheels
    double rotation;
    //this is the axis for rotation; otherwise known as rotation of the robot
    
    public Drive() {
        requires(chassis);
        //this says that the robot needs to have the chassis to execute this command
    }
    protected void initialize() {
        joystick = oi.getJoystick();
        chassis.initEncoders();
        //remember to initialize encoders
    }

    protected void execute() {
        x = joystick.getRawAxis(oi.X_TRANSLATION_AXIS);
        y = joystick.getRawAxis(oi.Y_TRANSLATION_AXIS);
        //these get the values from the two translation axes, which control rotation of the wheels, not the robot
        rotation = joystick.getRawAxis(oi.ROTATION_AXIS);
        //this gets the value from the rotation axis, which controls rotation of the robot
        chassis.drive(x, y, rotation);
        //the chassis needs the translation and rotation values to know how to drive
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.stop();
        //when the command is stopped, the chassis is also stopped from executing the commands/stops moving
    }

    protected void interrupted() {
        end();
    }
    
}
