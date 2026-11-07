package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SuperStructureViz extends SubsystemBase {
    // Where carrots sit in the hopper, in the robot frame with the elevator down. The hopper floor slopes
    // down towards the outtake wheels, so the first carrot sits under them and the rest line up behind it
    private static final Translation3d[] CARROT_SLOTS = {
        new Translation3d(-0.124, -0.226, 0.510),
        new Translation3d(-0.124, -0.080, 0.554),
        new Translation3d(-0.124, 0.066, 0.598),
    };
    // The carrot model stands upright, but they lie front to back in the hopper
    private static final Rotation3d CARROT_ROTATION = new Rotation3d(0, Math.PI / 2, 0);

    private final ElevatorSubsystem m_elevator;
    private final ChassisSubsystem m_chassis;
    private final StructArrayPublisher<Pose3d> m_measuredPublisher;
    private final StructArrayPublisher<Pose3d> m_goalPublisher;
    private final StructArrayPublisher<Pose3d> m_carrotPublisher;

    public SuperStructureViz(ElevatorSubsystem elevator, ChassisSubsystem chassis) {
        m_elevator = elevator;
        m_chassis = chassis;

        m_measuredPublisher = NetworkTableInstance.getDefault().getStructArrayTopic("SuperStructureViz/Measured", Pose3d.struct).publish();
        m_goalPublisher = NetworkTableInstance.getDefault().getStructArrayTopic("SuperStructureViz/Goal", Pose3d.struct).publish();
        m_carrotPublisher = NetworkTableInstance.getDefault().getStructArrayTopic("SuperStructureViz/Carrots", Pose3d.struct).publish();
    }

    @Override
    public void periodic() {
        double height = m_elevator.getHeight();
        m_measuredPublisher.set(getPoses(height));
        m_goalPublisher.set(getPoses(m_elevator.getGoalHeight()));
        m_carrotPublisher.set(getCarrotPoses(height));
    }

    private Pose3d[] getPoses(double carriageHeightMeters) {
        // Cascade elevator, the inner stage moves half as far as the carriage
        Pose3d innerStagePose = new Pose3d(0, 0, carriageHeightMeters / 2, new Rotation3d());
        Pose3d carriagePose = new Pose3d(0, 0, carriageHeightMeters, new Rotation3d());

        return new Pose3d[] {
            innerStagePose,
            carriagePose,
        };
    }

    private Pose3d[] getCarrotPoses(double carriageHeightMeters) {
        // Game pieces are drawn relative to the field, so place them relative to the robot's pose
        Pose3d robotPose = new Pose3d(m_chassis.getState().Pose);
        int numCarrots = 2; // TODO get from intake subsystem
        int count = Math.max(0, Math.min(numCarrots, CARROT_SLOTS.length));

        Pose3d[] carrots = new Pose3d[count];
        for (int i = 0; i < count; i++) {
            Translation3d slot = CARROT_SLOTS[i].plus(new Translation3d(0, 0, carriageHeightMeters));
            carrots[i] = robotPose.transformBy(new Transform3d(slot, CARROT_ROTATION));
        }
        return carrots;
    }
}
