package com.gos.lib.rev.swerve;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RevSwerveModuleConstants22SpurVortexTest {
    private final RevSwerveModuleConstants.DriveMotor m_driveMotor = RevSwerveModuleConstants.DriveMotor.VORTEX;
    private final RevSwerveModuleConstants.DriveMotorSpurTeeth m_spurTeeth = RevSwerveModuleConstants.DriveMotorSpurTeeth.T22;

    @Test
    public void test12Teeth() {
        RevSwerveModuleConstants.DriveMotorPinionTeeth teeth = RevSwerveModuleConstants.DriveMotorPinionTeeth.T12;

        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(m_driveMotor, teeth, m_spurTeeth);
        assertEquals(5.50, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(4.92, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void test13Teeth() {
        RevSwerveModuleConstants.DriveMotorPinionTeeth teeth = RevSwerveModuleConstants.DriveMotorPinionTeeth.T13;

        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(m_driveMotor, teeth, m_spurTeeth);
        assertEquals(5.08, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(5.33, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void test14Teeth() {
        RevSwerveModuleConstants.DriveMotorPinionTeeth teeth = RevSwerveModuleConstants.DriveMotorPinionTeeth.T14;

        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(m_driveMotor, teeth, m_spurTeeth);
        assertEquals(4.71, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(5.74, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }
}
