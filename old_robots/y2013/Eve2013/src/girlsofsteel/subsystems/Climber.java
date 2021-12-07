/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import girlsofsteel.RobotMap;

/**
 *
 * @author Heather
 */
public class Climber extends Subsystem {

    //cLIMBER sPIKES
    private final Relay rightClimberSpike = new Relay(RobotMap.RIGHT_CLIMBER_SPIKE);
    private final Relay leftClimberSpike = new Relay(RobotMap.LEFT_CLIMBER_SPIKE);


    private final Solenoid extendLifterPistonSolenoid = new Solenoid(
            RobotMap.LIFTER_MODULE, RobotMap.EXTEND_LIFTER_PISTON_SOLENOID);
    private final Solenoid retractLifterPistonSolenoid = new Solenoid(
            RobotMap.LIFTER_MODULE, RobotMap.RETRACT_LIFTER_PISTON_SOLENOID);


    //Piston methods
    public void extendLifterPiston() {
        extendLifterPistonSolenoid.set(true);
        retractLifterPistonSolenoid.set(false);
    }

    public void retractLifterPiston() {
        retractLifterPistonSolenoid.set(true);
        extendLifterPistonSolenoid.set(false);
    }

    //TODO figure out how to check piston

    public boolean isPistonExtended() {
        return extendLifterPistonSolenoid.get();
    }

    //Spikes methods
    public void forwardRightClimberSpike() {
        rightClimberSpike.set(Relay.Value.kReverse);
    }

    public void reverseRightClimberSpike() {
        rightClimberSpike.set(Relay.Value.kForward);
    }

    public void stopRightClimberSpike() {
        rightClimberSpike.set(Relay.Value.kOff);
    }

    public void forwardLeftClimberSpike() {
        leftClimberSpike.set(Relay.Value.kForward);
    }

    public void reverseLeftClimberSpike() {
        leftClimberSpike.set(Relay.Value.kReverse);
    }

    public void stopLeftClimberSpike() {
        leftClimberSpike.set(Relay.Value.kOff);
    }

    @Override
    protected void initDefaultCommand() {
    }


}
