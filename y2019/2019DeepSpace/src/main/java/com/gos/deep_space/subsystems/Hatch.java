/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.deep_space.RobotMap;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hatch extends SubsystemBase {
    private static final double SLOW_COLLECTOR_SPEED = 0.25;
    private static final double COLLECTOR_INTAKE_SPEED = 0.4;
    private static final double COLLECTOR_RELEASE_SPEED = 0.4;

    private final WPI_TalonSRX m_hatchCollector;


    public Hatch() {
        m_hatchCollector = new WPI_TalonSRX(RobotMap.HATCH_TALON);
        m_hatchCollector.setInverted(true);
        addChild("Collector", m_hatchCollector);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void stop() {
        m_hatchCollector.stopMotor();
    }

    //switched all to opposite sign of what they where bc that's correct on comp bot

    public void collect() {
        m_hatchCollector.set(-COLLECTOR_INTAKE_SPEED);
    }

    public void release() {
        m_hatchCollector.set(COLLECTOR_RELEASE_SPEED);
    }

    public void slowCollect() {
        m_hatchCollector.set(-SLOW_COLLECTOR_SPEED);
    }
}
