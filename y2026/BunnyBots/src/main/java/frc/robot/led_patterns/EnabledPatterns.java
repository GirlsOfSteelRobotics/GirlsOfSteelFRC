package frc.robot.led_patterns;

import com.gos.lib.led.LEDBase;
import com.gos.lib.led.LEDBoolean;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.OuttakeSubsystem;

public class EnabledPatterns {
    private final OuttakeSubsystem m_outtake;
    private final ChassisSubsystem m_chassis;
    private final ElevatorSubsystem m_elevator;
    private final LEDBoolean m_canSeeWall;
    //Section for if the can range sees the wall - LEDBoolean
    //Section for how many game pieces we have - Maybe 3 LEDBoolean?

    public EnabledPatterns(AddressableLEDBuffer addressme, OuttakeSubsystem outtake, ChassisSubsystem chassis, ElevatorSubsystem elevator){
        this.m_outtake = outtake;
        this.m_chassis = chassis;
        this.m_elevator = elevator;
        this.m_canSeeWall = new LEDBoolean(addressme, 0, 10, Color.kAzure, Color.kDarkOrange);
    }
    public void writeLED(){

    }
}
