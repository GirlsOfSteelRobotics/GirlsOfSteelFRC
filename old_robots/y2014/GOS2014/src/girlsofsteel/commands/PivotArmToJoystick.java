/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author user
 */
public class PivotArmToJoystick extends CommandBase {

    private final Joystick operator;
    private int count;
    private double angle;

    public PivotArmToJoystick() {
        requires(manipulator); //HAVE TO REQUIRE MANIPULATOR SO THAT THIS DOESN'T INTERFERE WITH OTHER MANIPULATOR COMMANDS
        operator = CommandBase.oi.getOperatorJoystick();
    }

    @Override
    protected void initialize() {
        angle = manipulator.getAbsoluteDistance();
        count = 0;
    }

    @Override
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

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        manipulator.holdAngle();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
