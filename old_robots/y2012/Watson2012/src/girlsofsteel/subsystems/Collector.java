package girlsofsteel.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import girlsofsteel.RobotMap;


public class Collector extends Subsystem{

    private  final DigitalInput m_collectorLimitSwitch = new DigitalInput(RobotMap.COLLECTOR_LIMIT_SWITCH);

    private final Jaguar m_brushJag = new Jaguar(RobotMap.BRUSH_JAG);
//    Relay brushSpike = new Relay(RobotMap.BRUSH_SPIKE);
    private final Relay m_middleConveyorSpike = new Relay(RobotMap.MIDDLE_COLLECTOR_SPIKE);

    public Collector () {
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1,
                "New ball?" + getLimitSwitch());
        DriverStationLCD.getInstance().updateLCD();
    }

    // true == pressed (of getRealSwitch)
    private boolean getLimitSwitch () {
        return !m_collectorLimitSwitch.get();
    }

    @Override
    protected void initDefaultCommand() {
    }

    public void reverseBrush(){
        m_brushJag.set(1.0);
//        brushSpike.set(Relay.Value.kForward);
    }

    public void forwardBrush(){
        m_brushJag.set(-1.0);
//        brushSpike.set(Relay.Value.kReverse);
    }

    public void stopBrush(){
        m_brushJag.set(0.0);
//        brushSpike.set(Relay.Value.kOff);
    }

    public void forwardMiddleConveyor(){
        m_middleConveyorSpike.set(Relay.Value.kReverse);
    }

    public void reverseMiddleConveyor(){
        m_middleConveyorSpike.set(Relay.Value.kForward);
    }

    public void stopMiddleConveyor(){
        m_middleConveyorSpike.set(Relay.Value.kOff);
    }
}
