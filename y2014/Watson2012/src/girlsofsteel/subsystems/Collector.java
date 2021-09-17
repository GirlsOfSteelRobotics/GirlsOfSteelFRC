package girlsofsteel.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import girlsofsteel.RobotMap;


public class Collector extends Subsystem{
    
    DigitalInput collectorLimitSwitch = new DigitalInput(RobotMap.COLLECTOR_LIMIT_SWITCH);
    
    Jaguar brushJag = new Jaguar(RobotMap.BRUSH_JAG);
//    Relay brushSpike = new Relay(RobotMap.BRUSH_SPIKE);
    Relay middleConveyorSpike = new Relay(RobotMap.MIDDLE_COLLECTOR_SPIKE);
    
    public Collector () {
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1,
                "New ball?" + getLimitSwitch());
        DriverStationLCD.getInstance().updateLCD();
    }
    
    // true == pressed (of getRealSwitch)
    private boolean getLimitSwitch () {
        return !collectorLimitSwitch.get();
    }

    protected void initDefaultCommand() {
    }
    
    public void reverseBrush(){
        brushJag.set(1.0);
//        brushSpike.set(Relay.Value.kForward);
    }
    
    public void forwardBrush(){
        brushJag.set(-1.0);
//        brushSpike.set(Relay.Value.kReverse);
    }
    
    public void stopBrush(){
        brushJag.set(0.0);
//        brushSpike.set(Relay.Value.kOff);
    }
    
    public void forwardMiddleConveyor(){
        middleConveyorSpike.set(Relay.Value.kReverse);
    }
    
    public void reverseMiddleConveyor(){
        middleConveyorSpike.set(Relay.Value.kForward);
    }
    
    public void stopMiddleConveyor(){
        middleConveyorSpike.set(Relay.Value.kOff);
    }
}