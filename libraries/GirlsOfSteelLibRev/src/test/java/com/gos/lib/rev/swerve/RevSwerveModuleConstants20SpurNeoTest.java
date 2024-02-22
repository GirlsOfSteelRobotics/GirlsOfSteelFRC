package com.gos.lib.rev.swerve;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RevSwerveModuleConstants20SpurNeoTest {
    private final RevSwerveModuleConstants.DriveMotor m_driveMotor = RevSwerveModuleConstants.DriveMotor.NEO;
    private final RevSwerveModuleConstants.DriveMotorSpurTeeth m_spurTeeth = RevSwerveModuleConstants.DriveMotorSpurTeeth.T20;

    @Test
    public void test14Teeth() {
        RevSwerveModuleConstants.DriveMotorPinionTeeth teeth = RevSwerveModuleConstants.DriveMotorPinionTeeth.T14;

        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(m_driveMotor, teeth, m_spurTeeth);
        assertEquals(4.29, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(5.28, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void test15Teeth() {
        RevSwerveModuleConstants.DriveMotorPinionTeeth teeth = RevSwerveModuleConstants.DriveMotorPinionTeeth.T15;

        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(m_driveMotor, teeth, m_spurTeeth);
        assertEquals(4.0, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(5.66, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void test16Teeth() {
        RevSwerveModuleConstants.DriveMotorPinionTeeth teeth = RevSwerveModuleConstants.DriveMotorPinionTeeth.T16;

        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(m_driveMotor, teeth, m_spurTeeth);
        assertEquals(3.75, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(6.04, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }
}
