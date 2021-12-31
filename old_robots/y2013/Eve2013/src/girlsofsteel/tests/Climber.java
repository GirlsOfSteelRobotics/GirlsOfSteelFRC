/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.tests;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.commands.CommandBase;
import girlsofsteel.subsystems.Gripper;

/**
 * @author sam
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Climber extends CommandBase {

    private final girlsofsteel.subsystems.Climber m_climber;
    private final Gripper m_gripper;
    public int m_state;

    public Climber(girlsofsteel.subsystems.Climber climber, Gripper gripper) {
        m_climber = climber;
        m_gripper = gripper;
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
            m_climber.forwardRightClimberSpike();
            m_climber.forwardLeftClimberSpike();
        } else if (SmartDashboard.getBoolean("Spikes", false) && !SmartDashboard.getBoolean("Spikes Foward Backwards", false)) {
            m_climber.reverseLeftClimberSpike();
            m_climber.reverseRightClimberSpike();
        } else if (!SmartDashboard.getBoolean("Spikes", false)) {
            m_climber.stopLeftClimberSpike();
            m_climber.stopRightClimberSpike();
        }

        if (SmartDashboard.getBoolean("Bottom grip Open Close", false)) {
            m_gripper.openGrip();
        } else {
            m_gripper.closeGrip();
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
