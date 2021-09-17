/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author user
 */
public class PivotArmToJoystick extends CommandBase {

    Joystick operator;
    int count;
    double angle;

    public PivotArmToJoystick() {
        requires(manipulator); //HAVE TO REQUIRE MANIPULATOR SO THAT THIS DOESN'T INTERFERE WITH OTHER MANIPULATOR COMMANDS
        operator = CommandBase.oi.getOperatorJoystick();
    }

    protected void initialize() {
        angle = manipulator.getAbsoluteDistance();
        count = 0;
    }

    protected void execute() {
        System.out.println("Operator Y: " + operator.getY());
        if (operator.getY() > 0.5) {
            manipulator.setSetPoint(angle);
            angle++; //+= 1.5;
        } else if (operator.getY() < -0.5) {
            manipulator.setSetPoint(angle);
            angle--; //-= 1.5;
        }
            manipulator.holdAngle();
        
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        manipulator.holdAngle();
    }

    protected void interrupted() {
        end();
    }
}
