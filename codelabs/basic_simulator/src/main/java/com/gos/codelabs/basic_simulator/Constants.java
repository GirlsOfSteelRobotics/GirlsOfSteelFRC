/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.codelabs.basic_simulator;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj.system.LinearSystem;
import edu.wpi.first.wpilibj.system.plant.DCMotor;
import edu.wpi.first.wpilibj.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpiutil.math.numbers.N2;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    /////////////////////////////////
    // Sensor / Actuator ports
    /////////////////////////////////

    // PWM

    // Digital IO
    public static final int DIO_LIFT_LOWER_LIMIT = 0;
    public static final int DIO_LIFT_UPPER_LIMIT = 1;

    // Solenoids
    public static final int SOLENOID_PUNCH = 0;

    // CAN
    public static final int CAN_CHASSIS_LEFT_A = 1;
    public static final int CAN_CHASSIS_LEFT_B = 2;
    public static final int CAN_CHASSIS_RIGHT_A = 3;
    public static final int CAN_CHASSIS_RIGHT_B = 4;
    public static final int CAN_LIFT_MOTOR = 5;
    public static final int CAN_SPINNING_MOTOR = 6;
    public static final int CAN_PIGEON = 7;

    private Constants() {

    }


    public static final class DrivetrainConstants {

        public static final DCMotor kDriveGearbox = DCMotor.getCIM(2);
        public static final double kDriveGearing = 8;

        public static final double kTrackwidthMeters = 0.69;
        public static final double kWheelDiameterMeters = 0.15;

        public static final double ksVolts = 0.22;
        public static final double kvVoltSecondsPerMeter = 1.98;
        public static final double kaVoltSecondsSquaredPerMeter = 0.2;
        public static final double kvVoltSecondsPerRadian = 2.5;
        public static final double kaVoltSecondsSquaredPerRadian = 0.8;

        public static final LinearSystem<N2, N2, N2> kDrivetrainPlant =
                LinearSystemId.identifyDrivetrainSystem(kvVoltSecondsPerMeter, kaVoltSecondsSquaredPerMeter,
                        kvVoltSecondsPerRadian, kaVoltSecondsSquaredPerRadian);

        public static final DifferentialDriveKinematics kDriveKinematics =
                new DifferentialDriveKinematics(kTrackwidthMeters);

        public static DifferentialDrivetrainSim createSim() {
            return new DifferentialDrivetrainSim(
                    kDrivetrainPlant,
                    kDriveGearbox,
                    kDriveGearing,
                    kTrackwidthMeters,
                    kWheelDiameterMeters / 2.0,
                    null);
        }
    }

    public static final class ElevatorSimConstants {
        public static final double kElevatorGearing = 10.0;
        public static final double kCarriageMass = 4.0; // kg
        public static final double kMinElevatorHeight = -5.0;
        public static final double kMaxElevatorHeight = Units.inchesToMeters(50e50);
        public static final DCMotor kElevatorGearbox = DCMotor.getVex775Pro(4);

        public static final double kElevatorDrumRadius = Units.inchesToMeters(2.0);

        public static ElevatorSim createSim() {

            return new ElevatorSim(
                    ElevatorSimConstants.kElevatorGearbox,
                    ElevatorSimConstants.kElevatorGearing,
                    ElevatorSimConstants.kCarriageMass,
                    ElevatorSimConstants.kElevatorDrumRadius,
                    ElevatorSimConstants.kMinElevatorHeight,
                    ElevatorSimConstants.kMaxElevatorHeight);
        }

        private ElevatorSimConstants() {

        }
    }

    public static final class FlywheelSimConstants {
        public static final DCMotor kGearbox = DCMotor.getVex775Pro(2);
        public static final double kGearing = 4;
        public static final double kInertia = 0.008;

        public static FlywheelSim createSim() {
            return new FlywheelSim(FlywheelSimConstants.kGearbox, FlywheelSimConstants.kGearing, FlywheelSimConstants.kInertia);
        }

        private FlywheelSimConstants() {

        }
    }
}
