package com.gos.rapidreact.trajectory;

import com.gos.rapidreact.commands.SetInitialOdometryCommand;
import com.gos.rapidreact.commands.autonomous.FollowTrajectory;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.math.spline.Spline;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;

public class TrajectoryUtils {

    public static final double DEFAULT_ACCELERATION = Units.inchesToMeters(60);
    public static final double DEFAULT_VELOCITY = Units.inchesToMeters(72);

    //reads file & spits out trajectory
    public static Trajectory loadingTrajectory(String fileName, TrajectoryConfig trajectoryConfig) {
        TrajectoryGenerator.ControlVectorList list = new TrajectoryGenerator.ControlVectorList();
        Path fullFile = Filesystem.getDeployDirectory().toPath().resolve(fileName);
        try (BufferedReader reader = Files.newBufferedReader(fullFile)) {

            // Read the CSV header
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                double xPath = Double.parseDouble(tokenizer.nextToken());
                double yPath = Double.parseDouble(tokenizer.nextToken()) + Units.feetToMeters(27);
                double xTangent = Double.parseDouble(tokenizer.nextToken());
                double yTangent = Double.parseDouble(tokenizer.nextToken());
                list.add(new Spline.ControlVector(
                    new double[] {xPath, xTangent, 0},
                    new double[] {yPath, yTangent, 0}));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return TrajectoryGenerator.generateTrajectory(list, trajectoryConfig);
    }

    public static CommandBase startTrajectory(String fileName, TrajectoryConfig trajectoryConfig, ChassisSubsystem chassis) {
        Trajectory trajectory = loadingTrajectory(fileName, trajectoryConfig);
        FollowTrajectory followTrajectory = new FollowTrajectory(trajectory, chassis);
        SetInitialOdometryCommand setPosition = new SetInitialOdometryCommand(chassis, trajectory.getInitialPose());
        return setPosition.andThen(followTrajectory);
    }

    public static CommandBase createTrajectory(String fileName, TrajectoryConfig trajectoryConfig, ChassisSubsystem chassis) {
        Trajectory trajectory = loadingTrajectory(fileName, trajectoryConfig);
        return new FollowTrajectory(trajectory, chassis);
    }

    public static TrajectoryConfig getTrajectoryConfig(double maxSpeedMetersPerSecond, double maxAccelerationMetersPerSecondSquared) {
        var autoVoltageConstraint =
            new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(ChassisSubsystem.KS_VOLTS,
                    ChassisSubsystem.KV_VOLT_SECONDS_PER_METER,
                    ChassisSubsystem.KA_VOLT_SECONDS_SQUARED_PER_METER),
                ChassisSubsystem.K_DRIVE_KINEMATICS,
                ChassisSubsystem.MAX_VOLTAGE);

        return new TrajectoryConfig(maxSpeedMetersPerSecond,
            maxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(ChassisSubsystem.K_DRIVE_KINEMATICS)
            // Apply the voltage constraint
            .addConstraint(autoVoltageConstraint);
    }

    public static TrajectoryConfig getTrajectoryConfig() {
        return  getTrajectoryConfig(DEFAULT_VELOCITY, DEFAULT_ACCELERATION);
    }
}
