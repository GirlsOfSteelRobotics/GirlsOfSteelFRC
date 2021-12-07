 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import girlsofsteel.RobotMap;

/**
 *
 * @author Sonia and Alex
 */
public class Feeder extends Subsystem {
    //Shooter piston
    private final Solenoid frontPiston;
    private final Solenoid backPiston;
    private final Solenoid frontBlocker;
    private final Solenoid backBlocker;
    private static boolean isRaised;

    public Feeder() {
        //Shooter piston
        frontPiston = new Solenoid(RobotMap.SHOOTER_MODULE, RobotMap.SHOOTER_PISTON_FRONT);
        backPiston = new Solenoid(RobotMap.SHOOTER_MODULE, RobotMap.SHOOTER_PISTON_BACK);
        frontBlocker = new Solenoid(RobotMap.BLOCKER_MODULE, RobotMap.OPEN_BLOCKER_SOLENOID);
        backBlocker = new Solenoid(RobotMap.BLOCKER_MODULE, RobotMap.CLOSE_BLOCKER_SOLENOID);
        isRaised = false;
    }

    public static boolean getIsRaised() {
        return isRaised;
    }

    public static void setIsRaised(boolean newRaised) {
        isRaised = newRaised;
    }

    //Shooter Piston Methods
    public void pushShooter() {
        frontPiston.set(false);
        backPiston.set(true);
    }

    public void pullShooter() {
        frontPiston.set(true);
        backPiston.set(false);
    }

    public void pushBlocker(){
        frontBlocker.set(false);
        backBlocker.set(true);
    }

    public void pullBlocker(){
        frontBlocker.set(true);
        backBlocker.set(false);
    }

    @Override
    protected void initDefaultCommand() {
    }
}
