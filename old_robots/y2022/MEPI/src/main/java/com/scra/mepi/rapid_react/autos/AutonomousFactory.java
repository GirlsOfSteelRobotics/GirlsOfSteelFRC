package com.scra.mepi.rapid_react.autos;

import com.scra.mepi.rapid_react.subsystems.ShooterSubsytem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.HashMap;
import java.util.Map;

public class AutonomousFactory {
    private static final AutonMode DEFAULT_MODE = AutonMode.SHOOT_4;

    private final SendableChooser<AutonMode> m_chooseAutoOption;

    //MAKE SURE YOU INCLUDE DO NOTHING and also JUST SHOOTING
    public enum AutonMode {
        SHOOT_LEAVE_COMMUNITY_1,
        SHOOT_ENGAGE_4,
        SHOOT_LEAVE_COMMUNITY_7,
        SHOOT_LEAVE_COMMUNITY_4,
        SHOOT_1,
        SHOOT_4,
        SHOOT_7


    }

    private final Map<AutonMode, Command> m_autoOptions = new HashMap<>();

    //in parameters, pass in all subsystems you're going to use
    public AutonomousFactory(ShooterSubsytem shooterSubsytem) {
        m_chooseAutoOption = new SendableChooser<>();

        //        m_autoOptions.put(AutonMode.SHOOT_LEAVE_COMMUNITY_1,new ShootLeaveCommunity(drivetrainSubsystem,shooterSubsytem,"leaveCommunity1"));
        //        m_autoOptions.put(AutonMode.SHOOT_LEAVE_COMMUNITY_7,new ShootLeaveCommunity(drivetrainSubsystem,shooterSubsytem,"leaveCommunity7"));
        m_autoOptions.put(AutonMode.SHOOT_4, new Shoot(shooterSubsytem));
        m_autoOptions.put(AutonMode.SHOOT_1, new Shoot(shooterSubsytem));
        m_autoOptions.put(AutonMode.SHOOT_7, new Shoot(shooterSubsytem));

        for (AutonMode auto : AutonMode.values()) {
            if (auto == DEFAULT_MODE) {
                m_chooseAutoOption.setDefaultOption(auto.toString(), auto);
            } else {
                m_chooseAutoOption.addOption(auto.toString(), auto);
            }
        }
        SmartDashboard.putData("Auto Mode Select", m_chooseAutoOption);
    }

    public Command getAutonomousCommand() {
        AutonMode mode = m_chooseAutoOption.getSelected();
        return m_autoOptions.get(mode);
    }


}
