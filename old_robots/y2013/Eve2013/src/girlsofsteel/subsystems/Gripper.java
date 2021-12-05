/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import girlsofsteel.RobotMap;

/**
 *
 * @author Heather
 */
public class Gripper extends Subsystem{
    private final DigitalInput openGripperSwitch;
    private final DigitalInput closeGripperSwitch;
    private final Solenoid openSolenoid;
    private final Solenoid closeSolenoid;

    public Gripper(DigitalInput openGripperSwitch, DigitalInput closeGripperSwitch,
            int openSolenoidPort, int closeSolenoidPort) {
        this.openGripperSwitch = openGripperSwitch;
        this.closeGripperSwitch = closeGripperSwitch;

        openSolenoid = new Solenoid(RobotMap.CLIMBER_MODULE, openSolenoidPort);
        closeSolenoid = new Solenoid(RobotMap.CLIMBER_MODULE,closeSolenoidPort);
    }

    //Moves the pneumatic piston slider out
    public void closeGrip() {
        openSolenoid.set(true);
        closeSolenoid.set(false);
    }

    //Getter method -> Tells if the piston slider is out (true) or in (false)
    public boolean gripperClosed(){
        return closeSolenoid.get();
    }


    //Moves the pneumatic piston slider in
    public void openGrip() {

        openSolenoid.set(false);
        closeSolenoid.set(true);
    }
    //Limit Switch Method

    //at bar means hitting open limit switch...PLEASE CHECK THIS!!!!!!!!
    public boolean atBar() {
        return !openGripperSwitch.get();
    } //Tells you that you have gone up a bar (the switch hitting a corner)

    //past bar means not hitting closed limit switch...PLEASE CHECK THIS!!!!!!!!
    public boolean pastBar(){
        return !closeGripperSwitch.get();
    }
    //This will count how many bar you hit, until you get to three at which point it returns true.

    @Override
    protected void initDefaultCommand() {
    }
}
