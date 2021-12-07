package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.RobotMap;

/**
 *
 */
public class Flap extends Subsystem {

    private static final double maxEncoder = 360; //max encoder val

    private final CANTalon m_flapTalon;
    private double m_encOffsetValue;

    public Flap(){
        m_flapTalon = new CANTalon(RobotMap.FLAP_MOTOR);
        addChild("Talon", m_flapTalon);

        if(RobotMap.USING_LIMIT_SWITCHES) {
            m_flapTalon.configFwdLimitSwitchNormallyOpen(false);
            m_flapTalon.configRevLimitSwitchNormallyOpen(false);
        }
        else {
            m_flapTalon.enableLimitSwitch(false, false);
        }
        m_flapTalon.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void setTalon(double speed){
        m_flapTalon.set(speed);
    }

    public void stopTalon(){
        m_flapTalon.set(0.0);
    }

    public double getMaxEnc() {
        return maxEncoder;
    }

    //assuming that going forward will raise the flap and going backwards will lower the flap
    public boolean getTopLimitSwitch(){
        return m_flapTalon.isFwdLimitSwitchClosed() == 0;
    }
    public boolean getBottomLimitSwitch(){
        return m_flapTalon.isRevLimitSwitchClosed() == 0;
    }

    public double getFlapEncoder() {
        return m_flapTalon.getEncPosition();
    }

    public double getFlapEncoderDistance() {
        return (getFlapEncoder() - m_encOffsetValue); //TODO: know how far encoder is
    }

    public void resetDistance() {
        m_encOffsetValue = getFlapEncoder();
    }
}
