/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder.PIDSourceParameter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GetLeftEncoderDistance extends CommandBase{

    protected void initialize() {
        chassis.initLeftEncoder( 1.0/250.0);
       //chose 250 because how its set up on the shaft sppciffically
    }

    protected void execute() {
        double encoderDistance = chassis.leftEncoderDistance();
        SmartDashboard.putDouble("Encoder Distance",encoderDistance);
        
        //allows you to tune the PID & print values using SmartDashBoard
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.endLeftEncoder();
    }

    protected void interrupted() {
        end();
    }
    
}
