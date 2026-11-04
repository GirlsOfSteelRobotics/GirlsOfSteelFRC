package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SuperStructureViz extends SubsystemBase {

    private final StructArrayPublisher<Pose3d> m_measuredPublisher;
    private final StructArrayPublisher<Pose3d> m_goalPublisher;
    private final StructPublisher<Pose2d> m_goalRobotPose;

    private final OuttakeSubsystem m_outtakeSubsystem;
    private final ElevatorSubsystem m_elevatorSubsystem;
    private final ChassisSubsystem m_chassisSubsystem;

    public SuperStructureViz(OuttakeSubsystem intakeSubsystem, ElevatorSubsystem elevatorSubsystem, ChassisSubsystem chassisSubsystem) {
        m_measuredPublisher = NetworkTableInstance.getDefault().getStructArrayTopic("SuperStructureViz/Measured", Pose3d.struct).publish();
        m_goalPublisher = NetworkTableInstance.getDefault().getStructArrayTopic("SuperStructureViz/Goal", Pose3d.struct).publish();
        m_goalRobotPose = NetworkTableInstance.getDefault().getStructTopic("SuperStructureViz/Robot Pose Goal", Pose2d.struct).publish();

        m_outtakeSubsystem = intakeSubsystem;
        m_elevatorSubsystem = elevatorSubsystem;
        m_chassisSubsystem = chassisSubsystem;
    }

    @Override

    public void periodic() {

        m_measuredPublisher.set(
            getPoses(m_elevatorSubsystem.getHeight(), 2));

    }


    private Pose3d[] getPoses(double elevatorHeight, double hopperHeight) {

        Pose3d elevatorPose = new Pose3d(
            Units.inchesToMeters(0), Units.inchesToMeters(0), Units.inchesToMeters(6),
            new Rotation3d(0, 0, 0));

        Pose3d hopperPose = new Pose3d(
            Units.inchesToMeters(0), Units.inchesToMeters(0), Units.inchesToMeters(10),
            new Rotation3d(0, 0, 0));

        return new Pose3d[] {
            elevatorPose,
            hopperPose
        };
    }
}
