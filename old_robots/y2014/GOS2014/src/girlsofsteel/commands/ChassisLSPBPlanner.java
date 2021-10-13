/*

 Uses the LSPB PID planner to position pid
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Sylvie
 */
public class ChassisLSPBPlanner extends CommandBase {

    private double startTime; //milliseconds
    private double changeInTime;
    private double setPoint;
    boolean graphed;

    public ChassisLSPBPlanner() {
        requires(driving);
    }

    protected void initialize() {
        graphed = false;
        startTime = System.currentTimeMillis();
        chassis.initEncoders();
        chassis.initPositionPIDS();
        chassis.resetPositionPIDError();
        SmartDashboard.putNumber("LSPB Setpoint", 0.0);
    }

    protected void execute() {
        setPoint = SmartDashboard.getNumber("LSPB Setpoint");
        if (setPoint != 0) {
            if (!graphed) {
                startTime = System.currentTimeMillis();
                chassis.leftChassisPlanner.calculateVelocityGraph(setPoint);
                chassis.rightChassisPlanner.calculateVelocityGraph(setPoint);
                graphed = true;
            }

            SmartDashboard.putNumber("LSPB Left Encoder", chassis.getLeftEncoderDistance());
            SmartDashboard.putNumber("LSPB Right Encoder", chassis.getRightEncoderDistance());
            System.out.print("\tRight Encoder: " + chassis.getRightEncoderDistance());
           // if ((int)changeInTime % 10 == 0) {
                changeInTime = System.currentTimeMillis() - startTime;
                chassis.setPositionSeparate(chassis.leftChassisPlanner.getDesiredPosition(changeInTime), chassis.rightChassisPlanner.getDesiredPosition(changeInTime));
           // }
        }
    }

    protected boolean isFinished() {
        boolean ret =  (/*Math.abs(chassis.getLeftEncoderDistance() - setPoint) < 0.05) || */
                ((Math.abs(chassis.getRightEncoderDistance() - setPoint) < 0.01 ) 
                || (Math.abs(chassis.getRightEncoderDistance()) > Math.abs(setPoint)))
                && graphed); //Right encoder/pid is flipped TODO configuration
        if(ret)
         System.out.println("finished ");
        
        return ret;
    }

    protected void end() {
        chassis.stopJags();
        chassis.disablePositionPID();
        chassis.stopEncoders();
    }

    protected void interrupted() {
        end();
    }

}
