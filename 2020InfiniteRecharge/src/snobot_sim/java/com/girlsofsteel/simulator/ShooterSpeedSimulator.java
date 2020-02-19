package com.girlsofsteel.simulator;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import com.snobot.simulator.simulator_components.smart_sc.BaseCanSmartSpeedController;
import frc.robot.Constants;

public class ShooterSpeedSimulator
{
    private final IPwmWrapper m_shooterWrapper;
    private final IPwmWrapper m_conveyorWrapper;
    private boolean m_lastConveyorOn;

    public ShooterSpeedSimulator()
    {
        m_shooterWrapper = SensorActuatorRegistry.get().getSpeedControllers().get(Constants.SHOOTER_SPARK_A + BaseCanSmartSpeedController.sCAN_SC_OFFSET);
        m_conveyorWrapper = SensorActuatorRegistry.get().getSpeedControllers().get(Constants.SHOOTER_CONVEYOR_SPARK_A + BaseCanSmartSpeedController.sCAN_SC_OFFSET);

    }

    public void update()
    {
        boolean conveyorOn = m_conveyorWrapper.get() > 0;
        boolean shooterOn = m_shooterWrapper.get() > 0;
        if(conveyorOn && !m_lastConveyorOn)
        {
            System.out.println("Just turned on");
            if (shooterOn)
            {
               m_shooterWrapper.reset(0, 0, 0);
            }
        }

        m_lastConveyorOn = conveyorOn;

    }
}
