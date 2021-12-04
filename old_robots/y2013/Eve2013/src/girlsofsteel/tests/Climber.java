/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.tests;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.commands.CommandBase;

/**
 *
 * @author sam
 */
public class Climber extends CommandBase {

    public int state;

    public Climber() {
        requires(climber);

        SmartDashboard.putString("Comand called", "comand called");



        SmartDashboard.putBoolean("Spikes", false);
        SmartDashboard.putBoolean("Spikes Foward Backwards", false);

        SmartDashboard.putBoolean("Bottom grip Open Close", false);
        SmartDashboard.putBoolean("Middle grip Open Close", false);
        SmartDashboard.putBoolean("Top grip Open Close", false);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {


        if (SmartDashboard.getBoolean("Spikes", false) && SmartDashboard.getBoolean("Spikes Foward Backwards", false)) {
            climber.forwardRightClimberSpike();
            climber.forwardLeftClimberSpike();
        } else if(SmartDashboard.getBoolean("Spikes", false) && !SmartDashboard.getBoolean("Spikes Foward Backwards", false)) {
            climber.reverseLeftClimberSpike();
            climber.reverseRightClimberSpike();
        } else if (!SmartDashboard.getBoolean("Spikes", false)){
            climber.stopLeftClimberSpike();
            climber.stopRightClimberSpike();
        }

        if (SmartDashboard.getBoolean("Bottom grip Open Close", false)) {
            bottomGripper.openGrip();
        } else {
            bottomGripper.closeGrip();
        }

//        if (SmartDashboard.getBoolean("Middle grip Open Close", false)) {
//            middleGripper.openGrip();
//        } else {
//            middleGripper.closeGrip();
//        }
//
//        if (SmartDashboard.getBoolean("Top grip Open Close", true)) {
//            topGripper.openGrip();
//        } else {
//            topGripper.closeGrip();
//        }

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
