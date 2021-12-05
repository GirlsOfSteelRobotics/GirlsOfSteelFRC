/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import girlsofsteel.Configuration;

/**
 *
 * @author sophia, sonia
 */
public class HoldChassisInPlace extends CommandBase {

    private double setPoint;

    public HoldChassisInPlace()
    {
        requires(chassis);

    }

    @Override
    protected void initialize() {
        chassis.initEncoders();
        chassis.initPositionPIDS();
        chassis.resetPositionPIDError();
        setPoint = chassis.getLeftEncoderDistance();//The current position, which must be maintained
        chassis.setLeftPositionPIDValues(5, 0, 0);
        chassis.setRightPositionPIDValues(5, 0, 0);
    }

    @Override
    protected void execute() {
        chassis.setPosition(setPoint);
        /* USING ENCODERS
        if(chassis.getLeftEncoder() > 0)
        {
            chassis.setLeftJag(.2); //TODO: Check these values
        }
        else if (chassis.getLeftEncoder() < 0)
        {
            chassis.setLeftJag(-.2);
        }
        else
        {
            chassis.setLeftJag(0.0);
        }

        if(chassis.getRightEncoder() > 0)
        {
            chassis.setRightJag(.2);
        }
        else if(chassis.getRightEncoder() < 0)
        {
            chassis.setRightJag(-.2);
        }
        else
        {
            chassis.setRightJag(0.0);
        }
        * */
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        chassis.setLeftPositionPIDValues(Configuration.leftPositionP, 0, 0);
        chassis.setRightPositionPIDValues(Configuration.rightPositionP, 0, 0);
        chassis.stopEncoders();
        chassis.disablePositionPID();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
